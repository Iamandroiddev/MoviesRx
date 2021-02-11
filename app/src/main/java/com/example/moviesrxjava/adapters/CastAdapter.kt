package com.example.moviesrxjava.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesrxjava.databinding.CastItemBinding
import com.example.moviesrxjava.fragments.MoviesDetailsDirections
import com.example.moviesrxjava.models.Cast
import com.example.moviesrxjava.models.Movie
import com.example.moviesrxjava.utils.Constants
import java.util.*

class CastAdapter(private val mContext: Context,private var castList: ArrayList<Cast>?) : RecyclerView.Adapter<CastAdapter.CastRecyclerViewHolder>() {
    private var binding: CastItemBinding? = null


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CastAdapter.CastRecyclerViewHolder {
        val inflater = LayoutInflater.from(mContext)
        binding = CastItemBinding.inflate(inflater, parent, false)
        return CastRecyclerViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: CastRecyclerViewHolder, position: Int) {
        holder.binding.castName.setText(castList!![position].name)
        holder.binding.castRole.setText(castList!![position].character)
        Glide.with(mContext).load(Constants.ImageBaseURL + castList!![position].profile_path)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.binding.castImage)
        holder.binding.castItemLayout.setOnClickListener(View.OnClickListener { view ->
            val action = MoviesDetailsDirections.actionMovieDetailsToActorDetails(
                castList!![position].id!!
            )
            Navigation.findNavController(view).navigate(action)
        })
    }

    override fun getItemCount(): Int {
        return castList?.size ?: 0
    }

    inner class CastRecyclerViewHolder(binding: CastItemBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        val binding: CastItemBinding

        init {
            this.binding = binding
        }
    }

    fun setCastList(castList: ArrayList<Cast>?) {
        this.castList = castList
        notifyDataSetChanged()
    }
}