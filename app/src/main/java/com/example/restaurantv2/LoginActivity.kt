package com.example.restaurantv2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restaurantv2.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.loginButton.setOnClickListener {
            val userName = binding.etUsername.text.toString()
            val passWord = binding.etPassword.text.toString()
            if (validateUser(userName,passWord)) {
                Toast.makeText(this,"Login Successful!",Toast.LENGTH_SHORT).show()

                val homeIntent = Intent(this,HomeActivity::class.java)
                startActivity(homeIntent)

            }else {
                Toast.makeText(this,"Login Failed!",Toast.LENGTH_SHORT).show()
            }

        }

        binding.createUserButton.setOnClickListener {
            val newIntent = Intent(this,CreateUserActivity::class.java)
            startActivity(newIntent)
        }
    }
    private fun validateUser(inputUsername: String, inputPassword: String): Boolean {
        val sharedPrefs = getSharedPreferences("LoginData",Context.MODE_PRIVATE)
        val gson = Gson()

        val json = sharedPrefs.getString("USERS","[]")
        val type = object: TypeToken<MutableList<User>>() {}.type
        val users: List<User> = gson.fromJson(json,type)

        return users.any{it.username == inputUsername && it.password == inputPassword}
    }
}