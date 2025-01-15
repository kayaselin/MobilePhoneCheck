package com.example.mobilephonecheck.di

import com.example.mobilephonecheck.data.datasource.FirebaseDataSource
import com.example.mobilephonecheck.data.repository.StudentRepository
import com.example.mobilephonecheck.data.repository.TeacherRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    // FirebaseDataSource bağımlılığını sağlar
    @Provides
    @Singleton
    fun provideFirebaseDataSource(): FirebaseDataSource {
        return FirebaseDataSource()
    }

    // StudentRepository bağımlılığını sağlar
    @Provides
    @Singleton
    fun provideStudentRepository(
        firebaseDataSource: FirebaseDataSource
    ): StudentRepository {
        return StudentRepository(firebaseDataSource)
    }

    @Provides
    @Singleton
    fun provideTeacherRepository(
        firebaseDataSource: FirebaseDataSource
    ): TeacherRepository {
        return TeacherRepository(firebaseDataSource)
    }
}
