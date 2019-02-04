package com.teamnexters.android.mealdiary.ui.write.note

import android.os.Bundle
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentNoteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class NoteFragment : BaseFragment<FragmentNoteBinding, NoteViewModel.ViewModel>() {

    override val layoutResId: Int = R.layout.fragment_note

    override val viewModel: NoteViewModel.ViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
    }

}
