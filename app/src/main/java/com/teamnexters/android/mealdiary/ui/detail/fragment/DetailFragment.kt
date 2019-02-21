package com.teamnexters.android.mealdiary.ui.detail.fragment

import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.teamnexters.android.mealdiary.MealDiaryConst
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentDetailBinding
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.observe
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel.ViewModel>() {

    companion object {
        fun newInstance(screen: Screen) : DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(MealDiaryConst.KEY_ARGS, screen)
                }
            }
        }
    }

    override val layoutResId: Int = R.layout.fragment_detail

    override val viewModel: DetailViewModel.ViewModel by viewModel()

    private val photoAdapter: DetailPhotoAdapter by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        binding.vpDetailPhotoList.run {
            //layoutManager = LinearLayoutManager(context!!, RecyclerView.HORIZONTAL, false)
            adapter = photoAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    viewModel.photoPosition.postValue(position+1)
                }

                override fun onPageSelected(position: Int) {
                }

                override fun onPageScrollStateChanged(state: Int) {
                }
            })

        }

        observe(viewModel.detailPhotoList) {
            photoAdapter.submitList(it)
            Log.d("GetDiaryItem", "내용 : $it")
        }
        /*observe(viewModel.photoPosition) {
            binding.vpDetailPhotoList.currentItem = it
        }*/
    }
}