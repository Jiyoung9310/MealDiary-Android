package com.teamnexters.android.mealdiary.ui.write.note

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentNoteBinding
import com.teamnexters.android.mealdiary.util.extension.observe
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class NoteFragment : BaseFragment<FragmentNoteBinding, NoteViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_note

    override val viewModel: NoteViewModel.ViewModel by viewModel()

    private val restaurantAdapter: RestaurantAdapter by inject()

    private var nextIcon: MenuItem? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        binding.viewModel = viewModel

        initializeRecyclerView()

        observe(viewModel.title) { nextIcon?.isEnabled = it.isNotBlank() }
        observe(viewModel.keyword) { viewModel.toSearch(it) }
        observe(viewModel.restaurantItems) { restaurantAdapter.submitList(it) }
        observe(viewModel.restaurantItemsVisibility) { binding.rvRestaurant.visibility = it}

        disposables.addAll(
                viewModel.outputs.ofNavigate()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { navigate(R.id.action_noteFragment_to_photoFragment, it) })
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.note_menu, menu)

        menu?.let {
            nextIcon = it.findItem(R.id.action_next)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_next -> {
                viewModel.inputs.toClickNext()
            }
        }

        return super.onOptionsItemSelected(item)
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
