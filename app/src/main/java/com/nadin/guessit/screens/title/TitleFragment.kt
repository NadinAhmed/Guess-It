package com.nadin.guessit.screens.title

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nadin.guessit.R
import com.nadin.guessit.databinding.TitleFragmentBinding

/**
 * Fragment for the starting or title screen of the app
 */
class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: TitleFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.title_fragment, container, false
        )

        val viewModel = ViewModelProvider(this).get(TitleViewModel::class.java)
        binding.titleViewModel = viewModel

        viewModel.eventPlayGame.observe(viewLifecycleOwner) {
            Log.i("TitleFragment", "eventPlayGame:$it")
            if (it) {
                navigateToGamePlay()
                viewModel.onPlayGameFinished()
            }
        }


        return binding.root
    }

    private fun navigateToGamePlay() {
        findNavController().navigate(TitleFragmentDirections.actionTitleToGame())
    }
}