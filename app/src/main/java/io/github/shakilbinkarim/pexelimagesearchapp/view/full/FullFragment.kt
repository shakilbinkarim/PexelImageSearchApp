package io.github.shakilbinkarim.pexelimagesearchapp.view.full

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.github.shakilbinkarim.pexelimagesearchapp.R
import io.github.shakilbinkarim.pexelimagesearchapp.databinding.FragmentFullBinding

class FullFragment : Fragment(R.layout.fragment_full) {

    private val args by navArgs<FullFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFullBinding.bind(view)
        binding.apply {
            val photo = args.pexelData
            Glide.with(this@FullFragment)
                .load(photo.src.original)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbFull.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbFull.isVisible = false
                        tvPhotographer.isVisible = true
                        return false
                    }
                })
                .into(ivFullImage)
            val uri = Uri.parse(photo.photographer_url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            tvPhotographer.apply {
                text = "Photo captured by ${photo.photographer}"
                setOnClickListener {context.startActivity(intent)}
                paint.isUnderlineText = true
            }
        }
    }
}