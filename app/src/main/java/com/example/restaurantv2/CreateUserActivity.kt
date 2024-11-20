package com.example.restaurantv2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.restaurantv2.databinding.ActivityCreateUserBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CreateUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCreateUserBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.createButton.setOnClickListener {
            val username = binding.etCreateUser.text.toString()
            val password = binding.etCreatePassword.text.toString()

            if (username.isNotBlank() && password.isNotBlank()) {

                val sharedPrefs = getSharedPreferences("LoginData", Context.MODE_PRIVATE)
                val gson = Gson()

                val json = sharedPrefs.getString("USERS","[]")
                val type = object : TypeToken<MutableList<User>>() {}.type
                val users: MutableList<User> = gson.fromJson(json,type)

                users.add(User(username,password))

                val editor = sharedPrefs.edit()
                val updatedJson = gson.toJson(users)
                editor.putString("USERS", updatedJson)
                editor.apply()

                Toast.makeText(this,"User created Successfully!",Toast.LENGTH_SHORT).show()
                finish()
            }else {
                Toast.makeText(this,"Fill in all fields",Toast.LENGTH_SHORT).show()
            }
        }
    }

}