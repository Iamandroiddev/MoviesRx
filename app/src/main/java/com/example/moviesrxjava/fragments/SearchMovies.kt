package com.example.moviesrxjava.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.moviesrxjava.HomeViewModel
import com.example.moviesrxjava.adapters.CategoryMoviesAdapter
import com.example.moviesrxjava.databinding.FragmentSearchMoviesBinding
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchMovies : Fragment() {

    private  var binding: FragmentSearchMoviesBinding?=null
    private lateinit var viewModel: HomeViewModel
    private lateinit var queryMap: HashMap<String, String>
    private var adapter: CategoryMoviesAdapter?=null
    private val moviesList: ArrayList<Movie>?=null
    private var queryText = ""


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = SearchMoviesArgs.fromBundle(requireArguments())
        queryText = args.query
        binding?.searchKeyword?.setText(queryText)

        queryMap = HashMap()
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        queryMap["api_key"] = Constants.API_KEY
        queryMap["query"] = queryText

        initRecyclerView()
        observeData()
        viewModel.getQueriedMovies(queryMap)

        binding?.searchMovie?.setOnClickListener {
            queryText = binding?.searchKeyword?.text.toString().trim().toLowerCase(Locale.getDefault())
            queryMap.clear()
            queryMap["api_key"] = Constants.API_KEY
            queryMap["query"] = queryText
            viewModel.getQueriedMovies(queryMap)
        }
        binding?.searchKeyword?.setOnEditorActionListener { _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                queryText = binding?.searchKeyword?.text.toString().trim().toLowerCase(Locale.getDefault())
                queryMap.clear()
                queryMap["api_key"] = Constants.API_KEY
                queryMap["query"] = queryText
                viewModel.getQueriedMovies(queryMap)
            }
            false
        }
    }
    private fun observeData() {
        viewModel.getQueriesMovies().observe(viewLifecycleOwner, { movies -> adapter!!.setList(movies) })
    }

    private fun initRecyclerView() {
        binding!!.searchMoviesRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapter = CategoryMoviesAdapter(requireContext(), moviesList)
        binding!!.searchMoviesRecyclerView.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}