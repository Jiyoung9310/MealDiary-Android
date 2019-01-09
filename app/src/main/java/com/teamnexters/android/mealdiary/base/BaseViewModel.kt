package com.teamnexters.android.mealdiary.base

import android.content.Intent
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import com.teamnexters.android.mealdiary.MealDiaryConst
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.RequestCode
import com.teamnexters.android.mealdiary.util.ResultCode
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.ofType
import java.lang.IllegalArgumentException

internal abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    private val activityResultRelay = PublishRelay.create<Triple<RequestCode, ResultCode, Intent?>>()

    private val argumentsRelay = BehaviorRelay.createDefault<Bundle>(Bundle())

    @CallSuper
    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }

    fun toArguments(arguments: Bundle) = argumentsRelay.accept(arguments)
    fun ofArguments(): Observable<Bundle> = argumentsRelay

    fun toActivityResult(requestCode: RequestCode, resultCode: ResultCode, data: Intent?) = activityResultRelay.accept(Triple(requestCode, resultCode, data))
    fun ofActivityResult(requestCode: RequestCode, resultCode: ResultCode, data: Intent?) = activityResultRelay

    inline fun <reified T : Screen> ofScreen(): Observable<T> {
        return argumentsRelay.map {
            it.getSerializable(MealDiaryConst.KEY_ARGS) ?: throw IllegalArgumentException("invalid screen ${T::class.java.simpleName}")
        }.ofType()
    }

}