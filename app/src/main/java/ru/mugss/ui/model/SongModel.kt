package ru.mugss.ui.model

data class SongModel(
    val name: String,
    val author: String,
    val urlImage: String? = null,
    val urlSong: String
)
