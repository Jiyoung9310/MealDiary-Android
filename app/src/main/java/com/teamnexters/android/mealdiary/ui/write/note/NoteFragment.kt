package com.teamnexters.android.mealdiary.ui.write.note

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentNoteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class NoteFragment : BaseFragment<FragmentNoteBinding, NoteViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_note

    override val viewModel: NoteViewModel.ViewModel by viewModel()

    private lateinit var nextIcon: MenuItem

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        binding.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.note_menu, menu)

        menu?.let {
            nextIcon = it.findItem(R.id.action_next)
            nextIcon.isEnabled = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_next -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }

}
