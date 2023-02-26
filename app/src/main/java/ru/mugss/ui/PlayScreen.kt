package ru.mugss.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.material3.Card
import dev.olshevski.navigation.reimagined.NavController
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import ru.mugss.ui.model.SongModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.Player
import ru.mugss.Constants
import ru.mugss.R
import ru.mugss.data.network.repository.TrackRepositoryImpl
import ru.mugss.domain.TrackInteractor
import ru.mugss.ui.stateholders.PlayScreenViewModel
import kotlin.math.roundToInt

@Composable
fun PlayScreen(navController: NavController<Screen>) {
    val viewModel = remember {
        PlayScreenViewModel(TrackInteractor(TrackRepositoryImpl()))
    }
    val guessedSong = viewModel.urlOfSongToGuess.observeAsState()
    SongPlayer(guessedSong.value?.urlSong ?: "", viewModel)
    UiPart(viewModel = viewModel, guessedSong.value)
}

@Composable
fun UiPart(viewModel: PlayScreenViewModel, guessedSong: SongModel?) {
    val songs = viewModel.currentSongs.observeAsState()
    val modeCard = viewModel.isFullScreen.observeAsState()
    val isFullScreen = modeCard.value ?: false
    val modifierCard: Modifier
    val guessedSongCard: SongModel
    val unknownSong = SongModel(
        name = Constants.unknownNameOfSong,
        author = Constants.unknownAuthorOfSong,
        urlSong = ""
    )
    if (isFullScreen) {
        modifierCard = Modifier
            .fillMaxSize()
            .animateContentSize()
        guessedSongCard = guessedSong ?: unknownSong
    } else {
        modifierCard = Modifier
            .padding(vertical = 20.dp, horizontal = 32.dp)
            .fillMaxWidth()
            .animateContentSize()
        guessedSongCard = unknownSong
    }

    if ((songs.value?.size ?: 0) == 0) return
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        AnimatedVisibility(visible = !isFullScreen) {
            TopBar(viewModel = viewModel)
            Spacer(modifier = Modifier.size(50.dp))
            Row {
                val firstSong = songs.value?.get(0) ?: return@AnimatedVisibility
                val secondSong = songs.value?.get(1) ?: return@AnimatedVisibility
                val thirdSong = songs.value?.get(2) ?: return@AnimatedVisibility
                val modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                CardSongChoose(
                    songModel = firstSong, modifier = modifier.clickable {
                        viewModel.handleClick(firstSong)
                    }
                )
                CardSongChoose(
                    songModel = secondSong, modifier = modifier.clickable {
                        viewModel.handleClick(secondSong)
                    }
                )
                CardSongChoose(
                    songModel = thirdSong, modifier = modifier.clickable {
                        viewModel.handleClick(thirdSong)
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Card(
            modifier = modifierCard,
            shape = RoundedCornerShape(CornerSize(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            SongInfo(
                songModel = guessedSongCard,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .animateContentSize(), isFullScreen
            )
            SliderLayout(viewModel)
            if (isFullScreen) {
                BottomFullScreenCard(
                    viewModel = viewModel,
                    multiplier = viewModel.multiplier.value ?: 1.0,
                    scores = viewModel.score.value ?: 0
                )
            }
        }
    }
}


@Composable
fun SongPlayer(url: String, viewModel: PlayScreenViewModel) {
    val context = LocalContext.current
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }.apply {
        if (mediaItemCount > 0) {
            removeMediaItem(0)
        }
        setMediaItem(
            MediaItem.Builder().setUri(url).setClippingConfiguration(
                MediaItem.ClippingConfiguration.Builder()
                    .setEndPositionMs(Constants.durationOfSong)
                    .build()
            ).build()
        )
        prepare()
    }
    DisposableEffect(key1 = Unit, effect = {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying_: Boolean) {
                if (isPlaying_) {
                    viewModel.resumeGameTimer()
                    viewModel.resumeSongTimer(exoPlayer.currentPosition)
                } else {
                    viewModel.pauseGameTimer()
                    viewModel.pauseSongTimer()
                }
            }
        }
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer.play()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.pause()
                }
                else -> {}
            }
        }
        val lifecycle = lifecycleOwner.value.lifecycle
        lifecycle.addObserver(observer)
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
            lifecycle.removeObserver(observer)
        }
    })
}


@Composable
fun SliderLayout(viewModel: PlayScreenViewModel) {
    val currentState = viewModel.currentTimeOfSong.observeAsState()
    val currentTime = currentState.value ?: 0L
    val textStyle = MaterialTheme.typography.bodySmall
    val noRipple = object : RippleTheme {
        @Composable
        override fun defaultColor(): Color = Color.Unspecified

        @Composable
        override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
    }
    CompositionLocalProvider(LocalRippleTheme provides noRipple) {
        Slider(
            value = currentTime / Constants.durationOfSong.toFloat(),
            onValueChange = {})
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 40.dp)
    ) {
        val timer = (currentTime / 1000.0).roundToInt()
        val secsToTimer = if (timer < 10) "0$timer" else "$timer"
        Text(
            style = textStyle,
            text = "00:$secsToTimer",
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            style = textStyle,
            text = "00:${Constants.durationOfSong / 1000}"
        )
    }
}

@Composable
fun CardSongChoose(songModel: SongModel, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(CornerSize(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        SongInfo(
            songModel = songModel,
            modifier = Modifier
                .align(CenterHorizontally)
                .animateContentSize(),
            false
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SongInfo(songModel: SongModel, modifier: Modifier, isFullScreen: Boolean) {
    val imageModifier: Modifier
    val textNameStyle: TextStyle
    val textAuthorStyle: TextStyle
    if (isFullScreen) {
        imageModifier = modifier
            .padding(top = 40.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
            .size(234.dp)
            .background(MaterialTheme.colorScheme.onPrimaryContainer)
        textNameStyle = MaterialTheme.typography.labelLarge
        textAuthorStyle = MaterialTheme.typography.labelMedium
    } else {
        imageModifier = modifier
            .padding(8.dp)
            .size(96.dp)
            .background(MaterialTheme.colorScheme.onPrimaryContainer)
        textNameStyle = MaterialTheme.typography.bodyMedium
        textAuthorStyle = MaterialTheme.typography.bodySmall
    }
    GlideImage(
        model = songModel.urlImage,
        contentDescription = null,
        modifier = imageModifier
    )
    Text(
        text = songModel.name,
        style = textNameStyle,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp, start = 8.dp),
        textAlign = TextAlign.Center
    )
    Text(
        text = songModel.author,
        style = textAuthorStyle,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun TopBar(viewModel: PlayScreenViewModel) {
    val counter = viewModel.counter.observeAsState()
    val score = viewModel.score.observeAsState()
    Row {
        val txtMod = Modifier.padding(8.dp)
        Text(
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            text = "0:55",
            modifier = txtMod
        )
        Spacer(modifier = Modifier.weight(0.33f))
        Text(
            style = MaterialTheme.typography.bodyLarge, text = "${counter.value}/10",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = txtMod
        )
        Spacer(modifier = Modifier.weight(0.33f))
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = "${score.value}",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = txtMod
        )
    }
}

@Composable
fun BottomFullScreenCard(viewModel: PlayScreenViewModel, multiplier: Double, scores: Int) {
    Button(
        onClick = { viewModel.next() },
        shape = RoundedCornerShape(CornerSize(8.dp)),
        modifier = Modifier
            .padding(top = 30.dp, start = 60.dp, end = 60.dp, bottom = 30.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.next),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
    val msg = if (multiplier > 1) {
        stringResource(id = R.string.right_guess, multiplier)
    } else {
        stringResource(id = R.string.wrong_guess)
    }
    Text(
        text = msg,
        style = MaterialTheme.typography.labelLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
    Text(
        text = stringResource(id = R.string.scores, scores),
        style = MaterialTheme.typography.labelLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
    )
}




