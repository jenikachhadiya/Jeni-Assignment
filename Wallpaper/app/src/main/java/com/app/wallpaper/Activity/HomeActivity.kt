package com.app.wallpaper.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavGraph
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager.widget.ViewPager
import com.app.wallpaper.Adaptor.viewpager_adaptor
import com.app.wallpaper.Fragment.categories
import com.app.wallpaper.R
import com.app.wallpaper.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout

class HomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    lateinit var toogle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settablayout()
        setviewpager()


        // side swipe close this code
        var db = binding.drawerlayout
        db.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // toolbar clicke then open drwer
        binding.toolbar.setNavigationOnClickListener {
            binding.drawerlayout.open()
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            menuItem.isChecked = true
            binding.drawerlayout.close()
            true
        }
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.like -> {

                    addFregment(categories())
                    menuItem.isChecked = false
                    Toast.makeText(applicationContext, "like", Toast.LENGTH_SHORT).show()

                }
                R.id.auto -> {

                    addFregment(categories())
                    menuItem.isChecked = false
                    Toast.makeText(applicationContext, "auto wallpaper", Toast.LENGTH_SHORT).show()

                }
                R.id.removeads -> {

                    addFregment(categories())
                    menuItem.isChecked = false
                    Toast.makeText(applicationContext, "remove Ads", Toast.LENGTH_SHORT).show()

                }
                R.id.nofi -> {

                    addFregment(categories())
                    menuItem.isChecked = false
                    Toast.makeText(applicationContext, "notification", Toast.LENGTH_SHORT).show()

                }
                R.id.pp -> {

                    addFregment(categories())
                    menuItem.isChecked = false
                    Toast.makeText(applicationContext, "privacy policy", Toast.LENGTH_SHORT).show()

                }
                R.id.rate -> {

                    addFregment(categories())
                    menuItem.isChecked = false
                    Toast.makeText(applicationContext, "rate us", Toast.LENGTH_SHORT).show()

                }
                R.id.share -> {

                    addFregment(categories())
                    menuItem.isChecked = false
                    Toast.makeText(applicationContext, "share", Toast.LENGTH_SHORT).show()

                }

            }
            db.close()
            true

        }


    }
//    override fun onOptionsItemSelected(item: MenuItem):Boolean {
//        if(toogle.onOptionsItemSelected(item)){
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun setviewpager(){
        binding.pager.apply {
            adapter = viewpager_adaptor(supportFragmentManager, binding.tabLayout.tabCount)
            binding.pager.setCurrentItem(1)
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        }

    }

    private fun addFregment(fragment: Fragment) {
        var manager = supportFragmentManager
        var transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()

        //toolbar ma set
        //binding.toolBar.title = tag

    }

    private fun settablayout() {

        binding.tabLayout.apply {
            addTab(this.newTab().setText("CATEGORIES"))
            addTab(this.newTab().setText("RECENT"))
            addTab(this.newTab().setText("RENDOM"))
            addTab(this.newTab().setText("WEEKLYPOPULAR"))
            addTab(this.newTab().setText("MONTHLYPOPULAR"))
            addTab(this.newTab().setText("MOSTPOPULAR"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let {

                        binding.pager.currentItem = it
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })


        }


    }
}