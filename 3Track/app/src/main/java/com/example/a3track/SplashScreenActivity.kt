package com.example.a3track

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private val TAG = "MyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Log.i(TAG, "onCreateSplash() called.")

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val backgroundImage: ImageView = findViewById(R.id.logo)
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_slide)
        backgroundImage.startAnimation(slideAnimation)


        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }




}