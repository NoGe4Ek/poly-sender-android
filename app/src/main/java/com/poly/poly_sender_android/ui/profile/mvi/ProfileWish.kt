package com.poly.poly_sender_android.ui.profile.mvi

import com.poly.poly_sender_android.mvi.Wish

sealed interface ProfileWish: Wish {
    object FetchUser: ProfileWish
}
