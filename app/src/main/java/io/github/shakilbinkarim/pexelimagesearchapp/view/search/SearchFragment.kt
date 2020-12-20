package io.github.shakilbinkarim.pexelimagesearchapp.view.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import io.github.shakilbinkarim.pexelimagesearchapp.R
import io.github.shakilbinkarim.pexelimagesearchapp.databinding.FragmentSearchBinding
import io.github.shakilbinkarim.pexelimagesearchapp.model.PexelPhoto
import io.github.shakilbinkarim.pexelimagesearchapp.viewmodel.SearchViewModel

@AndroidEntryPoint
class SearchFragment: Fragment(R.layout.fragment_search),
    PhotoAdapter.OnItemClickListener {

    private val viewModel by viewModels<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        val adapter = PhotoAdapter(this)
        binding.apply {
            rvGallery.setHasFixedSize(true)
            rvGallery.itemAnimator = null
            rvGallery.adapter = adapter.withLoadStateHeaderAndFooter(
                header = FooterLoadStateAdapter { adapter.retry() },
                footer = FooterLoadStateAdapter { adapter.retry() },
            )
            btnRetry.setOnClickListener { adapter.retry() }
        }
        dealWithObservables(adapter)
        setupLoadStateListenerForAdapter(adapter)
        setHasOptionsMenu(true)
    }

    private fun setupLoadStateListenerForAdapter(adapter: PhotoAdapter) {
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                pbGallery.isVisible = loadState.source.refresh is LoadState.Loading
                rvGallery.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error
                val noResult = loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        adapter.itemCount < 1
                if (noResult) {
                    rvGallery.isVisible = false
                    tvNoResult.isVisible = true
                } else {
                    tvNoResult.isVisible = false
                }
            }
        }
    }

    private fun dealWithObservables(adapter: PhotoAdapter) {
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.rvGallery.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = true

        })
    }

    override fun onItemClick(photo: PexelPhoto) {
        val action = SearchFragmentDirections.actionSearchFragmentToFullFragment(photo)
        findNavController().navigate(action)
    }
}