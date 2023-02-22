package ru.mugss.ui

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.NavController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import ru.mugss.ui.model.SongModel
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

@Composable
fun PlayScreen(navController: NavController<Screen>) {
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
                songModel = SongModel(
                    "Вина",
                    "Три дня дождя",
                    "https://images.genius.com/7194a28e504194114303d6fa0d38141c.1000x1000x1.jpg"
                ), modifier = modifier
            )
            CardSongChoose(
                songModel = SongModel(
                    "Где ты",
                    "Три дня дождя",
                    "https://dropmp3.net/uploads/posts/2021-03/1615381558_tri-dnya-dozhdya-gde-ty.jpg"
                ), modifier = modifier
            )
            CardSongChoose(
                songModel = SongModel(
                    "Отпускай",
                    "Три дня дождяlkokujkijujkjkjkhjkhujyghkuyghjbgyjbhjgyjhjbgyjvbhjjjjjjj",
                    "https://text-pesni.com/public/img/songs/565720743/1.jpg"
                ), modifier = modifier
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
                songModel = SongModel("???", "?"),
                modifier = Modifier.align(CenterHorizontally)
            )


        }
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioScreen() {
    val context = LocalContext.current
    val mediaItem =
        MediaItem.fromUri("https://p.scdn.co/mp3-preview/31423e2a42ea0cfd86fb71930ec51a3612135923?cid=774b29d4f13844c495f206cafdad9c86")
    val player = provideExoPlayer(context = context, mediaItem = mediaItem)

    LaunchedEffect(player) {
        player.prepare()
        player.playWhenReady = false
    }
}

fun provideExoPlayer(context: Context, mediaItem: MediaItem): ExoPlayer {
    val player = ExoPlayer.Builder(context).build()
    player.setMediaItem(mediaItem)
    return player
}

@Preview(showBackground = true)
@Composable
fun RadioScreenPreview() {
    RadioScreen()
}
