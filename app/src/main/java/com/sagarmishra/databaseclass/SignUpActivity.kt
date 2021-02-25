package com.sagarmishra.databaseclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
//import com.sagarmishra.databaseclass.db.StudentDB
import com.sagarmishra.databaseclass.entity.User
import com.sagarmishra.databaseclass.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etFn: EditText
    private lateinit var etLn: EditText
    private lateinit var etUn: EditText
    private lateinit var etPw: EditText
    private lateinit var etEm: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        binding()
        listener()
    }

    private fun binding() {
        etFn = findViewById(R.id.etFn)
        etLn = findViewById(R.id.etLn)
        etUn = findViewById(R.id.etUn)
        etPw = findViewById(R.id.etPw)
        etEm = findViewById(R.id.etEm)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)
        linearLayout = findViewById(R.id.linearLayout)
    }

    private fun listener() {
        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
    }

    private fun validation(): Boolean {
        if (TextUtils.isEmpty(etFn.text)) {
            etFn.error = "Firstname required"
            etFn.requestFocus()
            return false
        } else if (TextUtils.isEmpty(etLn.text)) {
            etLn.error = "Lastname required"
            etLn.requestFocus()
            return false
        } else if (TextUtils.isEmpty(etUn.text)) {
            etUn.error = "Username required"
            etUn.requestFocus()
            return false
        } else if (TextUtils.isEmpty(etPw.text)) {
            etPw.error = "Password required"
            etPw.requestFocus()
            return false
        } else if (TextUtils.isEmpty(etEm.text)) {
            etEm.error = "Email required"
            etEm.requestFocus()
            return false
        } else {
            return true
        }
    }

    private fun registerUser() {
        if (validation()) {
            var first_name = etFn.text.toString()
            var last_name = etLn.text.toString()
            var username = etUn.text.toString()
            var email = etEm.text.toString()
            var password = etPw.text.toString()
            val uObj = User(
                first_name = first_name,
                last_name = last_name,
                username = username.toLowerCase(),
                email = email,
                password = password
            )
            CoroutineScope(Dispatchers.IO).launch {
                try {

                        val userRepository = UserRepository()
                        val response = userRepository.registerUser(uObj)
                        if (response.success == true) {
                            withContext(Main)
                            {
                                clear()

                                Snackbar.make(
                                    linearLayout,
                                    "${response.message}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                        else if(response.success == false)
                        {

                            withContext(Main)
                            {
                                if(response.message == "Username already exists!!")
                                {
                                    etUn.error = "${response.message}"
                                    etUn.requestFocus()

                                }
                                else if(response.message == "Email already exists!!")
                                {
                                    etEm.error = "${response.message}"
                                    etEm.requestFocus()
                                }
                                Snackbar.make(
                                    linearLayout,
                                    "${response.message}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }

                } catch (ex: Exception) {
                    println("The error is "+ex.toString())
                    withContext(Main)
                    {
                        Snackbar.make(linearLayout, ex.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                }

            }
//            CoroutineScope(Dispatchers.IO).launch {
//                StudentDB.getInstance(this@SignUpActivity)!!.getUserDAO().userInsert(uObj)
//                withContext(Main){
//                    etFn.text.clear()
//                    etLn.text.clear()
//                    etUn.text.clear()
//                    etPw.text.clear()
//                    etEm.text.clear()
//                    Snackbar.make(etFn,"Added",Snackbar.LENGTH_SHORT).show()
//                }
//            }
        }
    }

    private fun clear()
    {
        etEm.text.clear()
        etPw.text.clear()
        etUn.text.clear()
        etLn.text.clear()
        etFn.text.clear()
        etFn.requestFocus()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin -> {
                val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                startActivity(intent)
            }

            R.id.btnRegister -> {
                registerUser()
            }
        }
    }
}