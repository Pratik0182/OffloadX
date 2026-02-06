package com.example.offload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {
    private val _taskCount = MutableLiveData<Int>(0)
    val taskCount: LiveData<Int> = _taskCount

    fun addTask() {
        _taskCount.value = (_taskCount.value ?: 0) + 1
    }
}