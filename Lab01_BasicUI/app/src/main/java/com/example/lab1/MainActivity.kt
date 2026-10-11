package com.example.lab1

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab1.databinding.Lab1Binding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: Lab1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo ViewBinding
        binding = Lab1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bắt sự kiện khi bấm nút Đăng ký
        binding.btnRegister.setOnClickListener {
            val fullName = binding.etFullName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            // Kiểm tra thông tin nhập vào
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            } else {
                // Hiển thị thông báo đăng ký thành công
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_LONG).show()
            }
        }
    }
}