package com.teamnexters.android.mealdiary.ui.write

import android.os.Bundle
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.databinding.ActivityWriteBinding
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class WriteActivity : BaseActivity<ActivityWriteBinding, WriteViewModel.ViewModel>() {

    override val viewModel: WriteViewModel.ViewModel by viewModel()

    override val layoutResId: Int = R.layout.activity_write

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        disposables.addAll(
                viewModel.outputs.ofNavigateToMain()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { finish() })
        )
    }

}
