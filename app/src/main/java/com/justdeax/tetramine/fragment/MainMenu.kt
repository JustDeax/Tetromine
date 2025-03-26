package com.justdeax.tetramine.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

//        binding.myButton.setOnClickListener {
//            Toast.makeText(requireContext(), "!", Toast.LENGTH_SHORT).show()
//        }

//        btnStart.setOnClickListener {
//            val intent = Intent(requireContext(), GameActivity::class.java)
//            startActivity(intent)
//        }
//
//        btnLevel.setOnClickListener {
//            findNavController().navigate(R.id.action_mainMenu_to_levelSelect)
//        }
//
//        btnSettings.setOnClickListener {
//            findNavController().navigate(R.id.action_mainMenu_to_settings)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}