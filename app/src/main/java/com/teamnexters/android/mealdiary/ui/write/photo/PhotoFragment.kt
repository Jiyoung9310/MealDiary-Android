package com.teamnexters.android.mealdiary.ui.write.photo

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentPhotoBinding
import com.teamnexters.android.mealdiary.util.NavigationUtil
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

internal class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_photo

    override val viewModel: PhotoViewModel.ViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        disposables.addAll(
                viewModel.outputs.photoList()
                        .subscribeOf(onNext = {
                            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                            Timber.d(it.toString())
                        })
        )
    }

}
