package com.example.offload

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnShowChangePass = view.findViewById<Button>(R.id.btnShowChangePass)
        val layoutChangePass = view.findViewById<LinearLayout>(R.id.layoutChangePass)

        val etCurrent = view.findViewById<EditText>(R.id.etCurrentPass)
        val etNew = view.findViewById<EditText>(R.id.etNewPass)
        val etConfirm = view.findViewById<EditText>(R.id.etConfirmNewPass)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdatePass)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // Toggle Password Fields
        btnShowChangePass.setOnClickListener {
            if (layoutChangePass.visibility == View.GONE) {
                layoutChangePass.visibility = View.VISIBLE
                btnShowChangePass.text = "Change Password"
            } else {
                layoutChangePass.visibility = View.GONE
                btnShowChangePass.text = "Change Password?"
            }
        }

        // Validation Logic
        btnUpdate.setOnClickListener {
            val newPass = etNew.text.toString()
            val confirmPass = etConfirm.text.toString()

            if (newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (newPass != confirmPass) {
                etConfirm.error = "Passwords do not match"
            } else {
                Toast.makeText(context, "Password Changed!", Toast.LENGTH_SHORT).show()
                layoutChangePass.visibility = View.GONE
                btnShowChangePass.text = "Change Password?"
            }
        }

        btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}