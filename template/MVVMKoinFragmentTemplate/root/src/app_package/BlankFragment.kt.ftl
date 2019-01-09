package ${packageName}.${packName}

import android.os.Bundle
import ${applicationPackage?replace('.debug|.qa|.release', '', 'r')}.R
import ${applicationPackage?replace('.debug|.qa|.release', '', 'r')}.base.BaseFragment
import ${applicationPackage?replace('.debug|.qa|.release', '', 'r')}.databinding.Fragment${Name}Binding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ${className}: BaseFragment<Fragment${Name}Binding, ${Name}ViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.${fragmentName}

    override val viewModel: ${Name}ViewModel.ViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

}
