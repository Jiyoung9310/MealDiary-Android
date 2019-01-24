package com.teamnexters.android.mealdiary.ui.write.photo

import android.os.Bundle
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentPhotoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_photo

    override val viewModel: PhotoViewModel.ViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

}
