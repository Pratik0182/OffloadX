package com.example.offload

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class StatsFragment : Fragment(R.layout.fragment_stats) {

    // This 'by activityViewModels()' is the secret sauce.
    // it connects this fragment to the same TaskViewModel used in UploadFragment.
    private val viewModel: TaskViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Link to the TextView in your fragment_stats.xml
        val tvStatsCount = view.findViewById<TextView>(R.id.tvStatsCount)

        // This 'observes' the taskCount.
        // Every time you hit "Upload" in the other screen, this code triggers
        // and updates the text on this screen automatically.
        viewModel.taskCount.observe(viewLifecycleOwner) { count ->
            tvStatsCount.text = count.toString()
        }
    }
}