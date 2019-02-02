package com.teamnexters.android.mealdiary.ui.write

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.teamnexters.android.mealdiary.MealDiaryConst
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseActivity
import com.teamnexters.android.mealdiary.base.LifecycleState
import com.teamnexters.android.mealdiary.databinding.ActivityWriteBinding
import com.teamnexters.android.mealdiary.ui.Screen
import com.teamnexters.android.mealdiary.util.extension.observe
import com.teamnexters.android.mealdiary.util.setToolbarResources
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import timber.log.Timber


internal class WriteActivity : BaseActivity<ActivityWriteBinding, WriteViewModel.ViewModel>() {

    private val navController: NavController
        get() = findNavController(R.id.navigation_fragment)

    override val layoutResId: Int = R.layout.activity_write

    override val viewModel: WriteViewModel.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.toLifecycleState(LifecycleState.OnCreate(getScreen()))

        binding.viewModel = viewModel

        observe(viewModel.toolbarResources) { setToolbarResources(binding.toolbar, it) }

        initializeNavigation()
    }

    override fun onBackPressed() {
        if(navController.currentDestination?.id == R.id.photoFragment) {
            finish()
        }

        navController.navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                if(navController.currentDestination?.id == R.id.photoFragment) {
                    finish()
                }
                navController.navigateUp()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun initializeNavigation() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        findNavController(R.id.navigation_fragment)
                .setGraph(
                        R.navigation.navigation_write,
                        Bundle().apply {
                            putSerializable(MealDiaryConst.KEY_ARGS, Screen.Write.Photo)
                        }
                )

        setupActionBarWithNavController(navController)

        Timber.d("해싱 ${getKeyHash(this)}")
    }

    fun getKeyHash(context: Context): String? {
        val packageInfo = packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)

        for(signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
            } catch(e: NoSuchAlgorithmException) {
//                Log.w(FragmentActivity.TAG, "Unable to get MessageDigest. signature=$signature", e)
            }

        }
        return null
    }

}
