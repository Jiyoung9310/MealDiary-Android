package com.teamnexters.android.mealdiary.ui.write.score

import android.os.Bundle
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentScoreBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ScoreFragment : BaseFragment<FragmentScoreBinding, ScoreViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_score

    override val viewModel: ScoreViewModel.ViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

}
