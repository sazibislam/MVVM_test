package com.sazib.ksl.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sazib.ksl.databinding.TestFragmentBinding

class TestFragment : Fragment() {

    private var _binding: TestFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): TestFragment {
            return TestFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TestFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setupView() {
        binding.tvTest.text = "hello text"

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}