package com.example.moviesrxjava.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesrxjava.HomeViewModel
import com.example.moviesrxjava.adapters.CategoryMoviesAdapter
import com.example.moviesrxjava.databinding.FragmentMoviesBinding
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class Movies : Fragment() {
    private var binding: FragmentMoviesBinding? = null
    private var viewModel: HomeViewModel? = null
    private var queryMap: HashMap<String, String>? = null
    private var moviesCategory = ""
    private var adapter: CategoryMoviesAdapter? = null
    private val moviesList: ArrayList<Movie>? = null



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queryMap = HashMap()
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val args = MoviesArgs.fromBundle(requireArguments())
        moviesCategory = args.movieCategory

        queryMap!!["api_key"] = Constants.API_KEY
        queryMap!!["page"] = "1"

        initRecyclerView()
        observeData()
        getMoviesList()
    }
    private fun initRecyclerView() {
        binding!!.moviesRecyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = CategoryMoviesAdapter(requireContext(), moviesList)
        binding!!.moviesRecyclerView.adapter = adapter
    }

    private fun observeData() {
        when (moviesCategory) {
            Constants.Current -> viewModel?.currentlyShowingList?.observe(viewLifecycleOwner,  { movies -> adapter!!.setList(movies) })
            Constants.Upcoming -> viewModel!!.getUpcomingMoviesList().observe(viewLifecycleOwner,  { movies -> adapter!!.setList(movies) })
            Constants.TopRated -> viewModel!!.getTopRatedMoviesList().observe(viewLifecycleOwner, { movies -> adapter!!.setList(movies) })
            Constants.Popular -> viewModel!!.getPopularMoviesList().observe(viewLifecycleOwner,  { movies -> adapter!!.setList(movies) })
        }
    }

    private fun getMoviesList() {
        when (moviesCategory) {
            Constants.Current -> viewModel!!.getCurrentlyShowingMovies(queryMap!!)
            Constants.Upcoming -> viewModel!!.getUpcomingMovies(queryMap!!)
            Constants.TopRated -> viewModel!!.getTopRatedMovies(queryMap!!)
            Constants.Popular -> viewModel!!.getPopularMovies(queryMap!!)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}