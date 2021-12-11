package com.shakir.spliff.ui.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import com.shakir.spliff.databinding.ActivityHomeBinding
import com.shakir.spliff.R
import android.view.View
import com.google.android.material.navigation.NavigationView




class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var flag = 0
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        invalidateOptionsMenu()

        val drawerLayout : DrawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open,
            R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.explore -> Toast.makeText(applicationContext,"this is Explore",Toast.LENGTH_SHORT).show()
            }
            when(it.itemId){
                R.id.logout -> {
                    logout()
                }
            }
            when(it.itemId){
                R.id.profile ->
                    openUser()
            }

            when(it.itemId){
                R.id.login -> login()
            }

            true
        }

        binding.card1.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        binding.card2.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        binding.card3.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
            val menuPic = menu?.findItem(R.id.menu_pic)
            menuPic?.isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    private fun logout() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") { _, _ ->
            hideLogOutItem()
            visibleLogIn()
             flag++
            Toast.makeText(this,"Logged out successfully",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ ->
            Toast.makeText(this,"Logged out not successful",Toast.LENGTH_SHORT).show()
        }
        builder.setTitle("Logout?")
        builder.setMessage("Are you sure you want to logout?")
        val alertDialog : AlertDialog = builder.create()
        alertDialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        when(item.itemId){
            R.id.menu_add -> openCart()
        }

        return super.onOptionsItemSelected(item)
    }


    private fun openUser() {
        val intent = Intent(this,UserActivity::class.java)
        startActivity(intent)
    }

    private fun login() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    private fun openCart() {
        val intent = Intent(this,CartActivity::class.java)
        startActivity(intent)
    }

    private fun hideLogOutItem(){
        val menuView = binding.navigationView.menu
        menuView.findItem(R.id.logout).isVisible = false

    }

    private fun hideLogInItem(){
        val menuView = binding.navigationView.menu
        menuView.findItem(R.id.login).isVisible = false

    }

    private fun visibleLogIn(){
        val menuView = binding.navigationView.menu
        menuView.findItem(R.id.login).isVisible = true
        menuView.findItem(R.id.profile).isVisible = false
       // val navMenu: Menu = binding.navigationView.getMenu()
       // navMenu.findItem(R.id.menu_pic).isVisible = false
    }
}