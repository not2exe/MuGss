package ru.mugss.ui

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.Player
import ru.mugss.Constants
import ru.mugss.ui.stateholders.PlayScreenViewModel
import kotlin.math.roundToInt

@Composable
fun PlayScreen(navController: NavController<Screen>) {
    val viewModel = remember {
        PlayScreenViewModel()
    }
    val songs = viewModel.currentSongs.observeAsState()
    val guessedSongUrl = viewModel.urlOfSongToGuess.observeAsState()
    if ((songs.value?.size ?: 0) == 0) return
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        TopBar(viewModel = viewModel)
        Spacer(modifier = Modifier.size(50.dp))
        Row() {
            val firstSong = songs.value?.get(0) ?: return
            val secondSong = songs.value?.get(1) ?: return
            val thirdSong = songs.value?.get(2) ?: return
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
        Card(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 32.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(CornerSize(8.dp)),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            SongInfo(
                songModel = SongModel(
                    name = Constants.unknownNameOfSong,
                    author = Constants.unknownAuthorOfSong,
                    urlSong = ""
                ),
                modifier = Modifier.align(CenterHorizontally)
            )
            SongPlayer(guessedSongUrl.value ?: "", viewModel)
            SliderLayout(viewModel)
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
            style = MaterialTheme.typography.bodySmall,
            text = "00:$secsToTimer"
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            style = MaterialTheme.typography.bodySmall,
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
        SongInfo(songModel = songModel, modifier = Modifier.align(CenterHorizontally))
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SongInfo(songModel: SongModel, modifier: Modifier) {
    if (songModel.urlImage != null) {
        GlideImage(
            model = songModel.urlImage,
            contentDescription = null,
            modifier = modifier
                .padding(8.dp)
                .size(96.dp)
        )
    } else {
        Box(
            modifier = modifier
                .size(96.dp)
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
    Text(
        text = songModel.name,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 8.dp, start = 8.dp),
        textAlign = TextAlign.Center
    )
    Text(
        text = songModel.author,
        style = MaterialTheme.typography.bodySmall,
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
    val timer = viewModel.timeOfGameToEnd.observeAsState()
    Row {
        val txtMod = Modifier.padding(8.dp)
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = "0:${timer.value}",
            modifier = txtMod
        )
        Spacer(modifier = Modifier.weight(0.33f))
        Text(
            style = MaterialTheme.typography.bodyLarge, text = "${counter.value}/10",
            modifier = txtMod
        )
        Spacer(modifier = Modifier.weight(0.33f))
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = "${score.value}",
            modifier = txtMod
        )
    }
}




