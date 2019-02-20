package com.teamnexters.android.mealdiary.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.base.LifecycleState
import com.teamnexters.android.mealdiary.databinding.ActivityDetailBinding
import com.teamnexters.android.mealdiary.util.extension.observe
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.activity_detail

    override val viewModel: DetailViewModel.ViewModel by viewModel()

    private val photoAdapter: DetailPhotoAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.toLifecycleState(LifecycleState.OnCreate(getScreen()))

        binding.viewModel = viewModel

        disposables.addAll(

        )

        binding.vpDetailPhotoList.run {
            layoutManager = LinearLayoutManager(this@DetailActivity)
            adapter = photoAdapter
        }

        observe(viewModel.diary) {
            photoAdapter.submitList(it.photoUrls)
            Log.d("GetDiaryItem", "내용 : ${it}")
        }
        observe(viewModel.photoPosition) { binding.vpDetailPhotoList.smoothScrollToPosition(it) }
    }

}
