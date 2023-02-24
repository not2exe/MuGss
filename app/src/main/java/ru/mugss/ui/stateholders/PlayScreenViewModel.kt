package ru.mugss.ui.stateholders

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.mugss.Constants

class PlayScreenViewModel : ViewModel() {
    val sliderValue = MutableLiveData(0f)
    var timeOfSongToEnd = Constants.durationOfSong
    var timerOfSong =
        object : CountDownTimer(Constants.durationOfSong, 1) {
            override fun onTick(timeToEnd: Long) {
                changeSliderValue(timeToEnd)
                timeOfSongToEnd = timeToEnd
            }

            override fun onFinish() {
                timeOfSongToEnd = Constants.durationOfSong
            }
        }
    val currentSongs = MutableLiveData(
        arrayOf(
            Constants.listOfSongs[0],
            Constants.listOfSongs[1],
            Constants.listOfSongs[2]
        )
    )
    val songToGuess = MutableLiveData(Constants.listOfSongs[0].urlSong)


    fun resume() {
        timerOfSong = object : CountDownTimer(timeOfSongToEnd, 1) {
            override fun onTick(timeToEnd: Long) {
                changeSliderValue(timeToEnd)
                timeOfSongToEnd = timeToEnd
            }

            override fun onFinish() {
                timeOfSongToEnd = Constants.durationOfSong
            }
        }
        timerOfSong.start()
    }

    fun pause() {
        timerOfSong.cancel()
    }


    private fun changeSliderValue(timeToEnd: Long, timeAll: Long = Constants.durationOfSong) {
        sliderValue.value = (timeAll - timeToEnd).toFloat() / timeAll.toFloat()
    }

}