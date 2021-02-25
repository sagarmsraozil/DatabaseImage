package com.sagarmishra.databaseclass

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.sagarmishra.databaseclass.api.ServiceBuilder
import com.sagarmishra.databaseclass.db.Database
//import com.sagarmishra.databaseclass.db.StudentDB
import com.sagarmishra.databaseclass.entity.AuthenticateData
import com.sagarmishra.databaseclass.model.AuthenticateModel
import com.sagarmishra.databaseclass.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var etUsername : TextInputEditText
    private lateinit var etPassword : TextInputEditText
    private lateinit var btnLogin : Button
    private lateinit var btnRegister : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        binding()
        val uobj = AuthenticateModel("softwarica","coventry")
        Database.loginValue.add(uobj)
        listener()
    }

    private fun binding()
    {
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
    }

    private fun listener()
    {
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    private fun loginProgress()
    {

        if(TextUtils.isEmpty(etUsername.text))
        {
            etUsername.error = "Insert Username"
            etUsername.requestFocus()

        }
        else if(TextUtils.isEmpty(etPassword.text))
        {
            etPassword.error = "Insert Password"
            etPassword.requestFocus()
        }
        else
        {
//           CoroutineScope(Dispatchers.IO).launch {
//               var user = StudentDB.getInstance(this@LoginActivity)!!.getUserDAO().authenticate(etUsername.text.toString(),etPassword.text.toString())
//               if(user != null)
//               {
//                   var auth:AuthenticateData? = AuthenticateData(etUsername.text.toString(),etPassword.text.toString())
//                   var dbInstance = StudentDB.getInstance(this@LoginActivity)
//                   dbInstance!!.getAuthenticateDAO().insertData(auth!!)
//                   val intent = Intent(this@LoginActivity,MainActivity::class.java)
//                   intent.putExtra("myUser",auth)
//                   startActivity(intent)
//                   finish()
//               }
//               else
//               {
//                   withContext(Main){
//                       Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
//                   }
//
//               }
//
//           }

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val userRepository = UserRepository()
                    var username = etUsername.text.toString().toLowerCase()
                    var password = etPassword.text.toString()
                    val response = userRepository.authenticateUser(username,password)

                    if(response.success == true)
                    {
                        addToPreferences()
                        ServiceBuilder.token = response.token
                        ServiceBuilder.offline = false
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        withContext(Main)
                        {
                            Snackbar.make(etUsername, "${response.message}", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
                catch (e:Exception)
                {
                    withContext(Main)
                    {
                        Toast.makeText(this@LoginActivity, "${e}", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }
    }

    private fun addToPreferences()
    {
        var getPref = getSharedPreferences("authorization",Context.MODE_PRIVATE)
        var editor = getPref.edit()
        editor.putString("username",etUsername.text.toString())
        editor.putString("password",etPassword.text.toString())
        editor.apply()
    }


    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.btnLogin ->{
                loginProgress()
            }

            R.id.btnRegister ->{
                var intent = Intent(this@LoginActivity,SignUpActivity::class.java)
                startActivity(intent)
            }
        }

    }
}