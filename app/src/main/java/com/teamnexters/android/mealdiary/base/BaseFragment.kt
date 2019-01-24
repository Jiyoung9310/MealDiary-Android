package com.teamnexters.android.mealdiary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.teamnexters.android.mealdiary.MealDiaryConst
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.ui.Screen
import io.reactivex.disposables.CompositeDisposable

internal abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected val disposables = CompositeDisposable()

    abstract val layoutResId: Int

    abstract val viewModel: VM

    lateinit var binding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.setLifecycleOwner(this)

        arguments?.let {
            viewModel.toLifecycleState(LifecycleState.OnCreate(arguments?.getSerializable(MealDiaryConst.KEY_ARGS) as Screen))
        }

        return binding.root
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()

        disposables.clear()
    }

    protected fun navigate(@IdRes id: Int, screen: Screen) {
        findNavController().navigate(
                id,
                Bundle().apply {
                    putSerializable(MealDiaryConst.KEY_ARGS, screen)
                }

        )
    }

    protected fun popBackStack() {
        findNavController().popBackStack()
    }

    protected fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean = true) {
        findNavController().popBackStack(destinationId, inclusive)
    }

}