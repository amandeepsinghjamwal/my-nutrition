package com.example.mynutrition

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mynutrition.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val iconList = listOf(
            binding.ramen,
            binding.coffee,
            binding.croissont,
            binding.burgerSoda,
            binding.ramen,
            binding.coffee,
            binding.croissont,
            binding.burgerSoda
        )

        CoroutineScope(Dispatchers.Main).launch {
            iconList.forEach{
                it.visibility = View.VISIBLE
                delay(200)
                it.visibility=View.GONE
            }
            withContext(Dispatchers.Main){
                startActivity(intent)
            }
        }
    }
}