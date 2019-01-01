package com.teamnexters.android.mealdiary.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable

internal abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {

    protected val disposables = CompositeDisposable()

    abstract val layoutResId: Int

    protected val binding by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView<VB>(this, layoutResId)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding.setLifecycleOwner(this)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        disposables.clear()
    }
}