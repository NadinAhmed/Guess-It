package com.nadin.guessit.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nadin.guessit.R
import com.nadin.guessit.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */

private lateinit var viewModel: GameViewModel

class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )

        Log.i("GameFragment", "GameViewModelProvider created")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventGameFinish.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished) {
                gameFinished()
                viewModel.onGameFinishedComplete()
            }
        }
        viewModel.buzz.observe(viewLifecycleOwner){
            if (it != BuzzType.NO_BUZZ) {
                buzz(it.pattern)
                viewModel.onBuzzComplete()
            }
        }

        return binding.root

    }

    private fun buzz(pattern: LongArray) {
        val buzzer = context?.getSystemService(Vibrator::class.java)

        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController().navigate(action)
        Toast.makeText(this.activity, "Game has finished", Toast.LENGTH_SHORT).show()
    }

}
