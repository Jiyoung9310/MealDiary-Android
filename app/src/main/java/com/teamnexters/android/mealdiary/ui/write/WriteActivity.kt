package com.teamnexters.android.mealdiary.ui.write

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.base.LifecycleState
import com.teamnexters.android.mealdiary.databinding.ActivityWriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class WriteActivity : BaseActivity<ActivityWriteBinding, WriteViewModel.ViewModel>() {

    private val navController: NavController
        get() = findNavController(R.id.navigation_fragment)

    override val layoutResId: Int = R.layout.activity_write

    override val viewModel: WriteViewModel.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.toLifecycleState(LifecycleState.OnCreate(getScreen()))

        binding.viewModel = viewModel

        initializeNavigation()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId ?: 0) {
            android.R.id.home -> navController.navigateUp()
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun initializeNavigation() {
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
    }


}
