package com.poly.poly_sender_android.ui

import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poly.poly_sender_android.common.Logger
import com.poly.poly_sender_android.common.launchWhenStarted
import com.poly.poly_sender_android.mvi.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel<S: State, W: Wish, E: Effect, N: News>: ViewModel() {

    @Inject
    lateinit var logger: Logger

    abstract val stateFlow: MutableStateFlow<S>
    abstract val newsFlow: MutableSharedFlow<N>
    abstract val wishFlow: MutableSharedFlow<W?>
    abstract val effectFlow: MutableSharedFlow<E?>
    abstract val store: Store<S, W, E, N>

    fun obtainWish(wish: W) {
        viewModelScope.launch {
            wishFlow.emit(wish)
        }
    }

    fun obtainState(state: S) {
        viewModelScope.launch {
            stateFlow.emit(state)
        }
    }

    override fun onCleared() {
        super.onCleared()

        Log.i("TEST3", "Cleared")
    }

    fun bind(lifecycleScope: LifecycleCoroutineScope, mviView: MviView<S, N>) {
        logger.connect(javaClass)

        stateFlow
            .onEach(mviView::renderState)
            .launchWhenStarted(lifecycleScope)

        newsFlow
            .onEach(mviView::renderNews)
            .launchWhenStarted(lifecycleScope)

        lifecycleScope.launch {
            wishFlow.collect { nW ->
                nW?.let { wish ->
                    logger.log("WF collects ${wish.javaClass.simpleName}")
                    val effectFlow = store.actor(stateFlow.value, wish)
                    effectFlow.collect { nE ->
                        nE?.let { effect ->
                            val reduced = store.reducer(stateFlow.value, effect)
                            logger.log("Reduced state - ${reduced.first}")
                            reduced.first?.let { reducedState ->
                                stateFlow.value = reducedState
                            }
                            reduced.second?.let { reducedNews ->
                                newsFlow.emit(reducedNews)
                            }
                        }
                    }
                }
            }
        }
    }
}