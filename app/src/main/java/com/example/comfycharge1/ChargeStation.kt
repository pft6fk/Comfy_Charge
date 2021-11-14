package com.example.comfycharge1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.comfycharge1.databinding.ActivityChargeStationBinding
import com.example.comfycharge1.databinding.ActivityLoginBinding

class ChargeStation : AppCompatActivity() {

    private lateinit var binding: ActivityChargeStationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChargeStationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btCreateRoute.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
            finish()
        }
    }
}