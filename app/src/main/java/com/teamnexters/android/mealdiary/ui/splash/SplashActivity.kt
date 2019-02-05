package com.teamnexters.android.mealdiary.ui.splash

import android.content.Intent
import android.os.Bundle
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.databinding.ActivitySplashBinding
import com.teamnexters.android.mealdiary.ui.intro.IntroActivity
import com.teamnexters.android.mealdiary.ui.main.MainActivity
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.activity_splash

    override val viewModel: SplashViewModel.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        disposables.addAll(
                viewModel.outputs.ofNavigate()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = {
                            when(it) {
                                true -> startActivity(Intent(this, MainActivity::class.java))
                                else -> {
                                    startActivity(Intent(this, IntroActivity::class.java))

                                }
                            }
                            finish()

                        })
        )
    }

}
