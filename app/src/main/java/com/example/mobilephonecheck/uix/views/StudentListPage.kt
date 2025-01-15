package com.example.mobilephonecheck.uix.views

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobilephonecheck.uix.viewmodel.TeacherViewModel



@Composable
fun StudentListPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    teacherViewModel: TeacherViewModel = hiltViewModel(),
    className: String
) {
    val studentList = teacherViewModel.studentList.collectAsState(emptyList()).value

    // Öğrencileri yükleme
    LaunchedEffect(className) {
        teacherViewModel.loadClassStudents(className)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Class: $className", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        if (studentList.isEmpty()) {
            Text(text = "No students found in this class.", fontSize = 16.sp)
        } else {
            studentList.forEach { student ->
                Text(
                    text = "${student.student_name} (${student.student_mobile})",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}
