package com.example.moviesrxjava.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesrxjava.databinding.HomeMovieItemBinding
import com.example.moviesrxjava.fragments.ActorDetailsDirections
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.utils.Constants
import java.util.*

class KnownForMoviesAdapter(private val mContext: Context? = null, private var moviesList: ArrayList<Movie>?) : RecyclerView.Adapter<KnownForMoviesAdapter.KnowForViewHolder>() {
    private var binding: HomeMovieItemBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnowForViewHolder {
        val inflater = LayoutInflater.from(mContext)
        binding = HomeMovieItemBinding.inflate(inflater, parent, false)
        return KnowForViewHolder(binding!!)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: KnowForViewHolder, position: Int) {
        holder.binding.movieItemRelativeLayout.setClipToOutline(true)
        holder.binding.movieItemName.setText(moviesList!!.get(position).title)
        //        temp = "";
//        for(String genre : moviesList.get(position).getGenre_names()){
//            temp+= genre + " | ";
//        }
        holder.binding.movieItemVotes.setText(moviesList!!.get(position).vote_count.toString() + "")
        Glide.with(mContext!!).load(Constants.ImageBaseURLw500 + moviesList!!.get(position).poster_path)
                .into(holder.binding.movieItemImage)
        holder.binding.movieItemRelativeLayout.setOnClickListener(View.OnClickListener { view ->
            val action = ActorDetailsDirections
                    .actionActorDetailsToMovieDetails2(moviesList!!.get(position).id!!)
            Navigation.findNavController(view).navigate(action)
        })
    }

    override fun getItemCount(): Int {
        return if (moviesList == null) 0 else moviesList!!.size
    }

    class KnowForViewHolder(binding: HomeMovieItemBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
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