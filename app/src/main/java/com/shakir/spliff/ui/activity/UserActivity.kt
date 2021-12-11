package com.shakir.spliff.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shakir.spliff.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(binding.root)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}