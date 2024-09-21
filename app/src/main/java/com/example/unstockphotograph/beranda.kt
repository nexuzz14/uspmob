package com.example.unstockphotograph

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unstockphotograph.databinding.ActivityBerandaBinding
import com.example.unstockphotograph.databinding.ActivityLoginBinding
import com.example.unstockphotograph.databinding.ActivityMainBinding
import com.example.unstockphotograph.databinding.FotoBinding

class beranda : AppCompatActivity() {

    private lateinit var binding: ActivityBerandaBinding
    private lateinit var bindingfoto: FotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding= ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadActivity.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }

        binding.profilActivity.setOnClickListener {
            val intent = Intent(this, profil::class.java)
            startActivity(intent)
        }

    }

    fun showimage(){
        bindingfoto=FotoBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(bindingfoto.root)
            .create()
        dialog.show()
    }
}