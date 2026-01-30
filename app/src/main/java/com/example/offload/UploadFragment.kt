package com.example.offload

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class UploadFragment : Fragment(R.layout.fragment_upload) {

    // State management
    private var currentTaskMode = "Composite"
    private val usedTaskIds = mutableSetOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Initialize Views
        val btnComposite = view.findViewById<Button>(R.id.btnComposite)
        val btnComplex = view.findViewById<Button>(R.id.btnComplex)
        val etTaskId = view.findViewById<EditText>(R.id.etTaskId)
        val tvProcessId = view.findViewById<TextView>(R.id.tvProcessId)
        val cbLocal = view.findViewById<CheckBox>(R.id.cbLocal)
        val cbCloud = view.findViewById<CheckBox>(R.id.cbCloud)
        val btnBrowse = view.findViewById<ImageButton>(R.id.btnBrowse)
        val tvFileName = view.findViewById<TextView>(R.id.tvSelectedFileName)
        val btnUpload = view.findViewById<Button>(R.id.btnFinalUpload)

        // 2. Tab Selection Logic (Composite vs Complex)
        btnComposite.setOnClickListener {
            currentTaskMode = "Composite"
            btnComposite.setBackgroundColor(Color.parseColor("#26A69A"))
            btnComplex.setBackgroundColor(Color.parseColor("#BDBDBD"))
            Toast.makeText(context, "Mode: Images (Composite)", Toast.LENGTH_SHORT).show()
        }

        btnComplex.setOnClickListener {
            currentTaskMode = "Complex"
            btnComplex.setBackgroundColor(Color.parseColor("#26A69A"))
            btnComposite.setBackgroundColor(Color.parseColor("#BDBDBD"))
            Toast.makeText(context, "Mode: All Files (Complex)", Toast.LENGTH_SHORT).show()
        }

        // 3. System-generated Process ID (Updates as user types)
        etTaskId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val id = s.toString()
                tvProcessId.text = if (id.isNotEmpty()) "$id-OFFLOAD-PROC" else "Generated-ID"
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 4. Single Selection Checkbox Logic (Acts like Radio Buttons)
        cbLocal.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) cbCloud.isChecked = false
        }
        cbCloud.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) cbLocal.isChecked = false
        }

        // 5. File Picker Launcher
        val getFile = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                tvFileName.text = it.lastPathSegment
                tvFileName.setTextColor(Color.BLACK)
            }
        }

        btnBrowse.setOnClickListener {
            val mimeType = if (currentTaskMode == "Composite") "image/*" else "*/*"
            getFile.launch(mimeType)
        }

        // 6. Final Upload & Validation Logic
        btnUpload.setOnClickListener {
            val taskId = etTaskId.text.toString().trim()
            val fileName = tvFileName.text.toString()
            val isTypeSelected = cbLocal.isChecked || cbCloud.isChecked

            when {
                taskId.isEmpty() -> etTaskId.error = "Task ID is required"
                usedTaskIds.contains(taskId) -> {
                    etTaskId.error = "ID already exists!"
                    Toast.makeText(context, "Please use a unique Task ID", Toast.LENGTH_SHORT).show()
                }
                !isTypeSelected -> Toast.makeText(context, "Select Task Type (Local/Cloud)", Toast.LENGTH_SHORT).show()
                fileName == "No file selected" -> Toast.makeText(context, "Please browse and select a file", Toast.LENGTH_SHORT).show()
                else -> {
                    // Success Case
                    usedTaskIds.add(taskId)
                    Toast.makeText(context, "Success! Task $taskId Uploaded", Toast.LENGTH_LONG).show()

                    // Reset UI for next task
                    etTaskId.text.clear()
                    tvFileName.text = "No file selected"
                    cbLocal.isChecked = false
                    cbCloud.isChecked = false
                }
            }
        }
    }
}