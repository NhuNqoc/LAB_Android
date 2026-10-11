package com.example.lab2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Mã màu xanh ngọc teal giống trong hình mẫu
val TealPrimary = Color(0xFF00897B)
val AvatarTeal = Color(0xFF26A69A)

@Composable
fun StudentCard(
    student: Student,
    onDetailClick: (Student) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circle Avatar
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(AvatarTeal, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = student.name.firstOrNull()?.toString() ?: "",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Tên và Ngành học
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F1F1F)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = student.major,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            // Nút "Xem chi tiết >" bo viền tròn
            Row(
                modifier = Modifier
                    .border(1.dp, TealPrimary, RoundedCornerShape(8.dp))
                    .clickable { onDetailClick(student) }
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Xem chi tiết",
                    color = TealPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = TealPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}