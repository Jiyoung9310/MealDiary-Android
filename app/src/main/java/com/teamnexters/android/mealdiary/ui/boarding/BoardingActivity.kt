package com.teamnexters.android.mealdiary.ui.boarding

import android.os.Bundle
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.databinding.ActivityBoardingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class BoardingActivity : BaseActivity<ActivityBoardingBinding, BoardingViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.activity_boarding

    override val viewModel: BoardingViewModel.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
    }

}
