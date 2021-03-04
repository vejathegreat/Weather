package com.velaphi.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.velaphi.weather.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
    }

    override fun onResume() {

        object : CountDownTimer(DURATION, INTERVAL) {

            override fun onFinish() {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {

            }
        }.start()
        super.onResume()
    }
    companion object{
        const val DURATION = 3000L
        const val INTERVAL = 2000L
    }
}
