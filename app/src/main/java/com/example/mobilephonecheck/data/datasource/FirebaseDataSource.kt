package com.example.mobilephonecheck.data.datasource

import com.example.mobilephonecheck.data.entity.Student
import com.example.mobilephonecheck.data.entity.Teacher
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDataSource @Inject constructor() {

    private val firestore: FirebaseFirestore = Firebase.firestore
    private val studentsCollection = firestore.collection("students")
    private val teachersCollection = firestore.collection("teachers")


    // Öğrenci ekleme işlevi
    suspend fun addStudent(student: Student): Boolean {
        return try {
            student.student_id?.let {
                studentsCollection.document(it)
                    .set(student)
                    .await()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Öğrenciyi alma işlevi
    suspend fun getStudent(studentId: String): Student? {
        return try {
            val documentSnapshot = studentsCollection.document(studentId)
                .get()
                .await()
            documentSnapshot.toObject(Student::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Tüm öğrencileri listeleme işlevi
    suspend fun getAllStudents(): List<Student> {
        return try {
            val querySnapshot = studentsCollection.get().await()
            querySnapshot.toObjects(Student::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }



    // Öğrencinin telefonunun alındığını işaretlemek için güncelleme işlevi
    suspend fun markPhoneCollected(studentId: String): Boolean {
        return try {
            studentsCollection.document(studentId)
                .update("isCollected", true)
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }


    suspend fun addTeacher(teacher: Teacher): Boolean {
        return try {
            teacher.teacher_id?.let {
                teachersCollection.document(it)
                    .set(teacher)
                    .await()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Öğretmeni alma işlevi
    suspend fun getTeacher(teacherId: String): Teacher? {
        return try {
            val documentSnapshot = teachersCollection.document(teacherId)
                .get()
                .await()
            documentSnapshot.toObject(Teacher::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    // Öğrenci kodunu güncelleme
    suspend fun updateStudentCode(studentId: String, code: String): Boolean {
        return try {
            studentsCollection.document(studentId)
                .update("student_code", code)
                .await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Öğretmen için sınıf listesini getirme
    suspend fun getStudentsByClass(className: String): List<Student> {
        return try {
            val querySnapshot = studentsCollection
                .whereEqualTo("student_class", className)
                .get()
                .await()
            querySnapshot.toObjects(Student::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    
}

