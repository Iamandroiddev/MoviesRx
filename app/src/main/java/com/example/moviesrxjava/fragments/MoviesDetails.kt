package com.example.moviesrxjava.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesrxjava.viewmodels.HomeViewModel
import com.example.moviesrxjava.R
import com.example.moviesrxjava.adapters.CastAdapter
import com.example.moviesrxjava.databinding.FragmentMoviesDetailsBinding
import com.example.moviesrxjava.db.WishListMovie
import com.example.moviesrxjava.dialog.VideoDialog
import com.example.moviesrxjava.models.Cast
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.models.Video
import com.example.moviesrxjava.utils.Constants
import com.google.gson.JsonArray
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MoviesDetails : Fragment() {

    companion object {
        private val TAG = "MovieDetails"
    }

    private var viewModel: HomeViewModel? = null
    private var binding: FragmentMoviesDetailsBinding? = null
    private var movieId: Int? = null
    private var queryMap: HashMap<String, String>? = null
    private var temp: String? = null
    private var videoId: String? = null
    private var adapter: CastAdapter? = null
    private var castList: ArrayList<Cast>? = null
    private var hour = 0
    private var min: Int = 0
    private var mMovie: Movie? = null
    private var inWishList = false
    private val videos: ArrayList<Video>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        castList = ArrayList()
        queryMap = HashMap()
        val args = MoviesDetailsArgs.fromBundle(requireArguments())
        movieId = args.movieId
        observeData()
        queryMap!!["api_key"] = Constants.API_KEY
        queryMap!!["page"] = "1"
        queryMap!!["append_to_response"] = "videos"
        viewModel!!.getMovieDetails(movieId!!, queryMap!!)
        viewModel!!.getCast(movieId!!, queryMap!!)
        binding!!.castRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        adapter = CastAdapter(requireContext(), castList!!)
        binding!!.castRecyclerView.adapter = adapter
        binding!!.moviePoster.clipToOutline = true
        binding!!.addToWishList.setOnClickListener {
            if (inWishList) {
                viewModel!!.deleteMovie(movieId!!)
                binding!!.addToWishList.setImageResource(R.drawable.ic_playlist_add_black_24dp)
                Toast.makeText(context, "Removed from WishList.", Toast.LENGTH_SHORT).show()
            } else {
                val movie = WishListMovie(
                    mMovie?.id!!,
                    mMovie?.poster_path!!,
                    mMovie?.overview!!,
                    mMovie?.release_date!!,
                    mMovie?.title!!,
                    mMovie?.backdrop_path!!,
                    mMovie?.vote_count!!,
                    mMovie?.runtime!!
                )
                viewModel!!.insertMovie(movie)
                binding!!.addToWishList.setImageResource(R.drawable.ic_playlist_add_check_black_24dp)
                Toast.makeText(context, "Added to WishList.", Toast.LENGTH_SHORT).show()
            }
        }
        binding!!.playTrailer.setOnClickListener {
            if (videoId != null) {
                val dialog = VideoDialog(videoId)
                dialog.show(parentFragmentManager, "Video Dialog")
            } else Toast.makeText(context, "Sorry trailer not found!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isMovieInWishList(movieId: Int) {
        inWishList = if (viewModel!!.getWishListMovie(movieId) != null) {
            binding!!.addToWishList.setImageResource(R.drawable.ic_playlist_add_check_black_24dp)
            true
        } else {
            binding!!.addToWishList.setImageResource(R.drawable.ic_playlist_add_black_24dp)
            false
        }
        binding!!.addToWishList.visibility = View.VISIBLE
    }

    private fun observeData() {
        viewModel!!.getMovie().observe(viewLifecycleOwner,
            { movie ->
                mMovie = movie
                Glide.with(requireContext()).load(Constants.ImageBaseURL + movie.poster_path)
                    .centerCrop()
                    .into(binding!!.moviePoster)
                binding!!.movieName.setText(movie.title)
                hour = movie.runtime!! / 60
                min = movie.runtime!! % 60
                binding!!.movieRuntime.text = hour.toString() + "h " + min + "m"
                binding!!.moviePlot.setText(movie.overview)
                temp = ""
                for (i in 0 until movie.genres!!.size) {
                    if (i == movie.genres.size - 1) temp += movie.genres.get(i)
                        .name else temp += movie.genres.get(i).name
                        .toString() + " | "
                }
                binding!!.movieGenre.text = temp
                binding!!.playTrailer.visibility = View.VISIBLE
                binding!!.movieCastText.visibility = View.VISIBLE
                binding!!.moviePlotText.visibility = View.VISIBLE
                isMovieInWishList(movieId!!)
                val array: JsonArray = movie.videos!!.getAsJsonArray("results")
                videoId = array[0].asJsonObject["key"].asString
            })
        viewModel!!.getMovieCastList().observe(viewLifecycleOwner, { actors ->
            Log.e(
                TAG,
                "onChanged: " + actors.size
            )
            adapter?.setCastList(actors)
            adapter?.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}