package com.teamnexters.android.mealdiary.ui.detail

import android.os.Bundle
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.activity_detail

    override val viewModel: DetailViewModel.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
    }

}
