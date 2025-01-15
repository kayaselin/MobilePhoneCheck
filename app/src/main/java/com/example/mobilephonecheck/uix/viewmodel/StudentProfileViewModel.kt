package com.example.mobilephonecheck.uix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilephonecheck.data.entity.Student
import com.example.mobilephonecheck.data.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentProfileViewModel @Inject constructor(
    private val studentRepository: StudentRepository
) : ViewModel() {

    suspend fun loadStudentProfile(studentId: String): Student? {
        return studentRepository.getStudent(studentId)
    }

    fun saveStudentProfile(
        studentId: String,
        studentName: String,
        studentClass: String,
        studentMobile: String
    ) {
        val student = Student(
            student_id = studentId,
            student_name = studentName,
            student_class = studentClass,
            student_mobile = studentMobile
        )

        viewModelScope.launch {
            studentRepository.addStudent(student)
        }
    }

    fun updateStudentCode(studentId: String, code: String) {
        viewModelScope.launch {
            val isSuccess = studentRepository.updateStudentCode(studentId, code)
            if (!isSuccess) {
                // Hata durumunda log eklenebilir
            }
        }
    }
}
