package com.example.todolist.Sub

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.google.errorprone.annotations.Keep
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.IgnoreExtraProperties

class TaskViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    val tasks: SnapshotStateList<Task> = mutableStateListOf()

    init {
        fetchTasks()
    }

    fun addTask(title: String, dateTime: String, category: String) {
        val task = Task(title, dateTime, category)
        tasks.add(task)
        db.collection("tasks").add(task)
    }

    fun deleteTask(task: Task) {
        tasks.remove(task)
        db.collection("tasks").whereEqualTo("title", task.title)
            .whereEqualTo("dateTime", task.dateTime).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("tasks").document(document.id).delete()
                }
            }
    }

    fun updateTask(oldTask: Task, newTitle: String, newDateTime: String) {
        val taskIndex = tasks.indexOf(oldTask)
        if (taskIndex != -1) {
            val newTask = oldTask.copy(title = newTitle, dateTime = newDateTime)
            tasks[taskIndex] = newTask

            db.collection("tasks").whereEqualTo("title", oldTask.title)
                .whereEqualTo("dateTime", oldTask.dateTime).get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        db.collection("tasks").document(document.id).set(newTask)
                    }
                }
        }
    }

    private fun fetchTasks() {
        db.collection("tasks").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val task = document.toObject(Task::class.java)
                    tasks.add(task)
                }
            }
    }
}

@Keep
@IgnoreExtraProperties
data class Task(
    var title: String = "",
    var dateTime: String = "",
    var category: String = "",
) {
    constructor() : this("", "", "")
}
