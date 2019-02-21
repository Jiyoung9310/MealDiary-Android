package com.teamnexters.android.mealdiary.ui.boarding

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.databinding.ActivityBoardingBinding
import com.teamnexters.android.mealdiary.ui.main.MainActivity
import com.teamnexters.android.mealdiary.util.extension.observe
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import kotlinx.android.synthetic.main.activity_boarding.*
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

        disposables.addAll(
                viewModel.outputs.ofClickNext()
                        .throttleClick()
                        .subscribeOf(onNext = {
                            viewModel.boardPosition.apply {
                                this.value?.let {
                                    when(it.compareTo(2)) {
                                        0 -> viewModel.inputs.toClickSkip()
                                        -1 -> this.postValue(it.plus(1))
                                    }
                                }

                            }
                        }),

                viewModel.outputs.ofNavigate()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        })
        )

        observe(viewModel.boardItems) { boardAdapter.submitList(it) }
        observe(viewModel.boardPosition) { binding.rvBoarding.smoothScrollToPosition(it) }
    }

    private fun initializeView() {
        binding.rvBoarding.apply {
            adapter = boardAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            boardSnapHelper.attachToRecyclerView(this)
//            setOnTouchListener{ v, m ->
//                true
//            }
        }
        binding.indicator.attachToRecyclerView(rvBoarding, boardSnapHelper)
    }

}
