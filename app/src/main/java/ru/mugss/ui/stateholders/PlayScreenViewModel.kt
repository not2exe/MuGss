package ru.mugss.ui.stateholders

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.mugss.Constants
import ru.mugss.domain.TrackInteractor
import ru.mugss.ui.model.SongModel
import kotlin.math.roundToInt

class PlayScreenViewModel(private val interactor: TrackInteractor) : ViewModel() {
    val counter = MutableLiveData(1)
    val score = MutableLiveData(0)
    val currentTimeOfSong = MutableLiveData(0L)
    val currentSongs = MutableLiveData<ArrayList<SongModel>>()
    val urlOfSongToGuess = MutableLiveData<SongModel>()
    val isFullScreen = MutableLiveData(false)
    val multiplier = MutableLiveData(1.0)
    private lateinit var timer: CountDownTimer
    private lateinit var timerOfSong: CountDownTimer

    init {
        viewModelScope.launch {
            interactor.refreshInteractor()
            currentSongs.value = interactor.getNextThree()
            urlOfSongToGuess.value = interactor.getGuessedSong()
        }
    }


    fun resumeSongTimer(timeOfEnd: Long) {
        timerOfSong = object : CountDownTimer(Constants.durationOfSong - timeOfEnd, 10) {
            override fun onTick(timeToEnd: Long) {
                currentTimeOfSong.value = Constants.durationOfSong - timeToEnd
            }

            override fun onFinish() {
                if (isFullScreen.value == true) {
                    next()
                }
            }
        }
        timerOfSong.start()
    }

    fun pauseSongTimer() {
        timerOfSong.cancel()
    }

    fun resumeGameTimer() {
    }

    fun pauseGameTimer() {
    }

    fun handleClick(song: SongModel) {
        if (song == urlOfSongToGuess.value) {
            score.value =
                score.value?.plus(
                    (Constants.valToIncrease * (multiplier.value ?: 1.0)).roundToInt()
                )
            multiplier.value = multiplier.value?.plus(Constants.valToIncreaseMultiplier)
            interactor.removeSong(song)
        } else {
            score.value = score.value?.minus(Constants.valToDecrease)
            multiplier.value = 1.0
        }
        isFullScreen.value = true

        counter.value = counter.value?.plus(1)
    }

    fun next() {
        currentSongs.value = interactor.getNextThree()
        urlOfSongToGuess.value = interactor.getGuessedSong()
        isFullScreen.value = false
    }
}