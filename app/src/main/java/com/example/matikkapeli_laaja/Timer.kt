package com.example.matikkapeli_laaja

class MyTimer(val gameFragment: GameFragment) {
    var secondsCount = 0
    private lateinit var runnable: Runnable
    private var handler = android.os.Handler()
    fun startTimer() {
        runnable = Runnable {
            secondsCount++
            gameFragment.updateTimer(secondsCount)
            handler.postDelayed(runnable, 1000) // odotetaan sekunti ennen uutta kierrosta
        }
        handler.postDelayed(runnable, 1000) // Tämä käynnistää
    }
    fun stopTimer() {
        // Removes all pending posts of runnable from the handler's queue, effectively stopping the
        // timer
        handler.removeCallbacks(runnable)
    }
}