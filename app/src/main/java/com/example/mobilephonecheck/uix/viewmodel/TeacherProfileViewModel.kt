package com.example.mobilephonecheck.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilephonecheck.data.entity.Teacher
import com.example.mobilephonecheck.data.repository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherProfileViewModel @Inject constructor(
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    suspend fun loadTeacherProfile(teacherId: String): Teacher? {
        return teacherRepository.getTeacher(teacherId)
    }

    fun saveTeacherProfile(teacherId: String, teacherName: String, teacherClass: String) {
        val teacher = Teacher(
            teacher_id = teacherId,
            teacher_name = teacherName,
            teacher_class = teacherClass
        )
        viewModelScope.launch {
            teacherRepository.addTeacher(teacher)
        }
    }
}
