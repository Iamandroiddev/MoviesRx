package com.example.moviesrxjava.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.moviesrxjava.databinding.CurrentlyShowingMovieItemBinding
import com.example.moviesrxjava.fragments.HomeDirections
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.utils.Constants
import java.util.*

class ViewPagerAdapter(private val mContext: Context, private var movieList: ArrayList<Movie>?) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
    private var binding: CurrentlyShowingMovieItemBinding? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val inflater = LayoutInflater.from(mContext)
        binding = CurrentlyShowingMovieItemBinding.inflate(inflater, parent, false)
        return ViewPagerViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        Log.e(TAG, "onBindViewHolder: " + movieList!![position].title)
        holder.binding.currentlyShowingMovieName.setText(movieList!![position].title)
        Glide.with(mContext).load(Constants.ImageBaseURL + movieList!![position].backdrop_path)
                .into(holder.binding.currentlyShowingMovieImage)
        holder.binding.currentlyShowingLayout.setOnClickListener(View.OnClickListener { view ->
            val action= HomeDirections
                    .actionHomeToMovieDetails(movieList!![position].id!!)
            Navigation.findNavController(view).navigate(action)
        })
        holder.binding.currentlyShowingMovieImage.setClipToOutline(true)
        holder.binding.currentlyShowingLayout.setLayoutParams(RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    override fun getItemCount(): Int {
        return if (movieList == null) 0 else movieList!!.size
    }

    inner class ViewPagerViewHolder(binding: CurrentlyShowingMovieItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        internal val binding: CurrentlyShowingMovieItemBinding

        init {
            this.binding = binding
        }
    }

    fun setMovieListResults(movieList: ArrayList<Movie>?) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "ViewPagerAdapter"
    }


}