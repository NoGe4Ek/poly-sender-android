package com.poly.poly_sender_android.mvi

interface Reducer<S: State, E: Effect, N: News> {
    fun reduce(state: S, effect: E): Pair<S?, N?>
    operator fun invoke(state: S, effect: E) = reduce(state, effect)
}