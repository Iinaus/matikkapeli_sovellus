package com.example.matikkapeli_laaja

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.matikkapeli_laaja.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {

    private lateinit var binding: FragmentSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)

        val points: Int = arguments?.getInt("points")?: 0
        val seconds: Int = arguments?.getInt("seconds")?: 0

        val minutes: Int = seconds / 60
        val remainingSeconds: Int = seconds % 60
        val timeSTR: String = String.format("%2d:%02d", minutes, remainingSeconds)

        binding.summaryText.text = resources.getString(R.string.summary_text, points, timeSTR)

        binding.buttonPlayAgain.setOnClickListener {
            val toStartFragment = SummaryFragmentDirections.actionSummaryFragmentToStartFragment()
            findNavController().navigate(toStartFragment)
        }

        return binding.root
    }
}