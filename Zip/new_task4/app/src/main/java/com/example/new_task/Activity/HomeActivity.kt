package com.example.new_task.Activity

import android.os.Bundle
import android.transition.Scene
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.new_task.Prefrencee.PrefClass
import com.example.new_task.R
import com.example.new_task.databinding.ActivityHomeBinding

import com.example.new_task.fragment.*


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addFragment(HomeFragment(), getString(R.string.homefregment))

        var manger = PrefClass(this).getBagStatus()

        if(manger){
            addFragment(BagFragment(),getString(R.string.bagfregment))
        }
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
                R.id.fav ->{
                    addFragment(FavoriteFragment(),getString(R.string.favfregment))
                    true
                }
                R.id.bag ->{
                    addFragment(BagFragment(),getString(R.string.bagfregment))
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
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        transaction.replace(R.id.fram_layout, fragment, tag)
        transaction.commit()

    }
}