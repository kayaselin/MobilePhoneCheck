package com.example.mobilephonecheck.uix.views

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobilephonecheck.uix.viewmodel.AuthViewModel
import com.example.mobilephonecheck.uix.viewmodel.TeacherViewModel

@Composable
fun TeacherHomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    teacherViewModel: TeacherViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var enteredCode by remember { mutableStateOf("") }
    val teacherClassName by teacherViewModel.teacherClassName.collectAsState(initial = "")

    // Öğretmenin sınıf bilgilerini yükleme
    LaunchedEffect(Unit) {
        val teacherId = authViewModel.getUserId()
        if (teacherId != null) {
            teacherViewModel.loadTeacherClassName(teacherId)
        } else {
            Toast.makeText(context, "Error: Teacher ID not found", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Teacher Home Page", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Edit Profile Button
        Button(
            onClick = { navController.navigate("teacher_edit_profile") },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Edit Profile")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Show Class Button
        if (teacherClassName.isNotEmpty()) {
            Button(
                onClick = { navController.navigate("student_list_page/${teacherClassName}") },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("View Class: $teacherClassName")
            }
        } else {
            Text("Class information not available", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Verify Student Code Section
        OutlinedTextField(
            value = enteredCode,
            onValueChange = { enteredCode = it },
            label = { Text("Enter Student Code") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val isVerified = teacherViewModel.verifyStudentCode(enteredCode)
                if (isVerified) {
                    Toast.makeText(context, "Code verified successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Invalid code or student not found.", Toast.LENGTH_SHORT).show()
                }
                enteredCode = ""
            }
        ) {
            Text("Verify Code")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign Out Button
        Button(
            onClick = {
                authViewModel.signout()
                Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
                navController.navigate("teacher_login") {
                    popUpTo("teacher_home") { inclusive = true }
                }
            }
        ) {
            Text("Sign Out")
        }
    }
}
