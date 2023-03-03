package com.example.new_task.activity

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper.create
import com.example.new_task.R
import com.example.new_task.api.post.`interface`.ApiClint
import com.example.new_task.api.post.modal.ResponseData
import com.example.new_task.dataBase.Userdatabase
import com.example.new_task.databinding.ActivitySignupBinding
import com.example.new_task.entity.Chat
import com.example.new_task.entity.User
import com.example.new_task.preference.PrefClass
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileDescriptor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    private lateinit var db: Userdatabase
    private var imageUri: Uri? = null
    private var uri: Uri? = null
    private var base64: String? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdb: DatabaseReference


    companion object {
        private const val CAMERA_PERMISSION_CODE = 100
        private const val READ_EXTERNAL_STORAGE = 103
        private const val WRITE_EXTERNAL_STORAGE = 104
    }


    private fun uriToBitmap(uri: Uri): Bitmap? {
        try {
            val parcelFileDescriptor =
                contentResolver.openFileDescriptor(uri, getString(R.string.R))
            val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor.close()
            return image
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()


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


        //date picker
        binding.etDob.setOnClickListener {
            val showCalender =
                CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())

            MaterialDatePicker.Builder.datePicker().setTitleText("Date Of Birth")
            val datePickerDialog =
                MaterialDatePicker.Builder.datePicker().setCalendarConstraints(showCalender.build())
                    .build()
            datePickerDialog.show(supportFragmentManager, "MATERIAL_DATE_PICKER")

            datePickerDialog.addOnPositiveButtonClickListener {
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = format.format(it)
                binding.etDob.setText(date)
            }

        }
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
            } else if (binding.etMobileNo.text?.length != 10) {
                binding.tvMobile.isErrorEnabled = false
                binding.tvMobile.error = "Enter Valid Mobile Number"
                binding.etCpass.requestFocus()
            } else if (null == base64) {
                Toast.makeText(
                    applicationContext, getString(R.string.please_uplode_image), Toast.LENGTH_SHORT
                ).show()
            } else {
                val name = binding.etName.text.toString().trim()
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPass.text.toString().trim()
                val image = base64.toString().trim()
                val phoneNumber = binding.etMobileNo.text.toString().trim()
                val dob = binding.etDob.text.toString().trim()
                val user = db.userDuos().findByData(email)

                if (user != null) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.user_email_Already_register),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    insertData(name, email, password, image, phoneNumber, dob)
                    signupAuth(name, email, password)

                }

            }

        }
        binding.btnSignin.setOnClickListener {
            startActivity(Intent(applicationContext, SignInActivity::class.java))
        }
        binding.ivImage.setOnClickListener {
            //Option  Dialog
            showOptionDialog()
            //checkReadPermission()
        }
    }

    private fun signupAuth(name: String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                addDataToDatabase(name, email, mAuth.uid!!)
                Toast.makeText(applicationContext, "Auth in Firebase", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addDataToDatabase(name: String, email: String, uid: String) {
        mdb = FirebaseDatabase.getInstance().reference
        mdb.child("user").child(uid).setValue(Chat(uid, name, email))

    }


    //image pic  option Dialog
    private fun showOptionDialog() {
        val optionD = arrayOf(getString(R.string.gallary), getString(R.string.camara))
        AlertDialog.Builder(this).setTitle(getString(R.string.pick_image))
            .setItems(optionD) { _, i ->
                if (i == 0) {
                    //showPermission()
                    // checkReadWritePermission()
                    val gallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, 100)
                    Toast.makeText(this, getString(R.string.gallary), Toast.LENGTH_SHORT).show()
                } else if (i == 1) {
                    createImageUri()
                    Toast.makeText(this, getString(R.string.camara), Toast.LENGTH_SHORT).show()
                }
            }.show()

    }

    //uploadFile
    private fun uploadFile(uri: Uri?): String? {
       // imageUri = uri
        val cursor: Cursor? = uri?.let { contentResolver.query(it, null, null, null, null) }
        cursor!!.moveToFirst()
        val columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        Log.e(TAG, "uploadFile: $columnIndex")
        return cursor.getString(columnIndex)
    }
    /* private fun checkReadWritePermission() {
         if (ActivityCompat.checkSelfPermission(
                 this,
                 Manifest.permission.READ_EXTERNAL_STORAGE
             ) != PackageManager.PERMISSION_GRANTED
             ||
             ActivityCompat.checkSelfPermission(
                 this,
                 Manifest.permission.WRITE_EXTERNAL_STORAGE
             ) != PackageManager.PERMISSION_GRANTED
         ) {
             if (ActivityCompat.shouldShowRequestPermissionRationale(
                     this,
                     Manifest.permission.READ_EXTERNAL_STORAGE
                 ) || ActivityCompat.shouldShowRequestPermissionRationale(
                     this,
                     Manifest.permission.WRITE_EXTERNAL_STORAGE
                 )
             ) {
                 AlertDialog.Builder(this)
                     .setTitle("Read and Write Permission Needed")
                     .setMessage("This add Needs the Read and Write Permission for Use your External Data Put and Get")
                     .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                         ActivityCompat.requestPermissions(
                             this,
                             arrayOf(
                                 Manifest.permission.READ_EXTERNAL_STORAGE,
                                 Manifest.permission.WRITE_EXTERNAL_STORAGE
                             ),
                             READ_EXTERNAL_STORAGE
                         )
                     })
                     .create()
                     .show()
             } else {
                 checkReadPermission()
             }

         }


     }*/

    /*private fun checkReadPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            showDialog()
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Needed")
            .setMessage("Please Allow Read and Write Permission,Permission Need For Pick Image in External Storage")
            .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                checkReadPermission()
            })
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 100) {

            imageUri = data?.data
            uploadFile(imageUri)

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

            if (bitmap != null) {
                val uri = getImageUriFromBitmap(bitmap)
                uploadFile(uri)
            }
        } else if (resultCode == RESULT_OK && requestCode == 0 && data != null) {
            //file pick
            try {
                data.data.let {
                    //uploadFile(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }

    private fun insertData(
        Name: String, Email: String, Password: String, Img: String, phoneNo: String, dob: String
    ) {
        //Room and SherPref
        val user =
            User(name = Name, email = Email, pass = Password, img = Img, phoneNumber = phoneNo)
        val userDaos = db.userDuos()
        userDaos.insertData(user)
        val imgB = base64ToBitmap(Img)
        val ig = getImageUriFromBitmap(imgB)

        val file = File(imageUri!!.path!!)

        val fbody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        //val file = uploadFile(ig)?.let { File(it) }
       // val img:RequestBody = RequestBody.create(("".toMediaType()),file!!)
       // Log.e(TAG, "insertData: $img")

        val nameR: RequestBody = RequestBody.create("text/plain".toMediaType(), Name)
        Log.e(TAG, "insertDataName: " + Gson().toJson(nameR))
        val emailR: RequestBody = RequestBody.create("text/plain".toMediaType(), Email)
        val passR: RequestBody = RequestBody.create("text/plain".toMediaType(), Password)
        val phoneR: RequestBody = RequestBody.create("text/plain".toMediaType(), phoneNo)
        val dobR: RequestBody = RequestBody.create("text/plain".toMediaType(), dob)

        //Post Api Calling
        ApiClint.init(this).fromField(
            name = nameR,
            email = emailR,
            password = passR,
            mobile_no = phoneR,
            upload = fbody,
            dob = dobR

        ).enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>, response: retrofit2.Response<ResponseData>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        Log.e("Post", "onResponse: ${response.isSuccessful}")
                        Log.e("Post", "onResponse: ${response.message()}")
                        Log.e("Post", "onResponse: ${response.body()}")
                        Toast.makeText(applicationContext, "Successfully", Toast.LENGTH_SHORT).show()
                        val uData = response.body()?.data
                        if (uData != null){
                            PrefClass(this@SignupActivity).saveUserDataApi(uData)
                        }

                        Log.e("onCreateView", "onResponse: " + Gson().toJson(uData))
                        Log.e("onCreateView", "onResponse: " + Gson().toJson(response.body()!!.data.userProfilePic))
                      //  Log.v("DEBUG","onResponse"+ Gson().toJson(response.body()) )
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                        finish()
                    }
                } else {
                    //error  response
                    try {
                        val gson = Gson()
                        val type = object : TypeToken<ResponseData>(){}.type
                        val responseData: ResponseData? = gson.fromJson(response.errorBody()!!.charStream(), type)
                        if (responseData != null) {
                            Toast.makeText(applicationContext, responseData.msg, Toast.LENGTH_SHORT).show()
                        }

                    }catch (e:IOException){
                        e.printStackTrace()
                    }

                }
            }
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
                Log.e(TAG, "onFailure: $t")
                Toast.makeText(applicationContext, "failAPi", Toast.LENGTH_SHORT).show()
            }

        })
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

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(this.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

    private fun refouces() {
        binding.tvName.isErrorEnabled = false
        binding.tvEmail.isErrorEnabled = false
        binding.tvPass.isErrorEnabled = false
        binding.tvCpass.isErrorEnabled = false
        binding.dropdownMenu.isErrorEnabled = false

    }

    /* private fun checkPermission(permission: String, requestCode: Int) {
         if (ContextCompat.checkSelfPermission(
                 this@SignupActivity,
                 permission
             ) != PackageManager.PERMISSION_GRANTED
         ) {
             //Requesting permission
             showOptionDialog()
             ActivityCompat.requestPermissions(this@SignupActivity, arrayOf(permission), requestCode)
         } else {

             showOptionDialog()
             Toast.makeText(this@SignupActivity, "Permission already granted", Toast.LENGTH_SHORT)
                 .show()
         }
     }*/

    private fun checkReadPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_MEDIA_IMAGES
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_MEDIA_IMAGES
                )
            ) {
                AlertDialog.Builder(this).setTitle("READ_EXTERNAL_STORAGE Permission Needed")
                    .setMessage("This add Needs the Location Permission ,Please access to use Location functionality")
                    .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                        requestReadPermission()
                    }).create().show()
            } else {
                if (ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_MEDIA_IMAGES
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    showOptionDialog()
                }
                requestReadPermission()
            }
        } else {
            showOptionDialog()
            checkWritePermission()
        }
    }

    private fun checkWritePermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE
        )
        //requestReadAndWritePermission()
    }

    private fun requestReadAndWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), READ_EXTERNAL_STORAGE
            )
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_EXTERNAL_STORAGE
            )
        }

    }

    private fun requestReadPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
            ), READ_EXTERNAL_STORAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            /*CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showOptionDialog()
                    Toast.makeText(
                        this@SignupActivity,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        this@SignupActivity,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }*/

            READ_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        showOptionDialog()
                        //checkWritePermission()
                        // Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            applicationContext, "Permission Denied1", Toast.LENGTH_SHORT
                        ).show()
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                this, Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        ) {
                            startActivity(
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", this.packageName, null)
                                )
                            )
                        }
                    }
                    Toast.makeText(
                        this@SignupActivity, "Write Permission Denied2", Toast.LENGTH_SHORT
                    ).show()
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.READ_MEDIA_IMAGES
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        checkWritePermission()
                        // Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this, Manifest.permission.READ_MEDIA_IMAGES
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null)
                            )
                        )
                    }
                    Toast.makeText(applicationContext, "Permission Denied RPS", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            WRITE_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        Toast.makeText(applicationContext, "Permission granted", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {

                    Toast.makeText(applicationContext, "Permission Denied3", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }

}

/*private fun requestCameraPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        when {
            ContextCompat.checkSelfPermission(
                this@SignupActivity,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                showOptionDialog()

                Toast.makeText(applicationContext, "Granted", Toast.LENGTH_SHORT).show()
            }shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)->{

            }
            else ->{
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts(
                    "package",
                    BuildConfig.APPLICATION_ID,null
                )
                intent.data = uri
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

    }
}*/






