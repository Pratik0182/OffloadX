package com.example.offload

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FileAdapter(private val fileList: List<FileModel>) :
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    // This class finds the views inside your item_file.xml
    class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFileName: TextView = itemView.findViewById(R.id.tvFileName)
        val tvFileSize: TextView = itemView.findViewById(R.id.tvFileSize)
        val btnDownload: Button = itemView.findViewById(R.id.btnDownload)
    }

    // This "inflates" the layout (turns the XML into a real UI object)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        return FileViewHolder(view)
    }

    // This puts the actual data (name/size) into the text fields
    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val currentFile = fileList[position]

        holder.tvFileName.text = currentFile.fileName
        holder.tvFileSize.text = currentFile.fileSize

        // Handle the download button click
        holder.btnDownload.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Starting download: ${currentFile.fileName}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Tells the list how many items to show
    override fun getItemCount(): Int {
        return fileList.size
    }
}