package com.sagarmishra.databaseclass

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.sagarmishra.databaseclass.api.ServiceBuilder
//import com.sagarmishra.databaseclass.db.StudentDB
import com.sagarmishra.databaseclass.entity.AuthenticateData
import com.sagarmishra.databaseclass.entity.User
import com.sagarmishra.databaseclass.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception


@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var ivLogo:ImageView
    private lateinit var top_anim : Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        binding()
        initialize()


    }

    private fun binding()
    {
        ivLogo = findViewById(R.id.ivLogo)
        top_anim = AnimationUtils.loadAnimation(this,R.anim.anim_top)

    }

    private fun initialize()
    {
        ivLogo.animation = top_anim
        val pref = getSharedPreferences("authorization",Context.MODE_PRIVATE)
        val username = pref.getString("username","")
        val password = pref.getString("password","")
        if(username != "" && password != "") {
            CoroutineScope(Dispatchers.IO).launch {
                delay(3000)
                try {


                    val userRepo = UserRepository()
                    val response = userRepo.authenticateUser(username!!, password!!)
                    if (response.success == true) {
                        ServiceBuilder.token = response.token

                        val intent =
                            Intent(this@SplashScreenActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                }



                catch(e:Exception)
                {
                    withContext(Main)
                    {

                        Snackbar.make(ivLogo, e.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                    val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        else
        {
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)
        }



//        CoroutineScope(Dispatchers.IO).launch {
//            var user: Boolean = false
//            var authenticatedData : List<AuthenticateData> = StudentDB.getInstance(this@SplashScreenActivity)!!.getAuthenticateDAO().retrieveData()
//            if(authenticatedData.size > 0)
//            {
//                user = true
//            }
//            delay(3000)
//            if(user)
//            {
//                val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
//                intent.putExtra("myUser",authenticatedData[0])
//                startActivity(intent)
//                finish()
//            }
//            else
//            {
//                val intent = Intent(this@SplashScreenActivity,LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//
//
//        }

    }


}