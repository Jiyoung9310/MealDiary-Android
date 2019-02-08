package com.teamnexters.android.mealdiary.ui.intro

import android.content.Intent
import android.os.Bundle
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.databinding.ActivityIntroBinding
import com.teamnexters.android.mealdiary.ui.boarding.BoardingActivity
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class IntroActivity : BaseActivity<ActivityIntroBinding, IntroViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.activity_intro

    override val viewModel: IntroViewModel.ViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        disposables.addAll(
                viewModel.outputs.ofNavigate()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = {
                            startActivity(Intent(this, BoardingActivity::class.java))
                            finish()
                        })
        )
    }

}
