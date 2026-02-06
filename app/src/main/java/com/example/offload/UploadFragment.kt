package com.example.offload

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.offload.TaskViewModel

class UploadFragment : Fragment(R.layout.fragment_upload) {

    private var currentTaskMode = "Composite"
    private val usedTaskIds = mutableSetOf<String>()

    private val viewModel: TaskViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Initialize Views (Matching the new XML IDs)
        val btnComposite = view.findViewById<Button>(R.id.btnComposite)
        val btnComplex = view.findViewById<Button>(R.id.btnComplex)
        val etTaskId = view.findViewById<EditText>(R.id.etTaskId)
        val tvProcessId = view.findViewById<TextView>(R.id.tvProcessId) // Ensure this is TextView
        val cbLocal = view.findViewById<CheckBox>(R.id.cbLocal)
        val cbCloud = view.findViewById<CheckBox>(R.id.cbCloud)
        val btnBrowse = view.findViewById<ImageButton>(R.id.btnBrowse)
        val tvFileName = view.findViewById<TextView>(R.id.tvSelectedFileName)
        val btnUpload = view.findViewById<Button>(R.id.btnFinalUpload)

        // 2. Tab Selection Logic - Using backgroundTint instead of setBackgroundColor
        // This prevents the "Button becomes a square box" and crash issue.
        btnComposite.setOnClickListener {
            currentTaskMode = "Composite"
            btnComposite.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.teal_700)
            btnComplex.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey_shadow)
            Toast.makeText(context, "Mode: Images (Composite)", Toast.LENGTH_SHORT).show()
        }

        btnComplex.setOnClickListener {
            currentTaskMode = "Complex"
            btnComplex.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.teal_700)
            btnComposite.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.grey_shadow)
            Toast.makeText(context, "Mode: All Files (Complex)", Toast.LENGTH_SHORT).show()
        }

        // 3. Process ID Generator (Safe check)
        etTaskId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val id = s.toString()
                tvProcessId.text = if (id.isNotEmpty()) "$id-OFFLOAD-PROC" else "Generated-ID"
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 4. Checkbox Logic
        cbLocal.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) cbCloud.isChecked = false
        }
        cbCloud.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) cbLocal.isChecked = false
        }

        // 5. File Picker (Using theme-aware text color)
        val getFile = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                tvFileName.text = it.lastPathSegment
                // Use default theme text color instead of hardcoded Color.BLACK
                tvFileName.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.tab_indicator_text))
            }
        }

        btnBrowse.setOnClickListener {
            val mimeType = if (currentTaskMode == "Composite") "image/*" else "*/*"
            getFile.launch(mimeType)
        }

        // 6. Validation
        btnUpload.setOnClickListener {
            val taskId = etTaskId.text.toString().trim()
            val fileName = tvFileName.text.toString()
            val isTypeSelected = cbLocal.isChecked || cbCloud.isChecked

            when {
                taskId.isEmpty() -> etTaskId.error = "Task ID is required"
                usedTaskIds.contains(taskId) -> {
                    etTaskId.error = "ID already exists!"
                }
                !isTypeSelected -> Toast.makeText(context, "Select Task Type", Toast.LENGTH_SHORT).show()
                fileName == "No file selected" -> Toast.makeText(context, "Please select a file", Toast.LENGTH_SHORT).show()
                else -> {
                    usedTaskIds.add(taskId)
                    viewModel.addTask()
                    Toast.makeText(context, "Success: $taskId Uploaded", Toast.LENGTH_LONG).show()

                    // Reset
                    etTaskId.text.clear()
                    tvFileName.text = "No file selected"
                    cbLocal.isChecked = false
                    cbCloud.isChecked = false
                }
            }
        }
    }
}