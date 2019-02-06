package com.teamnexters.android.mealdiary.ui.write.score

import android.os.Bundle
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentScoreBinding
import com.teamnexters.android.mealdiary.util.extension.observe
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ScoreFragment : BaseFragment<FragmentScoreBinding, ScoreViewModel.ViewModel>() {

    private val scoreAdapter: ScoreAdapter by inject()

    override val layoutResId: Int = R.layout.fragment_score

    override val viewModel: ScoreViewModel.ViewModel by viewModel()

    private val scoreLayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

    private val scoreSnapHelper = PagerSnapHelper()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        initializeView()

        observe(viewModel.scoreItems) { scoreAdapter.submitList(it) }
        observe(viewModel.scoreCardPosition) { binding.rvScore.smoothScrollToPosition(it) }

        disposables.addAll(
                viewModel.outputs.ofFinish()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = {
                            activity?.finish()
                        })
        )
    }

    private fun initializeView() {
        binding.rvScore.run {
            layoutManager = scoreLayoutManager
            adapter = scoreAdapter
            scoreSnapHelper.attachToRecyclerView(this)
            setOnTouchListener { _, _ ->
                true
            }
        }

        binding.seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.inputs.toScore(progress)
            }
        })
    }

}
