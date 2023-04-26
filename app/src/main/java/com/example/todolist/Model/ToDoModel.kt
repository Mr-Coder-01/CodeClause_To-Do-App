package com.example.todolist.Model

public class ToDoModel {
    private var id: Int = 0
    private var status: Int = 0
    private var task: String = ""


    public fun getId(): Int {
        return id
    }
    public fun setId(id: Int){
        this.id = id
    }

    public fun getStatus(): Int{
        return  status
    }

    public fun setStatus(status: Int) {
        this.status = status
    }

    public fun getTask(): String{
         return task
    }

    public fun setTask(task : String){
        this.task=task
    }

}