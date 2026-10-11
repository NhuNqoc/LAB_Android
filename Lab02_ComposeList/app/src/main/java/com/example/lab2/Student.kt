package com.example.lab2

data class Student(
    val id: Int,
    val name: String,
    val major: String
)

val sampleStudents = listOf(
    Student(1, "Nguyễn Văn An", "Công nghệ thông tin"),
    Student(2, "Trần Thị Bích", "Công nghệ thông tin"),
    Student(3, "Lê Hoàng Minh", "Công nghệ thông tin")
)