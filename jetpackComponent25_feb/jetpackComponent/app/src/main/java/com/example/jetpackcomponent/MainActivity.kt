package com.example.jetpackcomponent


import android.app.Activity
import android.graphics.drawable.DrawableWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import com.example.jetpackcomponent.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // val navHostFragment = this.findViewById<View>(R.id.nav_host_fragment)


        try {


            navController = findNavController(R.id.nav_host_fragment)
            binding.naviView.setupWithNavController(navController)
            appBarConfiguration = AppBarConfiguration(navController.graph,binding.drawerLayout)
            setupActionBarWithNavController(navController,appBarConfiguration)




        }catch (e:Exception){
            e.printStackTrace()
        }










    }
   /*   private val  navController: NavController by lazy {
       Navigation.findNavController(this,R.id.nav_host_fragment)

   }
      binding.bottomNavigation.setOnItemSelectedListener {
           return@setOnItemSelectedListener when (it.itemId) {
           R.id.item1 ->
           {
               Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
               true
           }
           R.id.item2 ->{
               Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
               true
           }
           R.id.item3 ->{
               Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
               true
           }
           R.id.item4 ->{
               Navigation.findNavController(binding.root).navigate(R.id.homeFragment)
               true
           }
           else -> true
        }
       }
    // binding.bottomNavigation.setupWithNavController(navController)
     navController.addOnDestinationChangedListener { _, destination, _ ->
         if (destination.id == R.id.defaltFragment) {
             binding.bottomNavigation.visibility = View.VISIBLE
         } else {
             binding.bottomNavigation.visibility = View.GONE
         }
     }
    //navigation Drawer
    //val appBarConfiguration = AppBarConfiguration(navController.graph ,binding.drawerLayout)
      val actionBarDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,R.string.open,R.string.close)
      binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
      actionBarDrawerToggle.syncState()
      supportActionBar?.setDisplayHomeAsUpEnabled(true)

      supportActionBar?.setDisplayHomeAsUpEnabled(true)

      binding.toolbar.setOnClickListener {
          binding.naviView.visibility
          binding.drawerLayout.isDrawerOpen(GravityCompat.START)
          setUpDrawerLayout()
      }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        }else super.onOptionsItemSelected(item)

    }
      //var navController = this.findNavController(R.id.nav_host_fragment)
      // val navController = Navigation.findNavController(binding.root)
      //  var navigration = navController.navigate(R.id.nav_host_fragment)
       // binding.bottomNavigation.setupWithNavController(navController)

      override fun onSupportNavigateUp(): Boolean {
          return NavigationUI.navigateUp(navController,binding.drawerLayout)
      }

     private fun setUpDrawerLayout(){
        binding.naviView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this,navController,binding.drawerLayout)

    }
     override fun onBackPressed() {
         if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
           binding.drawerLayout.isDrawerOpen(GravityCompat.START)
         }else{
             super.onBackPressed()
         }
     }*/
}