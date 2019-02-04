package com.teamnexters.android.mealdiary.ui.write.restaurant

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentRestaurantBinding
import com.teamnexters.android.mealdiary.util.extension.observe
import com.teamnexters.android.mealdiary.util.extension.showKeyboard
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class RestaurantFragment : BaseFragment<FragmentRestaurantBinding, RestaurantViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_restaurant

    override val viewModel: RestaurantViewModel.ViewModel by viewModel()

    private val restaurantAdapter: RestaurantAdapter by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        binding.viewModel = viewModel

        initializeRecyclerView()

        observe(viewModel.restaurantItems) { restaurantAdapter.submitList(it) }
        observe(viewModel.listVisibility) { binding.rvRestaurant.visibility = it}

        disposables.addAll(
                viewModel.outputs.ofNavigate()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { navigate(R.id.action_restaurantFragment_to_noteFragment, it) })
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.restaurant_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_skip -> {
                viewModel.inputs.toClickSkip()
            }
        }

        return super.onOptionsItemSelected(item)
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
                viewModel.inputs.toClickRestaurantItem(restaurantItem)
            }
        })
    }

}
