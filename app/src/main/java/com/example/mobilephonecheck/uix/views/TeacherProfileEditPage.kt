package com.example.mobilephonecheck.uix.views

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobilephonecheck.uix.viewmodel.AuthViewModel
import com.example.mobilephonecheck.uix.viewmodel.TeacherProfileViewModel

@Composable
fun TeacherProfileEditPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    teacherProfileViewModel: TeacherProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val teacherId = authViewModel.getUserId()

    if (teacherId == null) {
        LaunchedEffect(Unit) {
            navController.navigate("teacher_login") {
                popUpTo("teacher_home") { inclusive = true }
            }
        }
        return
    }

    var teacherName by remember { mutableStateOf("") }
    var teacherClass by remember { mutableStateOf("") }

    // Load teacher's profile information
    LaunchedEffect(teacherId) {
        teacherProfileViewModel.loadTeacherProfile(teacherId)?.let { teacher ->
            teacherName = teacher.teacher_name ?: ""
            teacherClass = teacher.teacher_class ?: ""
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
            value = teacherName,
            onValueChange = { teacherName = it },
            label = { Text("Name") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        TextField(
            value = teacherClass,
            onValueChange = { teacherClass = it },
            label = { Text("Class") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                teacherProfileViewModel.saveTeacherProfile(
                    teacherId = teacherId,
                    teacherName = teacherName,
                    teacherClass = teacherClass
                )
                Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                navController.navigate("teacher_home")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save")
        }
    }
}
