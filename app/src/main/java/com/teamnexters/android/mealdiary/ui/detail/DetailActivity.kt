package com.teamnexters.android.mealdiary.ui.detail

import android.os.Bundle
import android.util.Log
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.base.LifecycleState
import com.teamnexters.android.mealdiary.databinding.ActivityDetailBinding
import com.teamnexters.android.mealdiary.util.extension.observe
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.activity_detail

    override val viewModel: DetailViewModel.ViewModel by viewModel()

    private val viewPagerAdapter: DetailPhotoAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.toLifecycleState(LifecycleState.OnCreate(getScreen()))

        binding.viewModel = viewModel

        vpDetailPhotoList.adapter = viewPagerAdapter

        observe(viewModel.diary) {
            viewPagerAdapter.submitList(it.photoUrls)
            Log.d("GetDiaryItem", "내용 : ${it}")
        }

        disposables.addAll(
        )
    }

}
