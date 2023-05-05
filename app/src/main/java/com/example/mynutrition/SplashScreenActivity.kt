package com.example.mynutrition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mynutrition.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent= Intent(this,MainActivity::class.java).apply {
            flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        CoroutineScope(Dispatchers.Main).launch {
            delay(250)
            binding.ramen.visibility=View.GONE
            binding.coffee.visibility=View.VISIBLE
            delay(250)
            binding.coffee.visibility=View.GONE
            binding.croissont.visibility=View.VISIBLE
            delay(250)
            binding.croissont.visibility=View.GONE
            binding.burgerSoda.visibility=View.VISIBLE
            delay(250)
            binding.burgerSoda.visibility=View.GONE
            binding.ramen.visibility=View.VISIBLE
            delay(250)
            binding.ramen.visibility=View.GONE
            binding.coffee.visibility=View.VISIBLE
            delay(250)
            binding.coffee.visibility=View.GONE
            binding.croissont.visibility=View.VISIBLE
            delay(250)
            binding.croissont.visibility=View.GONE
            binding.burgerSoda.visibility=View.VISIBLE
            delay(250)
            startActivity(intent)
        }
    }
}