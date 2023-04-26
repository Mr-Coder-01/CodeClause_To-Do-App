package com.example.todolist


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Adapter.ToDoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var addTask: FloatingActionButton
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var taskAdapter: ToDoAdapter
    private lateinit var dbRef: DatabaseReference
    private lateinit var taskList: MutableList<TaskModel>
    private lateinit var signOutBTN: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var uid:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth= FirebaseAuth.getInstance()
        taskRecyclerView = findViewById(R.id.recyclerTasksView)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskList = mutableListOf()
        taskAdapter = ToDoAdapter(taskList)
        taskRecyclerView.adapter = taskAdapter
        uid = auth.currentUser?.uid.toString()
        dbRef = FirebaseDatabase.getInstance().getReference("Users")
        getTask()

        //Sign Out Button
        signOutBTN = findViewById(R.id.signOutBTN)
        signOutBTN.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        taskRecyclerView.setHasFixedSize(true)
        addTask = findViewById(R.id.newAddTaskBtn)
        addTask.setOnClickListener {
            val intent = Intent(this, NewTask::class.java)
            startActivity(intent)
        }



    }

    private fun getTask() {

            dbRef.child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    taskList.clear()
                    if (snapshot.exists()) {
                        for (taskSnap in snapshot.children) {
                            val toDoTaskData = taskSnap.getValue(TaskModel::class.java)
                            taskList.add(toDoTaskData!!)
                        }
                        taskAdapter.notifyDataSetChanged()

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
                }
            })

        }



}