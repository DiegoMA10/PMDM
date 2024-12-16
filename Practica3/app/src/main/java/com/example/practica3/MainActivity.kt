package com.example.practica3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getPreferences(Context.MODE_PRIVATE)
        val isFirstTime = preferences.getBoolean(getString(R.string.sp_firt_time), true)

        val usernameEditText = binding.etUsername
        val passwordEditText = binding.etPassword
        val registerButton = binding.registerButton

        if (!isFirstTime){
            val username = preferences.getString(getString(R.string.username),"")
            val password = preferences.getString(getString(R.string.password),"")
            usernameEditText.setText(username)
            passwordEditText.setText(password)
        }

        registerButton.setOnClickListener {
            val username = usernameEditText.text
            val password = passwordEditText.text
            val intent = Intent(this, NoticiasActivity::class.java)
            with(preferences.edit()) {
                putBoolean(getString(R.string.sp_firt_time), false)
                putString(getString(R.string.username), username.toString())
                putString(getString(R.string.password), password.toString())
                apply()
                startActivity(intent)
            }

        }
    }
}