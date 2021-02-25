package com.sagarmishra.databaseclass.adapter

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sagarmishra.databaseclass.R
import com.sagarmishra.databaseclass.`interface`.NotifyDatabaseUpdate
//import com.sagarmishra.databaseclass.db.StudentDB
import com.sagarmishra.databaseclass.entity.Student
import com.sagarmishra.databaseclass.model.StudentModel
import com.sagarmishra.databaseclass.repository.StudentRepository
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class StudentAdapter(val context: Context,val lstStudent:MutableList<Student>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    class StudentViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var cvImage: CircleImageView
        var tvRname: TextView
        var tvRage: TextView
        var tvRaddress: TextView
        var btnEdit: ImageView
        var btnDelete: ImageView

        init {
            cvImage = v.findViewById(R.id.cvImage)
            tvRname = v.findViewById(R.id.tvRname)
            tvRage = v.findViewById(R.id.tvRage)
            tvRaddress = v.findViewById(R.id.tvRaddress)
            btnEdit = v.findViewById(R.id.btnEdit)
            btnDelete = v.findViewById(R.id.btnDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.student_apdater_layout,parent,false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lstStudent.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val studentData = lstStudent[position]

        holder.tvRname.text = studentData.fullName
        holder.tvRaddress.text = studentData.address
        holder.tvRage.text = studentData.age.toString()

        Glide.with(context).load(studentData.displayPicture).into(holder.cvImage)

        var dialog = Dialog(context)
        dialog.setContentView(R.layout.delete_layout)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(false)

        var dialog2 = Dialog(context)
        dialog2.setContentView(R.layout.alert_update)
        dialog2.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog2.setCancelable(false)

        var btnYes : Button = dialog.findViewById(R.id.btnYes)
        var btnNo : Button = dialog.findViewById(R.id.btnNo)

        var etFn : EditText = dialog2.findViewById(R.id.etFn)
        var etAge : EditText = dialog2.findViewById(R.id.etAge)
        var etAddress : EditText = dialog2.findViewById(R.id.etAddress)
        var etProfile : EditText = dialog2.findViewById(R.id.etProfile)
        var rgGroup : RadioGroup = dialog2.findViewById(R.id.rgGroup)
        var gender = "Male"
        var btnUpdate : Button = dialog2.findViewById(R.id.btnUpdate)
        var btnCancel : Button = dialog2.findViewById(R.id.btnCancel)

        btnYes.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repo = StudentRepository()
                    val response = repo.deleteStudent(studentData._id!!)
                    if(response.success == true)
                    {
                        withContext(Dispatchers.Main)
                        {
                            lstStudent.removeAt(position)
                            notifyDataSetChanged()
                            Snackbar.make(holder.cvImage,"${response.message}",Snackbar.LENGTH_LONG).show()
                        }
                    }
                    else
                    {
                        withContext(Dispatchers.Main)
                        {
                            Snackbar.make(holder.cvImage,"${response.message}",Snackbar.LENGTH_LONG).show()

                        }
                    }
                }
                catch (ex:Exception)
                {
                    withContext(Dispatchers.Main)
                    {
                        Snackbar.make(holder.cvImage,"${ex.toString()}",Snackbar.LENGTH_LONG).show()
                        println(ex.printStackTrace())
                    }
                }
            }
            dialog.cancel()
        }

        btnNo.setOnClickListener {
            dialog.cancel()
        }

        holder.btnDelete.setOnClickListener{
            dialog.show()
        }

        rgGroup.setOnCheckedChangeListener { group, checkedId ->

            when(checkedId)
            {
                R.id.rbMale->{
                    gender ="Male"
                }
                R.id.rbFemale->{
                    gender ="Female"
                }
                R.id.rbOthers ->{
                    gender= "Others"
                }

            }
        }

        holder.btnEdit.setOnClickListener {
            etFn.setText(studentData.fullName)
            etAddress.setText(studentData.address)
            etAge.setText(studentData.age.toString())
            etProfile.setText(studentData.displayPicture)
            when(studentData.gender)
            {
                "Male"->{
                    rgGroup.check(R.id.rbMale)
                }
                "Female" ->{
                    rgGroup.check(R.id.rbFemale)
                }
                "Others"->{
                    rgGroup.check(R.id.rbOthers)
                }
            }
            dialog2.show()

        }

        btnCancel.setOnClickListener {
            dialog2.cancel()
        }

        btnUpdate.setOnClickListener {
            if(TextUtils.isEmpty(etFn.text))
            {
                etFn.error = "Enter Firstname"
                etFn.requestFocus()

            }
            else if(TextUtils.isEmpty(etAge.text))
            {
                etAge.error = "Enter Age"
                etAge.requestFocus()

            }
            else if(TextUtils.isEmpty(etAddress.text))
            {
                etAddress.error = "Enter Address"
                etAddress.requestFocus()

            }
            else if(TextUtils.isEmpty(etProfile.text))
            {
                etProfile.error = "Enter Address"
                etProfile.requestFocus()

            }
            else if(!Regex("""[a-z][a-z]+""").matches(etFn.text.toString().toLowerCase().replace(" ","")))
            {
                etAddress.error = "Full name should not contain any numbers"
                etAddress.requestFocus()

            }
            else if(etAge.text.toString().toInt() > 40 || etAge.text.toString().toInt() < 18)
            {
                etAge.error = "Age should be in between 18-40"
                etAge.requestFocus()

            }
            else
            {
                studentData.fullName = etFn.text.toString()
                studentData.address = etAddress.text.toString()
                studentData.age = etAge.text.toString().toInt()
                studentData.displayPicture = etProfile.text.toString()
                studentData.gender = gender
//                CoroutineScope(Dispatchers.IO).launch {
//                    var instance = StudentDB.getInstance(context)
//                    instance!!.getStudentDAO().updateStudent(studentData)
//
//                    withContext(Main){
//                        notifyDataSetChanged()
//                    }
//                }
                dialog2.cancel()
            }
        }

    }




}