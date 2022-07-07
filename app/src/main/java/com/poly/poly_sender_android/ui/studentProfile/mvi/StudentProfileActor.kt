package com.poly.poly_sender_android.ui.studentProfile.mvi

import android.content.Intent
import android.net.Uri
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Actor
import com.poly.poly_sender_android.ui.students.mvi.StudentsEffect
import com.poly.poly_sender_android.util.MessageConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StudentProfileActor: Actor<StudentProfileState, StudentProfileWish, StudentProfileEffect>() {

    override suspend fun effect(
        state: StudentProfileState,
        wish: StudentProfileWish
    ): Flow<StudentProfileEffect?> = flow {
        when (wish) {
            is StudentProfileWish.SetStudent -> {
                try {
                    emit(StudentProfileEffect.Success(wish.student))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: MessageConstants.ERROR_UNKNOWN_EXCEPTION
                    emit(StudentProfileEffect.Failure(errorMessage))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}