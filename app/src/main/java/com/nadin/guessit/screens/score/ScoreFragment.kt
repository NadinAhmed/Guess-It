package com.nadin.guessit.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nadin.guessit.R
import com.nadin.guessit.databinding.ScoreFragmentBinding

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.score_fragment,
            container,
            false
        )

        // Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()

        val viewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)


        binding.scoreViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventPlayAgain.observe(viewLifecycleOwner) {
            if (it) {
                onPlayAgain()
                viewModel.onPlayAgainFinished()
            }
        }

        return binding.root
    }

    private fun onPlayAgain() {
        findNavController().navigate(ScoreFragmentDirections.actionRestart())
    }
}
