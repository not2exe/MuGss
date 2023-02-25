package ru.mugss.ui.stateholders

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mugss.Constants
import ru.mugss.data.network.repository.TrackRepositoryImpl
import ru.mugss.domain.TrackInteractor
import ru.mugss.ui.model.SongModel

class PlayScreenViewModel : ViewModel() {
    val interactor = TrackInteractor(TrackRepositoryImpl())
    val counter = MutableLiveData(1)
    val score = MutableLiveData(0)
    val currentTimeOfSong = MutableLiveData(0L)
    var timeOfGameToEnd = MutableLiveData(Constants.playTime)
    lateinit var timer: CountDownTimer
    lateinit var timerOfSong: CountDownTimer
    val currentSongs = MutableLiveData<ArrayList<SongModel>>(interactor.getNextThree())
    val urlOfSongToGuess = MutableLiveData(interactor.getUrlGuessedSong())

    fun resumeSongTimer(timeOfEnd: Long) {
        timerOfSong = object : CountDownTimer(Constants.durationOfSong - timeOfEnd, 10) {
            override fun onTick(timeToEnd: Long) {
                currentTimeOfSong.value = Constants.durationOfSong - timeToEnd
            }
            override fun onFinish() {}
        }
        timerOfSong.start()
    }

    fun pauseSongTimer() {
        timerOfSong.cancel()
    }

    fun resumeGameTimer() {
        timer = object : CountDownTimer(timeOfGameToEnd.value ?: Constants.playTime, 10) {
            override fun onTick(timeToEnd: Long) {
                timeOfGameToEnd.value = timeToEnd
            }

            override fun onFinish() {
            }
        }
        timer.start()
    }

    fun pauseGameTimer() {
        timer.cancel()
    }

    fun handleClick(song: SongModel) {
        if (song.urlSong == urlOfSongToGuess.value) {
            score.value = score.value?.plus(Constants.valToIncrease)
            interactor.removeSong(song)
        } else {
            score.value = score.value?.minus(Constants.valToDecrease)
        }
        currentSongs.value = interactor.getNextThree()
        urlOfSongToGuess.value = interactor.getUrlGuessedSong()
        counter.value = counter.value?.plus(1)
    }
}