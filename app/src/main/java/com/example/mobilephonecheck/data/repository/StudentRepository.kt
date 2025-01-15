package com.example.mobilephonecheck.data.repository

import com.example.mobilephonecheck.data.datasource.FirebaseDataSource
import com.example.mobilephonecheck.data.entity.Student
import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {

    // Öğrenci ekleme
    suspend fun addStudent(student: Student): Boolean {
        return firebaseDataSource.addStudent(student)
    }

    // Belirli bir öğrenci verisini alma
    suspend fun getStudent(studentId: String): Student? {
        return firebaseDataSource.getStudent(studentId)
    }

    // Tüm öğrencileri alma
    suspend fun getAllStudents(): List<Student> {
        return firebaseDataSource.getAllStudents()
    }

    // Öğrencinin telefonunun toplandığını işaretleme
    suspend fun markPhoneCollected(studentId: String): Boolean {
        return firebaseDataSource.markPhoneCollected(studentId)
    }

    suspend fun updateStudentCode(studentId: String, code: String): Boolean {
        return firebaseDataSource.updateStudentCode(studentId, code)
    }

    suspend fun getStudentsByClass(className: String): List<Student> {
        return firebaseDataSource.getStudentsByClass(className)
    }

}
