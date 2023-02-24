package ru.mugss.data.network.repository

import ru.mugss.ui.model.SongModel

interface TrackRepository {

    suspend fun getTracks(): List<SongModel>

}