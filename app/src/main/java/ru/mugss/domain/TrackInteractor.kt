package ru.mugss.domain

import ru.mugss.data.network.repository.TrackRepositoryImpl
import ru.mugss.scopes.AppScope
import ru.mugss.ui.model.SongModel
import javax.inject.Inject


@AppScope
class TrackInteractor @Inject constructor(private val trackRepository: TrackRepositoryImpl) {
    private var list = mutableListOf<SongModel>()
    private var currentTree = arrayListOf<SongModel>()


    suspend fun refreshInteractor() {
        list = trackRepository.getTracks().toMutableList()
    }

    fun getNextThree(): ArrayList<SongModel> {
        val arrayList = arrayListOf<SongModel>()
        val listShuffled = list.shuffled()
        arrayList.add(listShuffled[0])
        arrayList.add(listShuffled[1])
        arrayList.add(listShuffled[2])
        currentTree = arrayList
        return arrayList
    }

    fun getGuessedSong(): SongModel = currentTree.shuffled()[0]

    fun removeSong(songModel: SongModel) {
        list.remove(songModel)
    }
}