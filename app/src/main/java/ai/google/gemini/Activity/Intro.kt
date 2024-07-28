package ai.google.gemini.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ai.google.gemini.R
import ai.google.gemini.databinding.ActivityIntroBinding
import android.content.Intent
import androidx.databinding.DataBindingUtil

class Intro : AppCompatActivity() {
    lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)
        binding.mbChat.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}