package ru.mugss.domain

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.mugss.Constants
import ru.mugss.data.network.repository.TrackRepository
import ru.mugss.ui.model.SongModel

class TrackInteractor(trackRepository: TrackRepository) {
    private val list = Constants.listOfSongs
    private var currentTree = arrayListOf<SongModel>()

    fun getNextThree(): ArrayList<SongModel> {
        val arrayList = arrayListOf<SongModel>()
        val listShuffled = list.shuffled()
        arrayList.add(listShuffled[0])
        arrayList.add(listShuffled[1])
        arrayList.add(listShuffled[2])
        currentTree = arrayList
        return arrayList
    }

    fun getUrlGuessedSong(): String = currentTree.shuffled()[0].urlSong

    fun removeSong(songModel: SongModel) {
        list.remove(songModel)
    }
}