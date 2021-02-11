package com.example.moviesrxjava.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesrxjava.databinding.HomeMovieItemBinding
import com.example.moviesrxjava.db.WishListMovie
import com.example.moviesrxjava.fragments.WishListFragmentDirections
import com.example.moviesrxjava.utils.Constants

class WishListAdapter(private val mContext: Context, private var moviesList: List<WishListMovie>?) :
    RecyclerView.Adapter<WishListAdapter.WishListViewHolder>() {

    private val TAG = "WishListAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val binding = HomeMovieItemBinding.inflate(inflater, parent, false)
        return WishListViewHolder(binding)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        holder.binding.movieItemRelativeLayout.setClipToOutline(true)
        holder.binding.movieItemName.setText(moviesList!![position].title)
        holder.binding.movieItemVotes.setText(
            moviesList!![position].vote_count.toString() + ""
        )
        Glide.with(mContext)
            .load(Constants.ImageBaseURLw500 + moviesList!![position].poster_path)
            .into(holder.binding.movieItemImage)
        holder.binding.movieItemRelativeLayout.setOnClickListener(View.OnClickListener { view ->
            val action =
                WishListFragmentDirections.actionWishListToMovieDetails(
                    moviesList!![position].id
                )
            Navigation.findNavController(view).navigate(action)
        })
    }

    override fun getItemCount(): Int {
        return if (moviesList == null) 0 else moviesList!!.size
    }

    class WishListViewHolder(binding: HomeMovieItemBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        internal val binding: HomeMovieItemBinding

        init {
            this.binding = binding
        }
    }

    fun setMoviesList(moviesList: List<WishListMovie>?) {
        Log.e(TAG, "setMoviesList: " + "called")
        this.moviesList = moviesList
        notifyDataSetChanged()
    }
}