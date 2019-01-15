package com.teamnexters.android.mealdiary.ui.write

import android.os.Bundle
import android.util.Log
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.base.LifecycleState
import com.teamnexters.android.mealdiary.databinding.ActivityWriteBinding
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class WriteActivity : BaseActivity<ActivityWriteBinding, WriteViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.activity_write

    override val viewModel: WriteViewModel.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d("MY_LOG", "onCreate")

        viewModel.toLifecycleState(LifecycleState.OnCreate(getScreen()))

        binding.viewModel = viewModel

        disposables.addAll(
                viewModel.outputs.ofNavigateToMain()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = {
                            Log.d("MY_LOG", "삐니시")
                            finish()
                        })
        )
    }

}
