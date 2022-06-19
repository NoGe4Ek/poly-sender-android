package com.poly.poly_sender_android.ui.studentProfile.mvi

import android.content.Intent
import android.net.Uri
import com.poly.poly_sender_android.App
import com.poly.poly_sender_android.data.models.domainModel.User
import com.poly.poly_sender_android.mvi.Actor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StudentProfileActor: Actor<StudentProfileState, UserDetailsWish, StudentProfileEffect>() {

    override suspend fun effect(
        state: StudentProfileState,
        wish: UserDetailsWish
    ): Flow<StudentProfileEffect?> = flow {
        when (wish) {
            is UserDetailsWish.GetUserDetails -> {
                try {
                    val user = mainRepository.getUser(wish.userId)
                    val friends = mutableListOf<User>()
                    friends.addAll(mainRepository.getUsersByIds(user.friends))

                    emit(StudentProfileEffect.GetUserDetailsSuccess(user, friends))
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(StudentProfileEffect.GetUserDetailsFailure(errorMessage))
                }
            }

            is UserDetailsWish.ExternalEmail -> {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    val emails: Array<String> = arrayOf(wish.email)
                    putExtra(Intent.EXTRA_EMAIL, emails)
                }
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                try {
                    if (intent.resolveActivity(App.appContext.packageManager) != null) {
                        App.appContext.startActivity(intent)
                    } else {
                        emit(
                            StudentProfileEffect.ExternalEmailFailure(
                                "There are no applications capable of processing the request"
                            )
                        )
                    }
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(StudentProfileEffect.ExternalEmailFailure(errorMessage))
                }
                emit(StudentProfileEffect.ExternalSuccess)
            }

            is UserDetailsWish.ExternalCall -> {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${wish.phone}")
                }
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                try {
                    if (intent.resolveActivity(App.appContext.packageManager) != null) {
                        App.appContext.startActivity(intent)
                    } else {
                        emit(
                            StudentProfileEffect.ExternalCallFailure(
                                "There are no applications capable of processing the request"
                            )
                        )
                    }
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(StudentProfileEffect.ExternalCallFailure(errorMessage))
                }
                emit(StudentProfileEffect.ExternalSuccess)
            }

            is UserDetailsWish.ExternalMap -> {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    val geo = "${wish.latitude}, ${wish.longitude}"
                    data = Uri.parse("geo:$geo")
                }
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                try {
                    if (intent.resolveActivity(App.appContext.packageManager) != null) {
                        App.appContext.startActivity(intent)
                    } else {
                        emit(
                            StudentProfileEffect.ExternalMapFailure(
                                "There are no applications capable of processing the request"
                            )
                        )
                    }
                } catch (e: Exception) {
                    val errorMessage = e.message ?: "Unknown exception"
                    emit(StudentProfileEffect.ExternalMapFailure(errorMessage))
                }
                emit(StudentProfileEffect.ExternalSuccess)
            }
        }
    }.flowOn(Dispatchers.IO)
}