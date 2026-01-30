package com.example.offload

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DownloadFragment : Fragment(R.layout.fragment_download) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Find the RecyclerView by ID from fragment_download.xml
        val rvFiles = view.findViewById<RecyclerView>(R.id.rvDownloadList)

        // 2. Create your Mock Data (The list of files)
        val files = listOf(
            // fileName, fileSize, fileDate, fileType
            FileModel("Project_Plan.pdf", "1.2 MB", "Oct 24, 2025", "pdf"),
            FileModel("Vacation.jpg", "4.5 MB", "Oct 22, 2025", "image"),
            FileModel("Budget.xlsx", "150 KB", "Oct 20, 2025", "doc")
        )

        // 3. Set the LayoutManager (Crucial: This is often why the screen is blank!)
        rvFiles.layoutManager = LinearLayoutManager(context)

        // 4. Attach the Adapter
        val adapter = FileAdapter(files)
        rvFiles.adapter = adapter
    }
}