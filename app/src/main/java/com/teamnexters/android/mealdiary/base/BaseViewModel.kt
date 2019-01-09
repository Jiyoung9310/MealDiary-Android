package com.teamnexters.android.mealdiary.base

import android.content.Intent
import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.RequestCode
import com.teamnexters.android.mealdiary.util.ResultCode
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType

internal abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    private val lifecycleStateRelay = PublishRelay.create<LifecycleState>()

    @CallSuper
    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }

    fun toLifecycleState(lifecycleState: LifecycleState) = lifecycleStateRelay.accept(lifecycleState)

    fun ofLifecycleState(): Observable<LifecycleState> = lifecycleStateRelay

    inline fun <reified T : Screen> ofScreen(): Observable<T> {
        return ofLifecycleState().ofType<LifecycleState.OnCreate>().map { it.screen }.ofType()
    }

}