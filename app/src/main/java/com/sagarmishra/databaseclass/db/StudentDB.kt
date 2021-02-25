package com.sagarmishra.databaseclass.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sagarmishra.databaseclass.dao.AuthenticateDAO
import com.sagarmishra.databaseclass.dao.StudentDAO
import com.sagarmishra.databaseclass.dao.UserDAO
import com.sagarmishra.databaseclass.entity.AuthenticateData
import com.sagarmishra.databaseclass.entity.Student
import com.sagarmishra.databaseclass.entity.User


@Database(

   entities = [(Student::class)],
    version = 5,
    exportSchema = false
)

abstract class StudentDB():RoomDatabase() {

    abstract fun getStudentDAO():StudentDAO
//    abstract fun getUserDAO():UserDAO
//    abstract fun getAuthenticateDAO() : AuthenticateDAO
    companion object
    {
        @Volatile
        private var instance : StudentDB? = null


        fun getInstance(context: Context):StudentDB?
        {
            if(instance == null)
            {
                synchronized(StudentDB::class){
                    instance = buildDataBase(context)
                }

            }

            return instance!!
        }


        private fun buildDataBase(context:Context):StudentDB{

            return Room.databaseBuilder(
                context.applicationContext,
                StudentDB::class.java,
                "College"
            ).fallbackToDestructiveMigration().build()
        }


    }
}