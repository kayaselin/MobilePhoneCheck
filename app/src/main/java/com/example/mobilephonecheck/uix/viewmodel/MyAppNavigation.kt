package com.example.mobilephonecheck.uix.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobilephonecheck.uix.views.*

@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
    teacherViewModel: TeacherViewModel = hiltViewModel()

) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.observeAsState(AuthState.Loading)

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate("student_home") {
                    popUpTo("student_login") { inclusive = true }
                    launchSingleTop = true
                }
            }
            is AuthState.TeacherAuthenticated -> {
                navController.navigate("teacher_home") {
                    popUpTo("teacher_login") { inclusive = true }
                    launchSingleTop = true
                }
            }
            is AuthState.Unauthenticated -> {
                navController.navigate("student_login") {
                    popUpTo("student_home") { inclusive = true }
                    launchSingleTop = true
                }
            }
            else -> Unit
        }
    }

    NavHost(navController = navController, startDestination = "student_login") {
        // Öğrenci giriş sayfası
        composable("student_login") {
            StudentLoginPage(modifier, navController, authViewModel)
        }
        // Öğretmen giriş sayfası
        composable("teacher_login") {
            TeacherLoginPage(modifier, navController, authViewModel)
        }
        // Öğrenci kayıt sayfası
        composable("student_signup") {
            StudentSignUpPage(modifier, navController, authViewModel)
        }
        // Öğretmen kayıt sayfası
        composable("teacher_signup") {
            TeacherSignUpPage(modifier, navController, authViewModel)
        }
        // Öğrenci anasayfa
        composable("student_home") {
            StudentHomePage(modifier, navController, authViewModel)
        }
        // Öğretmen anasayfa
        composable("teacher_home") {
            TeacherHomePage(modifier, navController, authViewModel)
        }
        // Öğrenci profil düzenleme sayfası
        composable("edit_profile") {
            StudentProfileEditPage(modifier, navController)
        }
        // Öğretmen profil düzenleme sayfası
        composable("teacher_edit_profile") {
            TeacherProfileEditPage(modifier, navController, authViewModel)
        }

        composable("student_list_page/{className}") { backStackEntry ->
            val className = backStackEntry.arguments?.getString("className") ?: ""
            StudentListPage(modifier, navController, teacherViewModel, className)
        }

    }
}