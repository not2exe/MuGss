package ru.mugss.ui

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import ru.mugss.ui.model.SongModel
import android.content.Context
import android.os.CountDownTimer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.common.base.Stopwatch
import ru.mugss.Constants
import ru.mugss.ui.stateholders.PlayScreenViewModel
import java.util.Timer

@Composable
fun PlayScreen(navController: NavController<Screen>) {
    val viewModel = PlayScreenViewModel()
    val songs = viewModel.currentSongs.observeAsState()
    val guessedSongUrl = viewModel.songToGuess.observeAsState()
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Row(Modifier.height(100.dp)) {}
        Row {
            val modifier = Modifier
                .weight(1f)
                .padding(8.dp)
            CardSongChoose(
                songModel = songs.value?.get(0) ?: return, modifier = modifier
            )
            CardSongChoose(
                songModel = songs.value?.get(1) ?: return, modifier = modifier
            )
            CardSongChoose(
                songModel = songs.value?.get(2) ?: return, modifier = modifier
            )
        }
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
                    urlSong = guessedSongUrl.value ?: ""
                ),
                modifier = Modifier.align(CenterHorizontally)
            )
            SongPlayer(
                guessedSongUrl.value ?: "", Modifier.align(CenterHorizontally),
                PlayScreenViewModel()
            )
        }
    }

}


@Composable
fun SongPlayer(url: String, modifier: Modifier, viewModel: PlayScreenViewModel) {
    val context = LocalContext.current
    val sliderValue = viewModel.sliderValue.observeAsState()
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(
                MediaItem.fromUri(
                    url
                )
            )
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_IDLE -> {
                            viewModel.pause()
                        }
                        Player.STATE_BUFFERING -> {
                            viewModel.pause()
                        }
                        Player.STATE_READY -> {
                            viewModel.resume()
                        }
                        Player.STATE_ENDED -> {}
                    }
                    super.onPlaybackStateChanged(playbackState)
                }

            })
            prepare()
            playWhenReady = true
        }
    }
    Slider(value = sliderValue.value ?: 0f, onValueChange = {})
    DisposableEffect(key1 = Unit) { onDispose { exoPlayer.release() } }
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




