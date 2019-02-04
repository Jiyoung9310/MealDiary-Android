package com.teamnexters.android.mealdiary.ui.write.score

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentScoreBinding
import com.teamnexters.android.mealdiary.util.extension.observe
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ScoreFragment : BaseFragment<FragmentScoreBinding, ScoreViewModel.ViewModel>() {

    private val scoreAdapter: ScoreAdapter by inject()

    override val layoutResId: Int = R.layout.fragment_score

    override val viewModel: ScoreViewModel.ViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        initializeRecyclerView()

        observe(viewModel.scoreItems) { scoreAdapter.submitList(it) }
    }

    private fun initializeRecyclerView() {
        binding.rvScore.run {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = scoreAdapter
        }
    }

}
