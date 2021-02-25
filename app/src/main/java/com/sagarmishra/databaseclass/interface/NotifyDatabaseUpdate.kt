package com.sagarmishra.databaseclass.`interface`

import com.sagarmishra.databaseclass.entity.Student

interface NotifyDatabaseUpdate {
    fun notifyUpdate(newData : List<Student>)
}