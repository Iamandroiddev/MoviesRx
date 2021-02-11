package com.example.moviesrxjava.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.moviesrxjava.databinding.HomeMovieItemBinding
import com.example.moviesrxjava.fragments.HomeDirections
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.utils.Constants
import java.util.*

class HomeAdapter(private val mContext: Context, private var moviesList: ArrayList<Movie>?) : RecyclerView.Adapter<HomeAdapter.HomeRecyclerViewHolder>() {
    private var binding: HomeMovieItemBinding? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        val inflater = LayoutInflater.from(mContext)
        binding = HomeMovieItemBinding.inflate(inflater, parent, false)
        return HomeRecyclerViewHolder(binding!!)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        holder.binding.movieItemRelativeLayout.setClipToOutline(true)
        holder.binding.movieItemName.setText(moviesList!![position].title)
        //        temp = "";
//        for(String genre : moviesList.get(position).getGenre_names()){
//            temp+= genre + " | ";
//        }
        holder.binding.movieItemVotes.setText(moviesList!![position].vote_count.toString() + "")
        Glide.with(mContext).load(Constants.ImageBaseURLw500 + moviesList!![position].poster_path)
                .into(holder.binding.movieItemImage)
        holder.binding.movieItemRelativeLayout.setOnClickListener({ view ->
            val action= HomeDirections
                    .actionHomeToMovieDetails(moviesList!![position].id!!)
            Navigation.findNavController(view).navigate(action)
        })
    }

    override fun getItemCount(): Int {
        return if (moviesList == null) 0 else moviesList!!.size
    }

    inner class HomeRecyclerViewHolder(binding: HomeMovieItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        val binding: HomeMovieItemBinding

        init {
            this.binding = binding
        }
    }

    fun setMoviesList(moviesList: ArrayList<Movie>?) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

}