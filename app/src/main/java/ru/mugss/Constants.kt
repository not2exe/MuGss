package ru.mugss

import android.graphics.drawable.Drawable
import androidx.compose.ui.res.stringResource
import ru.mugss.ui.model.Playlist

object Constants {
    const val normalMode = "Обычный"
    const val animeMode = "Аниме"
    val list = listOf<Playlist>(
        Playlist(normalMode, R.drawable.baseline_play_arrow_96), Playlist(
            animeMode, R.drawable.baseline_play_arrow_96
        )
    )
}