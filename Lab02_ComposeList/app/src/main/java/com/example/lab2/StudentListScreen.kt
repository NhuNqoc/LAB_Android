package com.example.lab2

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen() {
    val context = LocalContext.current

    // Danh sách sinh viên
    val studentList = remember { mutableStateListOf(*sampleStudents.toTypedArray()) }

    // State cho ô Tìm kiếm
    var isSearching by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    // State quản lý Dialog Thêm sinh viên
    var showAddDialog by remember { mutableStateOf(false) }

    // State quản lý Dialog Xem chi tiết sinh viên (null nghĩa là đang đóng)
    var selectedStudent by remember { mutableStateOf<Student?>(null) }

    // Lọc danh sách sinh viên theo từ khóa
    val filteredStudents = studentList.filter {
        it.name.contains(query, ignoreCase = true) || it.major.contains(query, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        TextField(
                            value = query,
                            onValueChange = { query = it },
                            placeholder = { Text("Tìm kiếm...", color = Color.White.copy(alpha = 0.7f)) },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                cursorColor = Color.White
                            )
                        )
                    } else {
                        Text(
                            text = "Danh sách sinh viên",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isSearching = !isSearching }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TealPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = TealPrimary,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Student",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(filteredStudents, key = { it.id }) { student ->
                    StudentCard(student = student) { s ->
                        // Lưu sinh viên được chọn để mở Dialog chi tiết
                        selectedStudent = s
                    }
                }
            }
        }
    }

    // --- BẢNG (DIALOG) XEM CHI TIẾT SINH VIÊN ---
    selectedStudent?.let { student ->
        AlertDialog(
            onDismissRequest = { selectedStudent = null },
            title = {
                Text(
                    text = "Thông tin chi tiết",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TealPrimary
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Row {
                        Text(
                            text = "Họ và tên: ",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(text = student.name, color = Color.DarkGray)
                    }
                    Row {
                        Text(
                            text = "Ngành học: ",
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(text = student.major, color = Color.DarkGray)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { selectedStudent = null },
                    colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                ) {
                    Text("Đóng", color = Color.White)
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White
        )
    }

    // --- BẢNG (DIALOG) NHẬP THÔNG TIN SINH VIÊN MỚI ---
    if (showAddDialog) {
        var nameInput by remember { mutableStateOf("") }
        var majorInput by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = {
                Text(
                    text = "Thêm sinh viên mới",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TealPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Họ và tên") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = majorInput,
                        onValueChange = { majorInput = it },
                        label = { Text("Ngành học") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nameInput.trim().isEmpty() || majorInput.trim().isEmpty()) {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                        } else {
                            val newId = (studentList.maxOfOrNull { it.id } ?: 0) + 1
                            val newStudent = Student(
                                id = newId,
                                name = nameInput.trim(),
                                major = majorInput.trim()
                            )
                            studentList.add(newStudent)
                            showAddDialog = false
                            Toast.makeText(context, "Đã thêm: ${newStudent.name}", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TealPrimary)
                ) {
                    Text("Thêm", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Hủy", color = Color.Gray)
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White
        )
    }
}