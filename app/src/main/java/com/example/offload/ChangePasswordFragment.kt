package com.example.offload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.offload.databinding.FragmentChangePasswordBinding // Ensure this import is here

class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // FIX for 'inflate': Use this specific signature for Fragments
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        // FIX for 'root': binding.root is the top-level view of your XML
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FIX for 'btnUpdatePassword' and 'etNewPassword':
        // These will be green ONLY IF the IDs in your XML match exactly.
        binding.btnUpdatePassword.setOnClickListener {
            val newPass = binding.etNewPassword.text.toString()

            if (newPass.isNotEmpty()) {
                Toast.makeText(requireContext(), "Password Updated!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter a password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}