package com.example.offload

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FileAdapter(private val fileList: List<FileModel>) :
    RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    // This class finds the views inside your item_file.xml
    // Change your ViewHolder class to look like this:
    class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Specify the type <TextView> or <ImageView> so findViewById works
        val tvFileName: TextView = itemView.findViewById(R.id.tvFileName)
        val tvFileSize: TextView = itemView.findViewById(R.id.tvFileSize)
        val tvFileDate: TextView = itemView.findViewById(R.id.tvFileDate)
        val ivFileIcon: ImageView = itemView.findViewById(R.id.ivFileIcon)
        val btnDownload: ImageButton = itemView.findViewById(R.id.btnDownload)
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
        holder.tvFileDate.text = currentFile.fileDate // Now this works!

        // Logic for setting the icon based on file type
        val iconRes = when (currentFile.fileType.lowercase()) {
            "pdf" -> android.R.drawable.ic_menu_edit // Standard Android PDF-like icon
            "image", "jpg", "png" -> android.R.drawable.ic_menu_gallery
            "video", "mp4" -> android.R.drawable.presence_video_online
            else -> android.R.drawable.ic_menu_save
        }

        holder.ivFileIcon.setImageResource(iconRes)

        holder.btnDownload.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Downloading ${currentFile.fileName}", Toast.LENGTH_SHORT).show()
        }
    }

    // Tells the list how many items to show
    override fun getItemCount(): Int {
        return fileList.size
    }
}