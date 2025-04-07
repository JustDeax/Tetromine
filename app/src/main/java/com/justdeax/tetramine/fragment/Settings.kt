package com.justdeax.tetramine.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.justdeax.tetramine.R
import com.justdeax.tetramine.databinding.FragmentSettingsBinding
import com.justdeax.tetramine.util.notAvailable

class Settings: Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notAvailable(requireContext(), getString(R.string.settings))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}