package com.sagarmishra.databaseclass

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sagarmishra.databaseclass.adapter.StudentAdapter
import com.sagarmishra.databaseclass.adapter.ViewPagerAdapter
//import com.sagarmishra.databaseclass.db.StudentDB
import com.sagarmishra.databaseclass.entity.AuthenticateData
import com.sagarmishra.databaseclass.entity.User
import com.sagarmishra.databaseclass.fragments.AddStudentFragment
import com.sagarmishra.databaseclass.fragments.StudentFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    private lateinit var navView : BottomNavigationView
    private lateinit var btnLogout : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding()
        initialize()
        listener()
    }

    private fun binding()
    {
       navView = findViewById(R.id.navBar)
       btnLogout = findViewById(R.id.btnLogout)
    }

    private fun initialize()
    {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.loadData, StudentFragment())
            addToBackStack(null)
            commit()
        }
    }

    private fun listener()
    {
        navView.setOnNavigationItemSelectedListener(this)
        btnLogout.setOnClickListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.nav_student ->{
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.loadData,StudentFragment())
                    addToBackStack(null)
                    commit()
                }
            }

            R.id.nav_addstudents ->{
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.loadData,AddStudentFragment())
                    addToBackStack(null)
                    commit()
                }
            }
        }

        return true
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnLogout ->{
               // var authenticatedUser : AuthenticateData? = intent.getParcelableExtra("myUser")
                CoroutineScope(Dispatchers.IO).launch {
                    val pref = getSharedPreferences("authorization",Context.MODE_PRIVATE)
                    var editor = pref.edit()
                    editor.putString("username","")
                    editor.putString("password","")
                    editor.apply()
                    val intent = Intent(this@MainActivity,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

//                CoroutineScope(Dispatchers.IO).launch {
//                    delay(1000)
//                    var instance = StudentDB.getInstance(this@MainActivity)
//                    instance!!.getAuthenticateDAO().deleteData(authenticatedUser!!)
//                    val intent = Intent(this@MainActivity,LoginActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
            }
        }
    }
}