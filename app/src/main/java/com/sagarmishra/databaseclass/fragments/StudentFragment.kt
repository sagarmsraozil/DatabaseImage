package com.sagarmishra.databaseclass.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sagarmishra.databaseclass.LoginActivity
import com.sagarmishra.databaseclass.R
import com.sagarmishra.databaseclass.`interface`.NotifyDatabaseUpdate
import com.sagarmishra.databaseclass.adapter.StudentAdapter
import com.sagarmishra.databaseclass.api.ServiceBuilder
import com.sagarmishra.databaseclass.db.Database
import com.sagarmishra.databaseclass.db.StudentDB
//import com.sagarmishra.databaseclass.db.StudentDB
import com.sagarmishra.databaseclass.entity.Student
import com.sagarmishra.databaseclass.entity.User
import com.sagarmishra.databaseclass.repository.StudentRepository
import com.sagarmishra.databaseclass.studentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

@Suppress("DEPRECATION")
class StudentFragment() : Fragment(),View.OnClickListener{
   private lateinit var recycler : RecyclerView
    private lateinit var adapter : StudentAdapter
    var lstStudents :MutableList<Student> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_student, container, false)

        binding(view)
        initialize(context!!)
        listener()

        return view
    }

    private fun binding(v:View?)
    {
        recycler = v!!.findViewById(R.id.recycler)

    }

    private fun listener()
    {

    }

    private fun initialize(context:Context)
    {
        val notificationChannel = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel("My notification","My notification",NotificationManager.IMPORTANCE_DEFAULT)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
        val notificationInstance = NotificationCompat.Builder(context,"My notification")
            .setSmallIcon(android.R.drawable.arrow_up_float)
            .setContentTitle("Futsal Arena")
            .setContentText("Happy Birthday!!")
            .setAutoCancel(true)
        notificationManager.notify(1,notificationInstance.build())
//        val pendingIntent : PendingIntent = PendingIntent.getActivity(context,0, Intent(context,LoginActivity::class.java),0)
//        notificationInstance.setContentIntent(pendingIntent)


//        var notificationManager:NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        var importance:Int = NotificationManager.IMPORTANCE_LOW;
//        val notificationChannel =  NotificationChannel(SyncStateContract.Constants.NOTIFICATION_CHANNEL_ID, SyncStateContract.Constants.NOTIFICATION_CHANNEL_NAME, importance);
//        notificationChannel.enableLights(true);
//        notificationChannel.setLightColor(Color.RED);
//        notificationChannel.enableVibration(true);
//
//        notificationManager.createNotificationChannel(notificationChannel);
//        notificationManager.notify(0,notificationInstance.build())



        CoroutineScope(Dispatchers.IO).launch {
            val instance = StudentDB.getInstance(context)
            try {
                val repo = StudentRepository()

                val response = repo.retrieveStudents()
                if(response.success == true)
                {
                    lstStudents = response.data
                    withContext(Dispatchers.Main)
                    {

                        instance!!.getStudentDAO().registerStudent(lstStudents)
                        adapter = StudentAdapter(context,lstStudents)
                        recycler.adapter = adapter
                        recycler.layoutManager = LinearLayoutManager(context)
                    }
                }
                else
                {
                    withContext(Dispatchers.Main)
                    {
                        Toast.makeText(context, "No data", Toast.LENGTH_SHORT).show()
                        println("No data")
                    }
                }
            }
            catch (ex:Exception)
            {
                withContext(Dispatchers.Main)
                {

                    if(ServiceBuilder.offline == true)
                    {
                        lstStudents = instance!!.getStudentDAO().findAll()
                        adapter = StudentAdapter(context,lstStudents)
                        recycler.adapter = adapter
                        recycler.layoutManager = LinearLayoutManager(context)
                    }
                    else
                    {
                        Toast.makeText(context, "${ex.toString()}", Toast.LENGTH_SHORT).show()
                        println(ex.printStackTrace())
                    }
                }
            }
        }


    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {

        }
    }




}