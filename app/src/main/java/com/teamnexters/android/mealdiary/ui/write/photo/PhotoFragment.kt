package com.teamnexters.android.mealdiary.ui.write.photo

import android.Manifest
import android.os.Bundle
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
                RxPermissions(this)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe { granted ->
                            if(granted) {
                                viewModel.inputs.onPermissionGranted()
                            } else {
                                context?.let {
                                    AlertDialog.Builder(it)
                                            .setCancelable(false)
                                            .setTitle(getString(R.string.permission_dialog_rational_title))
                                            .setMessage(getString(R.string.permission_storage_message))
                                            .setNegativeButton(getString(R.string.cancel), null)
                                            .setPositiveButton(getString(R.string.permission_dialog_rational_positive_text)) { _, _ ->
                                                NavigationUtil.navigateToSettingActivity(it)
                                            }
                                            .show()
                                }
                            }
                        },

                viewModel.outputs.photoList()
                        .subscribeOf(onNext = { Timber.d(it.toString()) })
        )
    }

}
