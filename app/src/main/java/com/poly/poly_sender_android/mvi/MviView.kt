package com.poly.poly_sender_android.mvi

interface MviView<S: State, N: News> {
    fun renderState(state: S)
    fun renderNews(new: N)
}