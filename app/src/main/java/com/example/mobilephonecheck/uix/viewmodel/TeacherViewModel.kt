package com.example.mobilephonecheck.uix.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilephonecheck.data.entity.Student
import com.example.mobilephonecheck.data.repository.StudentRepository
import com.example.mobilephonecheck.data.repository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    private val _studentList = MutableStateFlow<List<Student>>(emptyList())
    val studentList: StateFlow<List<Student>> get() = _studentList
    private val _teacherClassName = MutableStateFlow("")
    val teacherClassName: StateFlow<String> get() = _teacherClassName


    fun loadClassStudents(teacherId: String) {
        viewModelScope.launch {
            val teacher = teacherRepository.getTeacher(teacherId)
            if (teacher != null) {
                val teacherClass = teacher.teacher_class
                if (!teacherClass.isNullOrEmpty()) {
                    val students = studentRepository.getStudentsByClass(teacherClass)
                    _studentList.value = students
                } else {
                    _studentList.value = emptyList()
                    // Hata mesajı veya durum belirtmek için loglama ekleyebilirsiniz.
                }
            } else {
                _studentList.value = emptyList()
            }
        }
    }



    fun verifyStudentCode(code: String): Boolean {
        val students = _studentList.value.toMutableList()
        val student = students.find { it.student_code == code }
        return if (student != null) {
            student.isVerified = !student.isVerified // Tersine çevir
            _studentList.value = students // Listeyi güncelle
            true
        } else {
            false
        }
    }

    fun loadTeacherClassName(teacherId: String) {
        viewModelScope.launch {
            val teacher = teacherRepository.getTeacher(teacherId)
            _teacherClassName.value = teacher?.teacher_class ?: ""
        }
    }


}
