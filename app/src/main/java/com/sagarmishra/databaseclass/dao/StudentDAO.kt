package com.sagarmishra.databaseclass.dao

import androidx.room.*
import com.sagarmishra.databaseclass.entity.Student
import com.sagarmishra.databaseclass.entity.StudentDAOEntity

@Dao
interface StudentDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerStudent(student: MutableList<Student>)


//    @Query("select a.fname from Student a where a.fname LIKE :first")
//    suspend fun findByName(first:String):MuList<Student>

    @Query("select * from student")
    suspend fun findAll():MutableList<Student>
//
//    @Update
//    suspend fun updateStudent(student:Student)
//
//    @Delete
//    suspend fun deleteStudent(student:Student)
}