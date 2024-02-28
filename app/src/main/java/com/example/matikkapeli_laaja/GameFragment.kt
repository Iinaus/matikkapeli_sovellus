package com.example.matikkapeli_laaja

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.matikkapeli_laaja.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var timer: MyTimer

    private var gameType: Int = 0
    private var operator: String = "?"
    private val totalRounds: Int = 10
    private var round: Int = 1
    private var seconds: Int = 0
    private var points: Int = 0
    private var number1: Int = (1..10).random()
    private var number2: Int = (1..10).random()
    private var answer: Int = 0
    private var userAnswer: Int = 0
    private var timeSTR: String = "0:00"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        gameType = arguments?.getInt("gameType")?: 0

        //halutessaan alla olevalla uloskommentoidulla komennolla voisi piilottaa koko toolbarin
        //(requireActivity() as AppCompatActivity).supportActionBar?.hide()

        if(savedInstanceState != null) {
            round = savedInstanceState.getInt("round")
            points = savedInstanceState.getInt("points")
            number1 = savedInstanceState.getInt("number1")
            number2 = savedInstanceState.getInt("number2")
            answer = savedInstanceState.getInt("answer")
            seconds = savedInstanceState.getInt("seconds")
            timeSTR = savedInstanceState.getString("timeSTR")?: "0:00"
        }

        timer = MyTimer(this)
        timer.secondsCount = seconds

        chooseOperator()
        updateTexts()

        binding.buttonAnswer.setOnClickListener {
            val userAnswerSTR = binding.gameAnswer.text.toString()
            if (userAnswerSTR.isNotEmpty()) {
                userAnswer = userAnswerSTR.toInt()
                checkAnswer()
                if (round < totalRounds) {
                    round++
                    generateQuestion()
                } else if (round == totalRounds) {
                    updateTexts()
                    gameOver()
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.gameToast), Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    // piilotetaan options menu 1/2
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
    // piilotetaan options menu 2/2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("round", round)
        outState.putInt("points", points)
        outState.putInt("number1", number1)
        outState.putInt("number2", number2)
        outState.putInt("answer", answer)
        outState.putInt("points", points)
        outState.putInt("seconds", timer.secondsCount)
        outState.putString("timeSTR", timeSTR)
    }

    fun updateTimer(seconds: Int) {
        val minutes: Int = seconds / 60
        val remainingSeconds: Int = seconds % 60
        timeSTR = String.format("%2d:%02d", minutes, remainingSeconds)
        binding.gameTime.text = getString(R.string.gameTime, timeSTR)
    }

    override fun onResume() {
        super.onResume()
        timer.startTimer()
    }

    override fun onPause() {
        super.onPause()
        timer.stopTimer()
    }

    private fun chooseOperator() {
        if (gameType == 1) {
            operator = "+"
            answer = number1 + number2
        } else if (gameType == 2) {
            operator = "-"
            answer = number1 - number2
        }
    }

    private fun generateQuestion() {
        number1 = (1..10).random()
        number2 = (1..10).random()
        chooseOperator()
        updateTexts()
    }

    private fun checkAnswer() {
        if (userAnswer == answer) {
            points++
        }
    }

    private fun updateTexts() {
        binding.gameTime.text = getString(R.string.gameTime, timeSTR)
        binding.gameRounds.text = getString(R.string.gameRounds, round, totalRounds)
        binding.gamePoints.text = getString(R.string.gamePoints, points)
        binding.gameQuestion.text = getString(R.string.gameQuestion, number1, operator, number2)
        binding.gameAnswer.setText("")
    }

    private fun gameOver(){
        seconds = timer.secondsCount
        val toSummaryFragment = GameFragmentDirections.actionGameFragmentToSummaryFragment(points, seconds)
        findNavController().navigate(toSummaryFragment)
    }
}