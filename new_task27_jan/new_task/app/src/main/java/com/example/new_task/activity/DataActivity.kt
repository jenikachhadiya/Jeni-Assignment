package com.example.new_task.activity

import android.content.Intent
import android.media.MediaPlayer.OnCompletionListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.new_task.adaptor.RecAdaptor
import com.example.new_task.dataBase.Userdatabase
import com.example.new_task.preference.PrefClass
import com.example.new_task.R
import com.example.new_task.databinding.ActivityDataBinding
import com.example.new_task.entity.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class DataActivity : AppCompatActivity() {

    lateinit var binding: ActivityDataBinding

     private lateinit var db: Userdatabase
    private lateinit var userAdaptor: RecAdaptor
    private var userList = mutableListOf<User>()
    private lateinit var mAuth: FirebaseAuth
    private  var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        //database init
        db = Room.databaseBuilder(this, Userdatabase::class.java, getString(R.string.Db))
            .allowMainThreadQueries()
            .build()

        userAdaptor = RecAdaptor(this, userList)
        binding.RecView.adapter = userAdaptor
        binding.RecView.layoutManager = LinearLayoutManager(this)

        getData()
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        binding.topAppBar.setOnMenuItemClickListener{ menuItem ->

            when(menuItem.itemId){
                R.id.logout ->
                {
                    signOut()
                    userAdaptor.clearData()
                    val manager = PrefClass(this)
                       // manager.setLoginStatus(false)
                        if (manager.getUserData()==null) {
                            manager.setSplashStatus(false)
                            startActivity(Intent(applicationContext, SignInActivity::class.java))
                            finish()
                        }

                    false
                }
                R.id.permission ->{
                    startActivity(Intent(applicationContext,PermissionActivity::class.java))
                    finish()
                    true
                }
                else ->true
            }

        }

    }

    private fun signOut() {
        mAuth.signOut()
        mGoogleSignInClient?.signOut()?.addOnCompleteListener (this
        ){
            Toast.makeText(applicationContext, "Sign-out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext,SignInActivity::class.java))
        }

    }

    private fun getData() {
        val list = db.userdaos().getData() as MutableList<User>
        userAdaptor.setItem(list)

    }


}