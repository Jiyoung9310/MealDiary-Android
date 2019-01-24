package com.teamnexters.android.mealdiary.ui.main

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.tbruyelle.rxpermissions2.RxPermissions
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.data.model.ListItem
import com.teamnexters.android.mealdiary.databinding.ActivityMainBinding
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.NavigationUtil
import com.teamnexters.android.mealdiary.util.Navigator
import com.teamnexters.android.mealdiary.util.extension.observe
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import com.teamnexters.android.mealdiary.util.extension.throttleClick
import io.reactivex.rxkotlin.ofType
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


internal class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel.ViewModel>() {

    private val diaryAdapter: DiaryAdapter by inject()

    override val layoutResId: Int = com.teamnexters.android.mealdiary.R.layout.activity_main

    override val viewModel: MainViewModel.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initializeRecyclerView()

        disposables.addAll(
                viewModel.outputs.ofClickWrite()
                        .throttleClick()
                        .switchMap { RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE) }
                        .subscribeOf(onNext = { granted ->
                            if(granted) {
                                viewModel.inputs.toPermissionState(PermissionState.Granted)
                            } else {
                                viewModel.inputs.toPermissionState(PermissionState.Denied)
                            }
                        }),

                viewModel.outputs.ofPermissionState()
                        .ofType<PermissionState.Granted>()
                        .subscribeOf(onNext = { viewModel.inputs.toNavigateToWrite(Screen.Write.Write) }),

                viewModel.outputs.ofPermissionState()
                        .ofType<PermissionState.Denied>()
                        .subscribeOf(onNext = {
                            AlertDialog.Builder(this)
                                    .setCancelable(false)
                                    .setTitle(getString(R.string.permission_dialog_rational_title))
                                    .setMessage(getString(R.string.permission_storage_message))
                                    .setNegativeButton(getString(R.string.cancel), null)
                                    .setPositiveButton(getString(R.string.permission_dialog_rational_positive_text)) { _, _ ->
                                        NavigationUtil.navigateToSettingActivity(this)
                                    }
                                    .show()
                        }),

                viewModel.outputs.ofNavigateToWrite()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { Navigator.navigateToWrite(this, it) }),

                viewModel.outputs.ofShowDiaryDialog()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = {
                            AlertDialog.Builder(this)
                                    .setMessage(it.message)
                                    .setNegativeButton(it.negativeButtonText) { _, _ -> viewModel.inputs.toClickModify() }
                                    .setPositiveButton(it.positiveButtonText) { _, _ -> viewModel.inputs.toClickDelete() }
                                    .show()
                        })
        )

        observe(viewModel.diaryItems) { diaryAdapter.submitList(it) }
    }

    private fun initializeRecyclerView() {
        binding.rvDiary.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = diaryAdapter
        }

        diaryAdapter.setCallbacks(object : DiaryAdapter.Callbacks {
            override fun onClickDiary(item: ListItem.DiaryItem) {
                viewModel.toClickDiaryItem(item.diary)
            }
        })
    }

}
