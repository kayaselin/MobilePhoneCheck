package com.example.mobilephonecheck.uix.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mobilephonecheck.uix.viewmodel.AuthState
import com.example.mobilephonecheck.uix.viewmodel.AuthViewModel
import com.example.mobilephonecheck.uix.viewmodel.StudentProfileViewModel
import java.util.UUID

@Composable
fun StudentHomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    studentViewModel: StudentProfileViewModel = hiltViewModel()
) {
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
    var generatedCode by remember { mutableStateOf("") } // Oluşturulan kodu tutmak için

    // Kullanıcı oturumu kontrolü
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("student_login")
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Student Home Page", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            navController.navigate("edit_profile") // Profil düzenleme ekranına yönlendirme
        }) {
            Text(text = "Edit Profile")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kod oluşturma butonu
        Button(onClick = {
            val studentId = authViewModel.getUserId()
            if (studentId != null) {
                val code = UUID.randomUUID().toString().take(6) // Rastgele 6 karakterli kod
                studentViewModel.updateStudentCode(studentId, code)
                generatedCode = code
                Toast.makeText(context, "Code Generated: $code", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error: User ID not found", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Generate Code")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (generatedCode.isNotEmpty()) {
            Text(text = "Your Code: $generatedCode", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Oturumu kapatma butonu
        Button(
            onClick = {
                authViewModel.signout()
                Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT).show()
                navController.navigate("student_login") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ) {
            Text("Sign Out")
        }
    }
}
