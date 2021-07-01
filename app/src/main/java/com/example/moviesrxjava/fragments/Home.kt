package com.example.moviesrxjava.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesrxjava.adapters.HomeAdapter
import com.example.moviesrxjava.adapters.ViewPagerAdapter
import com.example.moviesrxjava.databinding.FragmentHomeBinding
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.utils.Constants
import com.example.moviesrxjava.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class Home : Fragment() {
    private val TAG = "Home"
    private var viewModel: HomeViewModel? = null
    private var binding: FragmentHomeBinding? = null
    private var currentMovies: ArrayList<Movie> = ArrayList()
    private var popularMovies: ArrayList<Movie> = ArrayList()
    private var topRatedMovies: ArrayList<Movie> = ArrayList()
    private var upcomingMovies: ArrayList<Movie> = ArrayList()
    private var upcomingAdapter: HomeAdapter? = null
    private var popularAdapter: HomeAdapter? = null
    private var topRatedAdapter: HomeAdapter? = null
    private var currentlyShowingAdapter: ViewPagerAdapter? = null

    private val map = HashMap<String, String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        map["api_key"] = Constants.API_KEY
        map["page"] = "1"
        observeData()
        getMoviesList()
        setUpRecyclerViewsAndViewPager()
        setUpOnclick()

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        observeData()
        binding!!.progressBar.visibility = View.VISIBLE
        if (Constants.isNetworkAvailable(requireContext())) {
            getMoviesList()
            binding!!.progressBar.visibility = View.GONE
        } else {
            observeData()
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeData() {
        viewModel?.currentlyShowingList?.observe(
            viewLifecycleOwner,
            { movies -> currentlyShowingAdapter?.setMovieListResults(movies) })
        viewModel?.getUpcomingMoviesList()?.observe(
            viewLifecycleOwner,
            { movies -> upcomingAdapter?.setMoviesList(movies) })
        viewModel?.getTopRatedMoviesList()?.observe(
            viewLifecycleOwner,
            { movies -> topRatedAdapter?.setMoviesList(movies) })
        viewModel?.getPopularMoviesList()?.observe(
            viewLifecycleOwner,
            { movies -> popularAdapter?.setMoviesList(movies) })
    }

    private fun getMoviesList() {
        viewModel!!.getCurrentlyShowingMovies(map)
        viewModel!!.getUpcomingMovies(map)
        viewModel!!.getTopRatedMovies(map)
        viewModel!!.getPopularMovies(map)
    }

    private fun setUpOnclick() {
        binding?.viewAllCurrent?.setOnClickListener({ view ->
            val action = HomeDirections.actionHomeToMovies(Constants.Current)
            //  action.setMovieCategory(Constants.Current)
            Navigation.findNavController(view).navigate(action)
        })
        binding?.viewAllTopRated?.setOnClickListener(View.OnClickListener { view ->
            val action = HomeDirections.actionHomeToMovies(Constants.TopRated)
            //action.setMovieCategory(Constants.TopRated)
            Navigation.findNavController(view).navigate(action)
        })
        binding?.viewAllPopular?.setOnClickListener(View.OnClickListener { view ->
            val action = HomeDirections.actionHomeToMovies(Constants.Popular)
            //action.setMovieCategory(Constants.Popular)
            Navigation.findNavController(view).navigate(action)
        })
        binding?.viewAllUpcoming?.setOnClickListener(View.OnClickListener { view ->
            val action = HomeDirections.actionHomeToMovies(Constants.Upcoming)
            //action.setMovieCategory(Constants.Upcoming)
            Navigation.findNavController(view).navigate(action)
        })

    }

    private fun setUpRecyclerViewsAndViewPager() {
        currentlyShowingAdapter = ViewPagerAdapter(requireContext(), currentMovies)
        binding?.currentlyShowingViewPager?.setAdapter(currentlyShowingAdapter)
        binding?.upcomingRecyclerView?.setLayoutManager(
            LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
            )
        )
        upcomingAdapter = HomeAdapter(requireContext(), upcomingMovies)
        binding?.upcomingRecyclerView?.setAdapter(upcomingAdapter)
        binding?.topRatedRecyclerView?.setLayoutManager(
            LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
            )
        )
        topRatedAdapter = HomeAdapter(requireContext(), topRatedMovies)
        binding?.topRatedRecyclerView?.setAdapter(topRatedAdapter)
        binding?.popularRecyclerView?.setLayoutManager(
            LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
            )
        )
        popularAdapter = HomeAdapter(requireContext(), popularMovies)
        binding?.popularRecyclerView?.setAdapter(popularAdapter)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}