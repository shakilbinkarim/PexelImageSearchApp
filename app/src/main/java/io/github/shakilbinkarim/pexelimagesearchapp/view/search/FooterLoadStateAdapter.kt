package io.github.shakilbinkarim.pexelimagesearchapp.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.shakilbinkarim.pexelimagesearchapp.databinding.FooterPhotoLoadStateBinding

class FooterLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FooterLoadStateAdapter.FooterLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: FooterLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterLoadStateViewHolder {
        val binding = FooterPhotoLoadStateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FooterLoadStateViewHolder(binding)
    }

    inner class FooterLoadStateViewHolder(private val binding: FooterPhotoLoadStateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnPexelFooterRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                pbPexelPhotoLoadFooter.isVisible = loadState is LoadState.Loading
                btnPexelFooterRetry.isVisible = loadState !is LoadState.Loading
                tvPexelFooterError.isVisible = loadState !is LoadState.Loading
            }
        }

    }

}