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
import androidx.drawerlayout.widget.DrawerLayout
import com.shakir.spliff.R
import com.shakir.spliff.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout : DrawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        invalidateOptionsMenu()

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
                R.id.login -> login()
            }

            when(it.itemId){
                R.id.user ->{
                    val intent = Intent(this, UserActivity::class.java)
                    startActivity(intent)
                }
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

    private fun logout() {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") { _, _ ->
            hideLogOutItem()
            visibleLogIn()

            val sharedPreferences = applicationContext.getSharedPreferences("my_sharedPreference",0)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
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
        val menuPic = menu.findItem(R.id.menu_pic)
        menuPic.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        when(item.itemId){
            R.id.menu_add -> openCart()
        }

        when(item.itemId) {
            R.id.menu_pic -> openUser()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun openUser() {
        val intent = Intent(this,UserActivity::class.java)
        startActivity(intent)
    }

    private fun login() {
        Toast.makeText(this,"login need",Toast.LENGTH_SHORT).show()
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

    private fun visibleLogIn(){
        val menuView = binding.navigationView.menu
        menuView.findItem(R.id.login).isVisible = true
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Close?")
        builder.setMessage("Do you want to close the app?")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") { _, _ ->
            finish()
            Toast.makeText(this,"successfully close the app",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ ->
            Toast.makeText(this,"Not Closed the app",Toast.LENGTH_SHORT).show()
        }

        val alertDialog : AlertDialog = builder.create()
        alertDialog.show()
        //super.onBackPressed()
    }
}