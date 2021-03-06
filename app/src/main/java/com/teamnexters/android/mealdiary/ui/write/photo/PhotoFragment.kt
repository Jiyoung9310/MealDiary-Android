package com.teamnexters.android.mealdiary.ui.write.photo

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.teamnexters.android.mealdiary.R
import com.teamnexters.android.mealdiary.base.BaseFragment
import com.teamnexters.android.mealdiary.databinding.FragmentPhotoBinding
import com.teamnexters.android.mealdiary.util.extension.hideKeyboard
import com.teamnexters.android.mealdiary.util.extension.subscribeOf
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class PhotoFragment : BaseFragment<FragmentPhotoBinding, PhotoViewModel.ViewModel>() {

    private val photoAdapter: PhotoAdapter by inject()

    private var nextIcon: MenuItem? = null

    override val layoutResId: Int = R.layout.fragment_photo

    override val viewModel: PhotoViewModel.ViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        binding.viewModel = viewModel

        disposables.addAll(
                viewModel.outputs.ofPhotoList()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { photoAdapter.submitList(it) }),

                viewModel.outputs.ofSelectedPhotoList()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = {
                            photoAdapter.setSelectedPhotoList(it)

                            nextIcon?.isEnabled = it.isNotEmpty()
                        }),

                viewModel.outputs.ofNavigate()
                        .observeOn(schedulerProvider.ui())
                        .subscribeOf(onNext = { navigate(R.id.action_photoFragment_to_scoreFragment, it) })
        )

        initializeRecyclerView()
    }

    override fun onStart() {
        super.onStart()

        binding.root.hideKeyboard()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.photo_menu, menu)

        menu?.let {
            nextIcon = it.findItem(R.id.action_next)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.action_next -> {
                viewModel.inputs.toClickNext()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun initializeRecyclerView() {
        binding.rvPhoto.run {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(GridDividerDecoration(context))
            adapter = photoAdapter
        }

        photoAdapter.setCallbacks(object : PhotoAdapter.Callbacks {
            override fun onSelectedPhotos(selectedPhotos: List<Photo>) {
                viewModel.inputs.toSelectedPhotoList(selectedPhotos)
            }
        })
    }

}