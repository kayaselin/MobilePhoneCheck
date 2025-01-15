package com.example.mobilephonecheck.uix.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobilephonecheck.uix.viewmodel.AuthViewModel
import com.example.mobilephonecheck.uix.viewmodel.StudentProfileViewModel

@Composable
fun StudentProfileEditPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    studentProfileViewModel: StudentProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val studentId = authViewModel.getUserId()

    if (studentId == null) {
        LaunchedEffect(Unit) {
            navController.navigate("student_login") {
                popUpTo("student_home") { inclusive = true }
            }
        }
        return
    }

    var studentName by remember { mutableStateOf("") }
    var studentClass by remember { mutableStateOf("") }
    var studentMobile by remember { mutableStateOf("") }

    LaunchedEffect(studentId) {
        studentProfileViewModel.loadStudentProfile(studentId)?.let { student ->
            studentName = student.student_name ?: ""
            studentClass = student.student_class ?: ""
            studentMobile = student.student_mobile ?: ""
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Edit Profile", fontSize = 24.sp)

        TextField(
            value = studentName,
            onValueChange = { studentName = it },
            label = { Text("Name") }
        )

        TextField(
            value = studentClass,
            onValueChange = { studentClass = it },
            label = { Text("Class") }
        )

        TextField(
            value = studentMobile,
            onValueChange = { studentMobile = it },
            label = { Text("Mobile") }
        )

        Button(
            onClick = {
                studentProfileViewModel.saveStudentProfile(studentId, studentName, studentClass, studentMobile)
                navController.navigate("home")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save")
        }
    }
}
