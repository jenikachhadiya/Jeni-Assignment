package com.example.new_task.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.new_task.R
import com.example.new_task.adaptor.RecAdaptor
import com.example.new_task.api.Get.modal.getUserResponse
import com.example.new_task.api.post.`interface`.ApiClint
import com.example.new_task.api.post.modal.ApiUser
import com.example.new_task.dataBase.Userdatabase
import com.example.new_task.databinding.ActivityDataBinding
import com.example.new_task.entity.User
import com.example.new_task.preference.PrefClass
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataActivity : AppCompatActivity() {

    lateinit var binding: ActivityDataBinding

    private lateinit var db: Userdatabase
    private lateinit var userAdaptor: RecAdaptor
    private var userList = mutableListOf<User>()
    private var apiList = mutableListOf<ApiUser>()
    private lateinit var mAuth: FirebaseAuth
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        //database init
        db = Room.databaseBuilder(this, Userdatabase::class.java, getString(R.string.Db))
            .allowMainThreadQueries()
            .build()

        //getData
        ApiClint.init(this).getUserData().enqueue(object : Callback<getUserResponse> {
            override fun onResponse(
                call: Call<getUserResponse>,
                response: Response<getUserResponse>
            ) {
                val user = response.body()
//                val list = userAdaptor.setItem(user?.data as MutableList<ApiUser>)
              //  Log.e(TAG, "onResponse: $list")
                Log.e("GetResponse", "onResponse: ${response.body()}")
                Toast.makeText(applicationContext, response.message(), Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<getUserResponse>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(applicationContext, "failed", Toast.LENGTH_SHORT).show()
            }
        })
        userAdaptor = RecAdaptor(this@DataActivity, apiList)
        binding.RecView.adapter = userAdaptor
        binding.RecView.layoutManager = LinearLayoutManager(this)

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        binding.topAppBar.setOnMenuItemClickListener { menuItem ->

            when (menuItem.itemId) {
                R.id.logout -> {
                    signOut()
                    userAdaptor.clearData()
                    val manager = PrefClass(this)
                    if (manager.getUserData() == null) {
                        manager.setSplashStatus(false)
                        startActivity(Intent(applicationContext, SignInActivity::class.java))
                        finish()
                    }

                    false
                }
                R.id.permission -> {
                    startActivity(Intent(applicationContext, PermissionActivity::class.java))
                    finish()
                    true
                }
                else -> true
            }

        }

    }

    private fun signOut() {
        mAuth.signOut()
        mGoogleSignInClient?.signOut()?.addOnCompleteListener(
            this
        ) {
            Toast.makeText(applicationContext, "Sign-out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, SignInActivity::class.java))
        }

    }



}