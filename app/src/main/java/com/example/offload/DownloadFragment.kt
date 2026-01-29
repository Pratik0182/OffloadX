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
            FileModel("Project_Offload.pdf", "2.4 MB"),
            FileModel("Final_Presentation.pptx", "5.1 MB"),
            FileModel("App_Screenshot.png", "800 KB"),
            FileModel("User_Manual.txt", "15 KB"),
            FileModel("Development_Build.apk", "24.0 MB")
        )

        // 3. Set the LayoutManager (Crucial: This is often why the screen is blank!)
        rvFiles.layoutManager = LinearLayoutManager(context)

        // 4. Attach the Adapter
        val adapter = FileAdapter(files)
        rvFiles.adapter = adapter
    }
}