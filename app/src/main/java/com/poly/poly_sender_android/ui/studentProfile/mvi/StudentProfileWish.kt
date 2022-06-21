package com.poly.poly_sender_android.ui.studentProfile.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface StudentProfileWish: Wish {
    object Empty: StudentProfileWish
}
