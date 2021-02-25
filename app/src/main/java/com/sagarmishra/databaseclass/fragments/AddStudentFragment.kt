package com.sagarmishra.databaseclass.fragments


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.sagarmishra.databaseclass.R
import com.sagarmishra.databaseclass.`interface`.NotifyDatabaseUpdate
import com.sagarmishra.databaseclass.api.ServiceBuilder
//import com.sagarmishra.databaseclass.db.StudentDB
import com.sagarmishra.databaseclass.entity.Student
import com.sagarmishra.databaseclass.entity.User
import com.sagarmishra.databaseclass.repository.StudentRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class AddStudentFragment : Fragment(),View.OnClickListener,PopupMenu.OnMenuItemClickListener {

    private lateinit var etFn : EditText
    private lateinit var etAge : EditText
    private lateinit var etAddress : EditText
    private lateinit var ivImage : ImageView
    private lateinit var btnSave : Button
    private lateinit var rgGroup : RadioGroup
    var gender = "Male"
    private var gallery_code = 0
    private  var camera_code = 1
    private var image_url = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_student, container, false)

        binding(view)
        listner()
//        etFn.doOnTextChanged { text, start, before, count ->
//            if(etFn.text.toString().length > 0)
//            {
//                etAddress.requestFocus()
//            }
//        }
        return view
    }

    private fun binding(v:View?)
    {
        etFn = v!!.findViewById(R.id.etFn)
        etAge = v!!.findViewById(R.id.etAge)
        etAddress = v!!.findViewById(R.id.etAddress)
        ivImage = v!!.findViewById(R.id.ivImage)
        btnSave = v!!.findViewById(R.id.btnSave)
        rgGroup = v!!.findViewById(R.id.rgGroup)
    
    }

    private fun listner()
    {
        btnSave.setOnClickListener(this)
        rgGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId)
            {
                R.id.rbMale ->{
                    gender = "Male"
                }
                R.id.rbFemale ->{
                    gender = "Female"
                }
                R.id.rbOthers ->{
                    gender = "Others"
                }
            }
        }
        ivImage.setOnClickListener(this)
    }

    private fun validation():Boolean
    {
        if(TextUtils.isEmpty(etFn.text))
        {
            etFn.error = "Insert Firstname"
            etFn.requestFocus()
            return false
        }
        else if(TextUtils.isEmpty(etAge.text))
        {
            etAge.error = "Insert Age"
            etAge.requestFocus()
            return false
        }
        else if(TextUtils.isEmpty(etAddress.text))
        {
            etAddress.error = "Insert Address"
            etAddress.requestFocus()
            return false
        }

        else
        {
            return true
        }
    }

    private fun addStudent()
    {
        if(validation())
        {
            val fn = etFn.text.toString()
            val ad = etAddress.text.toString()

            val age =etAge.text.toString().toInt()
            val studentObj = Student(fullName=fn,address=ad,gender = gender,age=age)

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val stuRepo = StudentRepository()
                    val response = stuRepo.insertStudent(studentObj)
                    if(response.success == true)
                    {
                        if(image_url != null)
                        {
                            uploadImage(response.userId!!)
                        }
                        withContext(Main)
                        {
                            Snackbar.make(etFn,"${response.message}",Snackbar.LENGTH_LONG).show()
                            clear()

                        }
                    }
                    else
                    {
                        withContext(Main)
                        {
                            Snackbar.make(etFn,"${response.message}",Snackbar.LENGTH_LONG).show()

                        }
                    }
                }
                catch (ex:Exception)
                {
                    withContext(Main)
                    {
                        Snackbar.make(etFn,"${ex}",Snackbar.LENGTH_LONG).show()
                        println(ex.printStackTrace())
                    }
                }
            }

        }
    }

    private fun uploadImage(uid:String)
    {
        if(image_url !=null)
        {
            val file = File(image_url)

            val req_file = RequestBody.create(MediaType.parse("multipart/form-data"),file)

            val body = MultipartBody.Part.createFormData("file",file.name,req_file)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repo = StudentRepository()
                    val response = repo.uploadImage(uid,body)
                    if(response.success == true)
                    {
                        withContext(Main)
                        {
                            Snackbar.make(etAddress,"Uploaded",Snackbar.LENGTH_LONG).show()

                            println(response.message)
                        }
                    }
                    else
                    {
                        withContext(Main)
                        {
                            Snackbar.make(etAddress,"${response.message}",Snackbar.LENGTH_LONG).show()
                            println(response.message)
                        }
                    }
                }
               catch (ex:Exception)
               {
                   withContext(Main)
                   {
                       Toast.makeText(context, "${ex.toString()}", Toast.LENGTH_SHORT).show()
                       println(ex.printStackTrace())
                   }
               }

            }

        }
    }

    private fun clear()
    {
        etFn.text.clear()

        etAddress.text.clear()
        etAge.text.clear()
        gender="Male"
        rgGroup.check(R.id.rbMale)
        etFn.requestFocus()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK)
        {
            if (requestCode == gallery_code && data != null) {
                //overall location of selected image
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

                val contentResolver = requireActivity().contentResolver
                //locator and identifier
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()//moveTONext // movetolast

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])

                image_url = cursor.getString(columnIndex)

                //image preview
                ivImage.setImageBitmap(BitmapFactory.decodeFile(image_url))
                cursor.close()
            } else if (requestCode == camera_code && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                image_url = file!!.absolutePath
                ivImage.setImageBitmap(BitmapFactory.decodeFile(image_url))
            }
        }
    }

    override fun onClick(v: View?) {
       when(v!!.id)
       {
           R.id.btnSave ->{
               addStudent()
           }

           R.id.ivImage ->{
               popMenu()
           }
       }
    }

    private fun popMenu() {
        val popupMenu = PopupMenu(context,ivImage)
        popupMenu.menuInflater.inflate(R.menu.camera_gallery,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.show()
    }

    private fun open_camera()
    {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent,camera_code)
    }

    private fun open_gallery()
    {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,gallery_code)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when(item!!.itemId)
        {
            R.id.open_camera ->{
                open_camera()
            }
            R.id.open_gallery ->{
                open_gallery()
            }
        }
        return true
    }
    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

}