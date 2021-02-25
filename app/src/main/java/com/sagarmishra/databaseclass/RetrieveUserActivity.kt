package com.sagarmishra.databaseclass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.snackbar.Snackbar
import com.sagarmishra.databaseclass.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class RetrieveUserActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var btnRetrieve : Button
    private lateinit var lstView : ListView
    private lateinit var consLayout:ConstraintLayout
    var selected = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieve_user)
        supportActionBar?.hide()
        binding()
        listener()
    }

    private fun binding()
    {
        btnRetrieve = findViewById(R.id.btnRetrieve)
        lstView = findViewById(R.id.lstView)
        consLayout= findViewById(R.id.consLayout)
    }

    private fun listener()
    {
        btnRetrieve.setOnClickListener(this)
        lstView.setOnItemClickListener { parent, view, position, id ->
            selected = parent?.getItemAtPosition(position).toString()
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnRetrieve ->{
                CoroutineScope(Dispatchers.IO).launch {
                    try{
                        val userRepo = UserRepository()
                        val response = userRepo.retrieveUser()
                        if(response.success == true)
                        {
                            println(response)
                        }
                        else
                        {
                            println(response)
                        }
                    }
                    catch (e:Exception)
                    {
                        withContext(Main)
                        {
                            println(e)
                            Snackbar.make(consLayout,"${e.printStackTrace()}",Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

}