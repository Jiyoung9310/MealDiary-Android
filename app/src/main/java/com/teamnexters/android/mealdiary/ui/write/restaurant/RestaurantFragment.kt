package com.teamnexters.android.mealdiary.ui.write.restaurant

import android.os.Bundle
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentRestaurantBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class RestaurantFragment : BaseFragment<FragmentRestaurantBinding, RestaurantViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_restaurant

    override val viewModel: RestaurantViewModel.ViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

}
