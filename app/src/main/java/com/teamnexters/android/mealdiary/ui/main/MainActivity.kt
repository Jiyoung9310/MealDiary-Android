package com.teamnexters.android.mealdiary.ui.main

import android.Manifest
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
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
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.teamnexters.android.mealdiary.ui.write.WriteParam

internal class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel.ViewModel>() {

    private val diaryAdapter: DiaryAdapter by inject()

    override val layoutResId: Int = R.layout.activity_main

    override val viewModel: MainViewModel.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0F
        supportActionBar?.setBackgroundDrawable(this.getDrawable(R.color.white))

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
                        .subscribeOf(onNext = { viewModel.inputs.toNavigateToWrite(Screen.Write.Restaurant(WriteParam())) }),

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

        observe(viewModel.diaryItems) {
            diaryAdapter.submitList(it)
            visibleRecyclerView(it)
        }
    }

    private fun visibleRecyclerView(items: List<ListItem.DiaryItem>?) {
        items?.let {
            when(it.isEmpty()) {
                true -> {
                    binding.frameEmpty.visibility = View.VISIBLE
                    binding.rvDiary.visibility = View.GONE
                }
                false -> {
                    binding.frameEmpty.visibility = View.GONE
                    binding.rvDiary.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initializeRecyclerView() {
        visibleRecyclerView(viewModel.diaryItems.value)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.teamnexters.android.mealdiary.R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.action_todo -> {
                Toast.makeText(applicationContext, "todo 버튼 클릭됨", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_search -> {
                Toast.makeText(applicationContext, "search 버튼 클릭됨", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
