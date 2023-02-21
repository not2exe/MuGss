package ru.mugss.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Screen : Parcelable {
    @Parcelize
    object StartScreen : Screen()

    @Parcelize
    object PlayScreen : Screen()

    @Parcelize
    object EndScreen : Screen()
}
