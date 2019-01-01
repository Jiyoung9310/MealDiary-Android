package com.teamnexters.android.mealdiary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable

internal abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    protected val disposables = CompositeDisposable()

    abstract val layoutResId: Int

    lateinit var binding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.setLifecycleOwner(this)

        return binding.root
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()

        disposables.clear()
    }

}