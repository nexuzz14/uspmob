package com.example.unstockphotograph

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.unstockphotograph.databinding.ActivityLoginBinding
import com.example.unstockphotograph.databinding.ActivityRegistrasiBinding
import com.google.firebase.auth.FirebaseAuth

class registrasi : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrasiBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityRegistrasiBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.RegisButton.setOnClickListener(){
            val email = binding.email.text.toString()
            val usrnm = binding.username.text.toString()
            val pass = binding.password.text.toString()

            if (email.isNotEmpty() && usrnm.isNotEmpty() && pass.isNotEmpty()){

                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this, login::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this , it.exception.toString() ,Toast.LENGTH_SHORT).show()
                    }
                }
            }else{

                Toast.makeText(this , "silahkan isi dengan benar!!" ,Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}