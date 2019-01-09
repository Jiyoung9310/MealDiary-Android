package com.teamnexters.android.mealdiary.ui.write

import android.os.Bundle
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.databinding.ActivityWriteBinding
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import kotlinx.android.synthetic.main.activity_write.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class WriteActivity : BaseActivity<ActivityWriteBinding>() {

    private val viewModel: WriteViewModel by viewModel()

    override val layoutResId: Int = R.layout.activity_write

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val text = intent.getStringExtra("text")

        binding.viewModel = viewModel
        edit_content.setText(text) //TODO 엥 왜 안뜸..ㅠㅠ

        disposables.addAll(
                viewModel.ofNavigateToMain()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { finish() })
        )
    }

}
