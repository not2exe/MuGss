package ru.mugss.ui.stateholders

import android.os.CountDownTimer
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayScreenViewModel : ViewModel() {
    var timer =
        object : CountDownTimer(30000, 1) {
            override fun onTick(p0: Long) {
                changeSliderValue(p0, 30000)
            }

            override fun onFinish() {}
        }
    val sliderValue = MutableLiveData(0f)


    fun resume(){
        timer = object:CountDownTimer(1,1){
            override fun onTick(p0: Long) {
                changeSliderValue(p0, 30000)
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }

        }

    }
    fun pause(){

    }


    private fun changeSliderValue(timeToEnd: Long, timeAll: Long) {
        sliderValue.value = (timeAll - timeToEnd).toFloat() / timeAll.toFloat()
    }

}