package com.example.comfycharge1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.comfycharge1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, MapsActivity::class.java)

        binding.btSignIn.setOnClickListener {
            startActivity(intent)
            finish()
        }

        binding.btCancel.setOnClickListener {
            startActivity(intent)
            finish()
        }

        binding.txtForgotEmail.setOnClickListener {
            Toast.makeText(this, "Open new dialog", Toast.LENGTH_SHORT).show()
        }

        binding.txtForgotPassword.setOnClickListener {
            Toast.makeText(this, "Open new dialog", Toast.LENGTH_SHORT).show()
        }

        binding.btCreateAccount.setOnClickListener{
            Toast.makeText(this, "Open Sign Up fragment", Toast.LENGTH_SHORT).show()
        }


    }
}