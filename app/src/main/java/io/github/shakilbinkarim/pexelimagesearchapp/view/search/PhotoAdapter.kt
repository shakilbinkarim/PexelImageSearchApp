package io.github.shakilbinkarim.pexelimagesearchapp.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.github.shakilbinkarim.pexelimagesearchapp.R
import io.github.shakilbinkarim.pexelimagesearchapp.databinding.ItemPexelPhotoBinding
import io.github.shakilbinkarim.pexelimagesearchapp.model.PexelPhoto

class PhotoAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<PexelPhoto, PhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<PexelPhoto>() {

            override fun areItemsTheSame(
                oldItem: PexelPhoto,
                newItem: PexelPhoto
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PexelPhoto,
                newItem: PexelPhoto
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ItemPexelPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }

    inner class PhotoViewHolder(private val binding: ItemPexelPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    item?.let {
                        listener.onItemClick(it)
                    }
                }
            }
        }

        fun bind(photo: PexelPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.src.original)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivPexelPhoto)
                tvUserName.text = photo.photographer
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: PexelPhoto)
    }
}