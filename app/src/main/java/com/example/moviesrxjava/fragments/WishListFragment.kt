package com.example.moviesrxjava.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviesrxjava.viewmodels.WishListViewModel
import com.example.moviesrxjava.adapters.WishListAdapter
import com.example.moviesrxjava.databinding.FragmentWishListBinding
import com.example.moviesrxjava.db.WishListMovie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishListFragment : Fragment() {
    companion object {
        private const val TAG = "WishListFragment"

    }

    private var viewModel: WishListViewModel? = null
    private var binding: FragmentWishListBinding? = null
    private var adapter: WishListAdapter? = null
    private var moviesList: ArrayList<WishListMovie>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWishListBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(WishListViewModel::class.java)

        initRecyclerView()
        observeData()

        binding!!.clearWishList.setOnClickListener {
            viewModel!!.clearWishList()
            Toast.makeText(context, "WishList cleared!", Toast.LENGTH_SHORT).show()
            moviesList?.clear()
            adapter!!.setMoviesList(moviesList)
        }
    }

    private fun observeData() {
        viewModel!!.getWishListMoviesList()!!.observe(viewLifecycleOwner, { wishListMovies ->
                if (wishListMovies!!.size == 0) {
                    binding!!.placeHolderText.setVisibility(View.VISIBLE)
                    binding!!.noItemsPlaceHolder.setVisibility(View.VISIBLE)
                } else {
                    binding!!.placeHolderText.setVisibility(View.GONE)
                    binding!!.noItemsPlaceHolder.setVisibility(View.GONE)
                    adapter?.setMoviesList(wishListMovies)
                    moviesList = wishListMovies as ArrayList<WishListMovie>?
                }
            })
    }

    private fun initRecyclerView() {
        binding!!.wishListRecyclerView.setLayoutManager(GridLayoutManager(context, 2))
        adapter = WishListAdapter(requireContext(), moviesList)
        binding!!.wishListRecyclerView.setAdapter(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}