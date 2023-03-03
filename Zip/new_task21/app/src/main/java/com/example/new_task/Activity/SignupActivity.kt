
package com.example.new_task.Activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.new_task.DataBase.Userdatabase
import com.example.new_task.Prefrencee.PrefClass
import com.example.new_task.R
import com.example.new_task.databinding.ActivitySignupBinding
import com.example.new_task.entity.User
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.IOException
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    private lateinit var db: Userdatabase
     var imageUri: Uri? = null
     var base64: String? = null

    private fun uriToBitmap(uri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor =contentResolver.openFileDescriptor(uri, getString(R.string.R))
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)




        //database init
        db = Room.databaseBuilder(this, Userdatabase::class.java, "db").allowMainThreadQueries()
            .build()


        //password formula
        val pattern: Pattern = Pattern.compile(
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        )
        //DropDown Menu
        val gender = resources.getStringArray(R.array.Gender)
        val arrayAdapter = ArrayAdapter(this, R.layout.drop_down_layout, gender)
        binding.autoComplate.setAdapter(arrayAdapter)

        // validation
        binding.btnSignup.setOnClickListener {

            refouces()
            if (binding.etName.text.toString().isEmpty()) {
                binding.tvName.isErrorEnabled = false
                binding.tvName.error = getString(R.string.enter_name)
                binding.etName.requestFocus()
                Toast.makeText(applicationContext, getString(R.string.name), Toast.LENGTH_SHORT)
                    .show()
            } else if (binding.etEmail.text.toString().isEmpty()) {
                binding.tvEmail.isErrorEnabled = false
                binding.tvEmail.error = getString(R.string.enter_valid_email)
                binding.etEmail.requestFocus()
                Toast.makeText(applicationContext, getString(R.string.email), Toast.LENGTH_SHORT)
                    .show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
                binding.tvEmail.isErrorEnabled = false
                binding.tvEmail.error = getString(R.string.email_not_valid)
                binding.etEmail.requestFocus()
            } else if (binding.etPass.text.toString().isEmpty()) {
                binding.tvPass.isErrorEnabled = false
                binding.tvPass.error = getString(R.string.enter_password)
                binding.etPass.requestFocus()
                Toast.makeText(applicationContext, getString(R.string.password), Toast.LENGTH_SHORT)
                    .show()
            } else if (!pattern.matcher(binding.etPass.text.toString()).matches()) {
                binding.tvPass.isErrorEnabled = false
                binding.tvPass.error = getString(R.string.enter_valid_password)
                binding.etEmail.requestFocus()
            } else if (binding.etCpass.text.toString().isEmpty()) {
                binding.tvCpass.isErrorEnabled = false
                binding.tvCpass.error = getString(R.string.enter_comfirm_password)
                binding.etCpass.requestFocus()
                Toast.makeText(
                    applicationContext, getString(R.string.comfirm_password), Toast.LENGTH_SHORT
                ).show()
            } else if (binding.etCpass.text.toString() != binding.etPass.text.toString()) {
                binding.tvCpass.isErrorEnabled = false
                binding.tvCpass.error = getString(R.string.enter_valid_password)
                binding.etCpass.setText("")
                binding.etCpass.requestFocus()
            }else if (null == base64) {
                Toast.makeText(
                    applicationContext, getString(R.string.please_uplode_image), Toast.LENGTH_SHORT
                ).show()
            } else {
                val name = binding.etName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPass.text.toString().trim()
                val image = base64.toString().trim()


                val user = db.userdaos().findByData(email)

                if (user != null) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.user_email_Already_register),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val manger = PrefClass(this)
                    manger.setLoginStatus(true)
                    insertData(name, email, password, image)
                    startActivity(Intent(applicationContext, DataActivity::class.java))
                    finish()
                }

            }


        }

        binding.btnSignin.setOnClickListener {
            startActivity(Intent(applicationContext, SignInActivity::class.java))
            finish()
        }
        binding.ivImage.setOnClickListener {
            //Option  Dialog
            showOptionDialog()

        }
    }

    private fun insertData(Name: String, Email: String, Password: String, Img: String) {
        val user = User(name = Name, email = Email, pass = Password, img = Img)
        val userDaos = db.userdaos()
        userDaos.insertData(user)
        Toast.makeText(
            applicationContext, getString(R.string.account_create_succefully), Toast.LENGTH_SHORT
        ).show()

    }

    //image pic  option Dialog
    private fun showOptionDialog() {
        val optionD = arrayOf(getString(R.string.gallary), getString(R.string.camara))
        AlertDialog.Builder(this).setTitle(getString(R.string.pick_image))
            .setItems(optionD) {_, i ->
                if (i == 0) {
                    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, 100)
                    Toast.makeText(this, getString(R.string.gallary), Toast.LENGTH_SHORT).show()

                } else if (i == 1) {
                    createImageUri()
                    Toast.makeText(this, getString(R.string.camara), Toast.LENGTH_SHORT).show()
                }
            }.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {

            imageUri = data?.data

            val bitmap = imageUri?.let {
                uriToBitmap(it)
            }
            base64 = bitmap?.let {
                bitmapToBase64(it)
            }
            val baseToBitmap = base64?.let {
                base64ToBitmap(base64)
            }
            binding.ivImage.setImageBitmap(baseToBitmap)

        } else if (resultCode == RESULT_OK && requestCode == 1) {
            val bitmapImage = data?.extras?.get(getString(R.string.Data)) as Bitmap

             base64 = bitmapToBase64(bitmapImage)

            val bitmap = base64?.let {
                base64ToBitmap(it)
            }
            binding.ivImage.setImageBitmap(bitmap)
        }
    }

    private fun createImageUri() {

        val takePicIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePicIntent, 1)

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(applicationContext, getString(R.string.error), Toast.LENGTH_SHORT).show()
        }

    }

    private fun bitmapToBase64(bitmap: Bitmap): String? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)

    }

    private fun base64ToBitmap(base64: String?): Bitmap {
        val imageAsBytes: ByteArray = Base64.decode(base64?.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)

    }


    private fun refouces() {
        binding.tvName.isErrorEnabled = false
        binding.tvEmail.isErrorEnabled = false
        binding.tvPass.isErrorEnabled = false
        binding.tvCpass.isErrorEnabled = false
        binding.dropdownMenu.isErrorEnabled = false

    }


}



