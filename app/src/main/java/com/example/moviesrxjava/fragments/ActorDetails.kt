package com.example.moviesrxjava.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesrxjava.viewmodels.HomeViewModel
import com.example.moviesrxjava.adapters.KnownForMoviesAdapter
import com.example.moviesrxjava.databinding.FragmentActorDetailsBinding
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.utils.Constants
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ActorDetails : Fragment() {

    private val TAG = "ActorDetails"
    private var binding: FragmentActorDetailsBinding? = null
    private var viewModel: HomeViewModel? = null
    private var personID: Int? = null
    private var queries: HashMap<String, String>? = null
    private var adapter: KnownForMoviesAdapter? = null
    private var popularMovies: ArrayList<Movie>? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentActorDetailsBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val args = ActorDetailsArgs.fromBundle(requireArguments())
        personID = args.personId

        queries = HashMap()
        queries!!["api_key"] = Constants.API_KEY
        queries!!["append_to_response"] = "movie_credits"

        viewModel!!.getActorDetails(personID!!, queries!!)

        viewModel!!.getActor().observe(viewLifecycleOwner, { actor ->
            binding!!.actorName.setText(actor.name)
            binding!!.actorBirthday.setText(actor.birthday)
            binding!!.actorBio.setText(actor.biography)
            binding!!.actorPlace.setText(actor.place_of_birth)
            Glide.with(requireContext()).load(Constants.ImageBaseURL + actor.profile_path)
                    .into(binding!!.actorImage)
            binding!!.actorPopularity.setText(actor.popularity.toString() + "")
            binding!!.actorBioText.visibility = View.VISIBLE
            binding!!.knownForText.visibility = View.VISIBLE
            binding!!.popularityIcon.visibility = View.VISIBLE
            val array: JsonArray = actor.movie_credits!!.getAsJsonArray("cast")
            popularMovies = Gson().fromJson(array.toString(),
                    object : TypeToken<ArrayList<Movie?>?>() {}.type)
            initKnownFor(popularMovies!!)
        })

    }

    private fun initKnownFor(movies: ArrayList<Movie>) {
        Log.e(TAG, "initKnownFor: " + movies.size)
        binding!!.knownForRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        adapter = KnownForMoviesAdapter(context, movies)
        binding!!.knownForRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}