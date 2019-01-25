package com.teamnexters.android.mealdiary.ui.write.photo

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentPhotoBinding
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel.ViewModel>() {

    private val photoAdapter: PhotoAdapter by inject()

    override val layoutResId: Int = R.layout.fragment_photo

    override val viewModel: PhotoViewModel.ViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        disposables.addAll(
                viewModel.outputs.photoList()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { photoAdapter.submitList(it) })
        )

        initializeRecyclerView()
    }

    private fun initializeRecyclerView() {
        binding.rvPhoto.run {
            layoutManager = GridLayoutManager(context, 3)
            adapter = photoAdapter
        }
    }

}
