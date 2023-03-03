package com.example.new_task.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.new_task.R
import com.example.new_task.databinding.ActivityHomeBinding
import com.example.new_task.fragment.HomeFragment
import com.example.new_task.fragment.ProfileFragment
import com.example.new_task.fragment.SettingFragments


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //  var manager = PrefClass(this)

        addFragment(HomeFragment(), getString(R.string.homefregment))

        binding.bottomNavigation.setOnItemSelectedListener {
            return@setOnItemSelectedListener when (it.itemId) {
                R.id.home -> {
                    addFragment(HomeFragment(), getString(R.string.homefregment))
                    true
                }
                R.id.profile -> {
                    addFragment(ProfileFragment(), getString(R.string.profilefregment))
                    true
                }
                R.id.setting -> {
                    addFragment(SettingFragments(), getString(R.string.settingfregment))
                    true
                }
                else -> {
                    false
                }
            }
        }


    }

    private fun addFragment(fragment: Fragment, tag: String) {
        val manger = supportFragmentManager
        val transaction = manger.beginTransaction()
        transaction.replace(R.id.fram_layout, fragment, tag)
        transaction.commit()

    }
}