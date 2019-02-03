package com.teamnexters.android.mealdiary.ui.write.restaurant

import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentRestaurantBinding
import com.teamnexters.android.mealdiary.util.extension.hideKeyboard
import com.teamnexters.android.mealdiary.util.extension.observe
import com.teamnexters.android.mealdiary.util.extension.showKeyboard
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class RestaurantFragment : BaseFragment<FragmentRestaurantBinding, RestaurantViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_restaurant

    override val viewModel: RestaurantViewModel.ViewModel by viewModel()

    private val restaurantAdapter: RestaurantAdapter by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        initializeRecyclerView()

        observe(viewModel.restaurantItems) { restaurantAdapter.submitList(it) }
    }

    override fun onStart() {
        super.onStart()

        binding.editKeyword.showKeyboard()
    }

    private fun initializeRecyclerView() {
        binding.rvRestaurant.run {
            layoutManager = LinearLayoutManager(context)
            adapter = restaurantAdapter
        }

        restaurantAdapter.setCallbacks(object: RestaurantAdapter.Callbacks {
            override fun onClickRestaurantItem(restaurantItem: RestaurantItem) {
                viewModel.inputs.toClickRestauntItem(restaurantItem)
            }
        })
    }

}
