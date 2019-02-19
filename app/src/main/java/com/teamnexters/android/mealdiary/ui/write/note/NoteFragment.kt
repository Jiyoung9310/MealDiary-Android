package com.teamnexters.android.mealdiary.ui.write.note

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
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

        initializeEditText()
        initializeRecyclerView()
        initializeListener()

        observe(viewModel.enableNext) { nextIcon?.isEnabled = it }
        observe(viewModel.restaurantItems) { restaurantAdapter.submitList(it) }
        observe(viewModel.restaurantItemsVisibility) { binding.rvRestaurant.visibility = it }
        observe(viewModel.keyword) { viewModel.toSearch(it.toString()) }
        observe(viewModel.keywordTextColor) { binding.editRestaurant.setTextColor(ContextCompat.getColor(context!!, it)) }

        disposables.addAll(
                viewModel.outputs.ofNavigate()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { navigate(R.id.action_noteFragment_to_photoFragment, it) })
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)

        menu.run {
            nextIcon = findItem(R.id.action_next)
            nextIcon?.isEnabled = viewModel.enableNext.value ?: false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_next -> {
                viewModel.inputs.toClickNext()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initializeEditText() {
        val starDrawable = ContextCompat.getDrawable(context!!, R.drawable.ic_necessary_star)!!.apply {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        }

        binding.editTitle.hint = SpannableString("${binding.editTitle.hint}  ").apply {
            setSpan(ImageSpan(starDrawable, ImageSpan.ALIGN_BASELINE), length - 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        binding.editContent.hint = SpannableString("${binding.editContent.hint}  ").apply {
            setSpan(ImageSpan(starDrawable, ImageSpan.ALIGN_BASELINE), length - 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun initializeRecyclerView() {
        binding.rvRestaurant.run {
            layoutManager = LinearLayoutManager(context)
            adapter = restaurantAdapter
        }
    }

    private fun initializeListener() {
        restaurantAdapter.setCallbacks(object : RestaurantAdapter.Callbacks {
            override fun onClickRestaurantItem(restaurantItem: RestaurantItem) {
                viewModel.inputs.toClickRestaurantItem(restaurantItem)
            }
        })
    }

}
