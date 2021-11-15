package com.example.comfycharge1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.comfycharge1.databinding.ActivityChargeStationBinding

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
//        val b = this.intent.extras
//        val array = b!!.getStringArray("key")
//        var a = array?.get(0)
//        var c = array?.get(1)
//
//        val giveBack = arrayOf(a,c)
//        Log.d("LOG123", "latitude: " + array!![0] + "longitude: " + array[1])
//            //The key argument here must match that used in the other activity
//
//
//        binding.btCreateRoute.setOnClickListener {
//            val b = Bundle()
//            b.putStringArray("key", arrayOf(a, c))
//            val i = Intent(this, MapsActivity::class.java)
//            i.putExtras(b)
//            startActivity(i)
//            finish()
//        }

    }



}