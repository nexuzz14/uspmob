package com.example.unstockphotograph

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unstockphotograph.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener{
            val registrasiIntent= Intent(this,registrasi::class.java)
            startActivity(registrasiIntent)
            Log.i("registerpage","button register now pressed")
        }

        binding.loginButton.setOnClickListener(){
            val email = binding.email.text.toString()
            val pass = binding.password.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()){
1
                firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this, BerandaActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this , "silahkan isi dengan benar!!" , Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this , "silahkan isi dengan benar!!" , Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}