package com.example.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class NewTask : AppCompatActivity() {
    private lateinit var newTaskAdd: EditText
    private lateinit var saveBtn: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        newTaskAdd= findViewById(R.id.newTask)
        saveBtn= findViewById(R.id.newTaskBtn)
        auth=FirebaseAuth.getInstance()
        dbRef= FirebaseDatabase.getInstance().getReference("Users")



        saveBtn.setOnClickListener{
          saveTask()
      }
    }



    private fun saveTask() {
        //getting the task

        val task = newTaskAdd.text.toString()

        if (task.isEmpty()) {
            newTaskAdd.error = " Please Enter the task"
        }

        if (task.isNotEmpty()) {
            val uid= auth.currentUser?.uid.toString()
            val taskId=dbRef.push().key!!
            val taskStatus=0
            val tasks = TaskModel(taskId,task, taskStatus)

            dbRef.child(uid).child(taskId).setValue(tasks).addOnCompleteListener {
                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()

                newTaskAdd.text.clear()

            }.addOnFailureListener { err ->
                Toast.makeText(this, "ERROR ${err.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}