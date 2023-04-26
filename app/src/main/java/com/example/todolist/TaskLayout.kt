package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TaskLayout : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_layout)
//         val imageBtn: ImageButton=findViewById(R.id.editTaskBtn)
//         imageBtn.setOnClickListener {
//             Toast.makeText(MainActivity::class.java, "This worked", Toast.LENGTH_SHORT).show()
//         }
    }
}