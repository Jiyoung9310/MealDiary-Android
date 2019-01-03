package com.teamnexters.android.mealdiary.util.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.*
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

private val onNextStub: (Any) -> Unit = {}
private val onSuccessStub: (Any) -> Unit = {}
private val onCompleteStub: () -> Unit = {}
private val onErrorStub: (Throwable) -> Unit = {
    Timber.e("Rx Error - $it")
//    throw RuntimeException(it)
}


@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any> Flowable<T>.subscribeOf(
        onNext: (T) -> Unit = onNextStub,
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub

): Disposable = subscribe(onNext, onError, onComplete)


@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any> Observable<T>.subscribeOf(
        onNext: (T) -> Unit = onNextStub,
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub

): Disposable = subscribe(onNext, onError, onComplete)


@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any> Single<T>.subscribeOf(
        onSuccess: (T) -> Unit = onSuccessStub,
        onError: (Throwable) -> Unit = onErrorStub

): Disposable = subscribe(onSuccess, onError)


@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any> Maybe<T>.subscribeOf(
        onSuccess: (T) -> Unit = onSuccessStub,
        onError: (Throwable) -> Unit = onErrorStub,
        onComplete: () -> Unit = onCompleteStub
): Disposable = subscribe(onSuccess, onError, onComplete)


@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun Completable.subscribeOf(
        onComplete: () -> Unit = onCompleteStub,
        onError: (Throwable) -> Unit = onErrorStub
): Disposable = subscribe(onComplete, onError)

fun <T> Flowable<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this)
}

fun <T> Observable<T>.toLiveData(backPressureStrategy: BackpressureStrategy): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable(backPressureStrategy))
}

fun <T> Single<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Maybe<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Completable.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T, U> Observable<T>.withLatestFromSecond(other: ObservableSource<U>): Observable<U> = withLatestFrom(other, BiFunction { _, u -> u })

@CheckReturnValue
@SchedulerSupport(SchedulerSupport.COMPUTATION)
fun <T> Observable<T>.throttleClick(): Observable<T> = throttleFirst(300, TimeUnit.MILLISECONDS)