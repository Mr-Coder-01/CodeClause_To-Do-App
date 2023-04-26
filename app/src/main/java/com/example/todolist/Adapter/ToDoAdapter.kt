package com.example.todolist.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.TaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ToDoAdapter( private var todoList: List<TaskModel>) : RecyclerView.Adapter<ToDoAdapter.ItemViewHolder>() {
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var uid:String





    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.TaskCheckbox)
        val imageButton1: ImageButton = view.findViewById(R.id.deleteTaskBtn)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //Create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_task_layout, parent, false)
        return ItemViewHolder(adapterLayout)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = todoList[position]
        holder.checkBox.isChecked = toBoolean(item.taskStatus!!)
        holder.checkBox.text = item.taskAdded
        holder.imageButton1.setOnClickListener {
            val taskId= item.taskId
            delete(taskId!!)

        }

    }

    private fun delete(taskId: String) {
        auth= FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child(taskId)
        dbRef.removeValue()
    }
    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }


    override fun getItemCount() = todoList.size


}