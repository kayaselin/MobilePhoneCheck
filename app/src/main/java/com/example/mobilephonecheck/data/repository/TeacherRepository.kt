package com.example.mobilephonecheck.data.repository

import com.example.mobilephonecheck.data.datasource.FirebaseDataSource
import com.example.mobilephonecheck.data.entity.Teacher
import javax.inject.Inject

class TeacherRepository @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource
) {

    suspend fun addTeacher(teacher: Teacher): Boolean {
        return firebaseDataSource.addTeacher(teacher)
    }

    suspend fun getTeacher(teacherId: String): Teacher? {
        return firebaseDataSource.getTeacher(teacherId)
    }

    // Ek olarak, öğretmenle ilgili daha fazla işlev de ekleyebilirsiniz
}
