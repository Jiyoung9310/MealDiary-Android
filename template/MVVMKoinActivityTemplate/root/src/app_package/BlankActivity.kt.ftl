package ${packageName}.${packName}

import android.os.Bundle
import ${applicationPackage?replace('.debug|.qa|.release', '', 'r')}.R
import ${applicationPackage?replace('.debug|.qa|.release', '', 'r')}.base.BaseActivity
import ${applicationPackage?replace('.debug|.qa|.release', '', 'r')}.databinding.Activity${Name}Binding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ${className}: BaseActivity<Activity${Name}Binding, ${Name}ViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.${activityName}

    override val viewModel: ${Name}ViewModel.ViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
    }

}
