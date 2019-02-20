package com.teamnexters.android.mealdiary.ui.detail

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.base.LifecycleState
import com.teamnexters.android.mealdiary.databinding.ActivityDetailBinding
import com.teamnexters.android.mealdiary.ui.detail.fragment.DetailFragment
import com.teamnexters.android.mealdiary.util.extension.observe
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class DetailActivity : BaseActivity<ActivityDetailBinding, DetailActivityViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.activity_detail

    override val viewModel: DetailActivityViewModel.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initializeFragment()
    }

    private fun initializeFragment() {
        val detailFragment = supportFragmentManager.findFragmentById(R.id.container_fragment)

        if(detailFragment == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container_fragment, DetailFragment.newInstance(getScreen()))
                    .commit()
        }
    }

}
