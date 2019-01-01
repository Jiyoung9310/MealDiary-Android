package com.teamnexters.android.mealdiary.base

import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

internal abstract class BaseFragment : Fragment() {

    protected val disposables = CompositeDisposable()

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()

        disposables.clear()
    }

}