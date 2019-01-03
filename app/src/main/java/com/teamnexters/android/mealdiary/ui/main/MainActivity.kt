package com.teamnexters.android.mealdiary.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.databinding.ActivityMainBinding
import com.teamnexters.android.mealdiary.util.Navigator
import com.teamnexters.android.mealdiary.util.extension.observe
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: MainViewModel by viewModel()

    private val diaryAdapter: DiaryAdapter by inject()

    override val layoutResId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initializeRecyclerView()

        disposables.addAll(
                viewModel.ofNavigateToWrite()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { Navigator.navigateToWrite(this) })
        )

        observe(viewModel.diaryItems) { diaryAdapter.submitList(it) }
    }

    private fun initializeRecyclerView() {
        binding.rvDiary.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = diaryAdapter
        }
    }
}
