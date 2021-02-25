package com.sagarmishra.databaseclass.response

import com.sagarmishra.databaseclass.entity.Student

data class StudentDataResponse(val success:Boolean?=null,val data:MutableList<Student>) {
}