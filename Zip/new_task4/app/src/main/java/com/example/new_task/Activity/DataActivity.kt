package com.example.new_task.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.new_task.Adaptor.RecAdaptor
import com.example.new_task.DataBase.Userdatabase
import com.example.new_task.Prefrencee.PrefClass
import com.example.new_task.R
import com.example.new_task.databinding.ActivityDataBinding
import com.example.new_task.entity.User

class DataActivity : AppCompatActivity() {

    lateinit var binding: ActivityDataBinding

     private lateinit var db: Userdatabase
    private lateinit var userAdaptor: RecAdaptor
    private var userList = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //database init
        db = Room.databaseBuilder(this, Userdatabase::class.java, getString(R.string.Db))
            .allowMainThreadQueries()
            .build()

        userAdaptor = RecAdaptor(this, userList)
        binding.RecView.adapter = userAdaptor
        binding.RecView.layoutManager = LinearLayoutManager(this)

        getData()

        binding.topAppBar.setOnMenuItemClickListener{ menuItem ->

            when(menuItem.itemId){
                R.id.logout ->
                {
                    userAdaptor.clearData()
                    var manager = PrefClass(this)
                    manager.setLoginStatus(false)
                    startActivity(Intent(applicationContext, SignInActivity::class.java))
                    finish()

                    false
                }
                else ->false
            }

        }




        /*binding.btnLogout.setOnClickListener {


        }*/
    }

    private fun getData() {
        val list = db.userdaos().getData() as MutableList<User>
        userAdaptor.setItem(list)

    }


}