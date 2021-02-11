package com.example.moviesrxjava.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesrxjava.databinding.MovieItemBinding
import com.example.moviesrxjava.fragments.SearchMoviesDirections
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.utils.Constants
import java.util.*

class CategoryMoviesAdapter(private val mContext: Context, private var moviesList: ArrayList<Movie>?) : RecyclerView.Adapter<CategoryMoviesAdapter.CategoryMovieRecyclerViewHolder>() {


    private val TAG = "CategoryMoviesAdapter"
    private var binding: MovieItemBinding? = null
    private var temp: String? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMovieRecyclerViewHolder {
        val inflater = LayoutInflater.from(mContext)
        binding = MovieItemBinding.inflate(inflater, parent, false)
        return CategoryMovieRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryMovieRecyclerViewHolder, position: Int) {
        holder.binding!!.movieName.setText(moviesList!![position].title)
        temp = ""
        for (i in 0 until moviesList!![position].genre_ids!!.size) {
            if (i == moviesList!![position].genre_ids!!.size - 1) temp += Constants.genreMap.get(moviesList!![position].genre_ids!!.get(i)) else temp += Constants.genreMap.get(moviesList!![position].genre_ids!!.get(i)).toString() + " | "
        }
        holder.binding.movieGenre.setText(temp)
        holder.binding.movieRating.setRating(moviesList!![position].vote_average?.toFloat()?.div(2)!!)
        val movieYear: List<String>? = moviesList!![position].release_date?.split("-")
        holder.binding.movieYear.setText(movieYear?.get(0))
        Glide.with(mContext).load(Constants.ImageBaseURL + moviesList!![position].poster_path)
                .into(holder.binding.movieImage)
        holder.binding.movieItemLayout.setOnClickListener({ view ->
            val action = SearchMoviesDirections.actionSearchMoviesToMovieDetails(moviesList!![position].id!!)
            Navigation.findNavController(view).navigate(action)
        })
        holder.binding.movieItemLayout.setClipToOutline(true)
    }

    override fun getItemCount(): Int {
        return if (moviesList == null) 0 else moviesList!!.size
    }

    class CategoryMovieRecyclerViewHolder(binding: MovieItemBinding?) : RecyclerView.ViewHolder(binding!!.getRoot()) {
        val binding: MovieItemBinding?
        init {
            this.binding = binding
        }
    }

    fun setList(list: ArrayList<Movie>?) {
        moviesList = list
        notifyDataSetChanged()
    }
}