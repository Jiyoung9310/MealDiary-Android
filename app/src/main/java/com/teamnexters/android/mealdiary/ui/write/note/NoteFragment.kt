package com.teamnexters.android.mealdiary.ui.write.note

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentNoteBinding
import com.teamnexters.android.mealdiary.util.extension.observe
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import io.reactivex.Observable
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class NoteFragment : BaseFragment<FragmentNoteBinding, NoteViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_note

    override val viewModel: NoteViewModel.ViewModel by viewModel()

    private lateinit var nextIcon: MenuItem

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        binding.viewModel = viewModel

        observe(viewModel.nextEnable) { nextIcon.isEnabled = it }

        disposables.addAll(
                Observable.merge(viewModel.ofHashTagTextParam(), viewModel.outputs.ofHashTagFocused())
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { binding.editTag.setSelection(binding.editTag.length()) }),

                viewModel.ofNavigate()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { navigate(R.id.action_noteFragment_to_photoFragment, it) })
        )

        binding.editTag.setOnFocusChangeListener { v, hasFocus ->
            viewModel.inputs.toHashTagFocused(hasFocus)
        }
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

}
