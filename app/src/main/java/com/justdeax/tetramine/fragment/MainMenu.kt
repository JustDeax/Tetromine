package com.justdeax.tetramine.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.justdeax.tetramine.R
import com.justdeax.tetramine.databinding.FragmentMainMenuBinding

class MainMenu : Fragment() {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        play.setOnClickListener {
//            val intent = Intent(requireContext(), GameActivity::class.java)
//            startActivity(intent)
//        }

        binding.playLevels.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_levels)
        }

        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenu_to_settings)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}