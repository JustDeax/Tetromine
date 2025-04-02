package com.justdeax.tetramine.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.justdeax.tetramine.R
import com.justdeax.tetramine.databinding.FragmentChooseModeBinding

class ChooseMode : Fragment() {
    private var _binding: FragmentChooseModeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseModeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.classic.setOnClickListener {
            notAvailable(getString(R.string.classic_mode))
        }
        binding.practice.setOnClickListener {
            notAvailable(getString(R.string.practice_mode))
        }
        binding.sprint.setOnClickListener {
            notAvailable(getString(R.string.sprint_mode))
        }
        binding.modern.setOnClickListener {
            notAvailable(getString(R.string.modern_mode))
        }
    }

    private fun notAvailable(mode: String) {
        Toast.makeText(
            activity, mode + " " + getString(R.string.not_available), Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}