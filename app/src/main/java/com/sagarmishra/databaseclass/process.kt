package com.sagarmishra.databaseclass

import com.sagarmishra.databaseclass.model.StudentModel

var studentList: MutableList<StudentModel> =
    mutableListOf(StudentModel("Sagar Mishra", "Swyambhu", 21, "","Male"),StudentModel("Sagar Mishra", "Swyambhu", 21, "","Female"))


fun main()
{
    var a : MutableList<String> = mutableListOf("Sagar","absek")
    var b:MutableList<String> = mutableListOf()
    b.addAll(a)
    b.remove("Sagar")
    println(a)
}
