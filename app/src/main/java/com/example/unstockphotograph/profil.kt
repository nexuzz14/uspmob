package com.example.unstockphotograph

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unstockphotograph.databinding.ActivityBerandaBinding
import com.example.unstockphotograph.databinding.ActivityLoginBinding
import com.example.unstockphotograph.databinding.ActivityMainBinding
import com.example.unstockphotograph.databinding.ActivityProfilBinding

class profil : AppCompatActivity() {

    private lateinit var binding: ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding= ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.berandaActivity.setOnClickListener {
            val intent = Intent(this, beranda::class.java)
            startActivity(intent)
        }

        binding.uploadActivity.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }

    }
}