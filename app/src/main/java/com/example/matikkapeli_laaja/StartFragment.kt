package com.example.matikkapeli_laaja

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.matikkapeli_laaja.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    private var gameType: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = null

        binding.buttonPlus.setOnClickListener {
            gameType = 1
            startGame()
        }

        binding.buttonMinus.setOnClickListener {
            gameType = 2
            startGame()
        }

        return binding.root
    }

    private fun startGame() {
        val toGameActivity = StartFragmentDirections.actionStartFragmentToGameFragment(gameType)
        findNavController().navigate(toGameActivity)
    }
}