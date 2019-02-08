package com.teamnexters.android.mealdiary.ui.boarding

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.databinding.ActivityBoardingBinding
import com.teamnexters.android.mealdiary.util.extension.observe
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class BoardingActivity : BaseActivity<ActivityBoardingBinding, BoardingViewModel.ViewModel>() {

    private val boardAdapter: BoardingPagerAdapter by inject()

    override val layoutResId: Int = R.layout.activity_boarding

    override val viewModel: BoardingViewModel.ViewModel by viewModel()

    private val boardSnapHelper = PagerSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initializeView()

        observe(viewModel.boardItems) { boardAdapter.submitList(it) }
        observe(viewModel.boardPosition) { binding.rvBoarding.smoothScrollToPosition(it) }
    }

    private fun initializeView() {
        binding.rvBoarding.apply {
            adapter = boardAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            boardSnapHelper.attachToRecyclerView(this)
            setOnTouchListener{ v, m ->
                true
            }
        }
        binding.indicator.attachToRecyclerView(binding.rvBoarding, boardSnapHelper)
    }

}
