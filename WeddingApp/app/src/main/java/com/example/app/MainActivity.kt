package com.example.app


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app.fragments.*
import com.example.weddingapp.R
import com.example.weddingapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        binding.tvHome.text = "Home"

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, TaskFragment())
            addToBackStack(null)
            commit()

            binding.bottomNavigation.itemIconTintList = null

            binding.bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.action_task -> {
                        addFragment( TaskFragment())
                        true
                    }
                    R.id.action_services -> {
                        addFragment( ServicesFragment())
                        true
                    }
                    R.id.action_budget -> {
                        addFragment(BudgetFragment())
                        true
                    }
                    R.id.action_chat -> {
                        addFragment( ChatFragment())
                        true
                    }
                    R.id.action_profile -> {
                        addFragment(ProfileFragment())
                        true
                    }
//                    R.id.action_settings -> {
//                        addFragment(SettingsFragment())
//                        true
//                    }
                    else -> false
                }
            }
        }

    }

    private fun addFragment( fragment: Fragment) {

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }

//        binding.tvHome.text = title
    }


//    override fun onBackPressed() {
//
//        val seletedItemId = binding.bottomNavigation.selectedItemId
//        if (R.id.action_task != seletedItemId) {
//            setHomeItem(this@MainActivity)
//        } else {
//            super.onBackPressed()
//        }
//    }
//
//    private fun setHomeItem(mainActivity: MainActivity) {
//
//
//    }

//    override fun onBackPressed() {
//        if (!isTopFragmentConsumedBackPress()) {
//            super.onBackPressed()
//        }
//    }
//
//    private fun isTopFragmentConsumedBackPress() = getTopFragment<BackPressHandler>()?.onBackPressed() == true
}