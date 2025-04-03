package com.justdeax.tetramine.fragment
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.justdeax.tetramine.databinding.FragmentAboutGameBinding
import androidx.core.net.toUri
import com.justdeax.tetramine.R

class AboutGame : Fragment() {
    private var _binding: FragmentAboutGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val versionString = getString(R.string.version) + "  Check Updates"
            versionText.text = versionString

            val authorString = getString(R.string.author) + " github.com/JustDeax"
            authorText.text = authorString

            val sourcecodeString = getString(R.string.source_code) + " github.com/JustDeax/Tetromine"
            sourcecodeText.text = sourcecodeString

            version.setOnClickListener { openLink("https://github.com/JustDeax/Tetromine/releases") }
            author.setOnClickListener { openLink("https://github.com/JustDeax") }
            sourcecode.setOnClickListener { openLink("https://github.com/JustDeax/Tetromine") }
        }
    }

    private fun openLink(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}