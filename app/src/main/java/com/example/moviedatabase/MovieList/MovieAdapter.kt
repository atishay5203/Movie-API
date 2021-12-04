package com.example.moviedatabase.MovieList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.database.Movie
import com.example.moviedatabase.R
import com.example.moviedatabase.ServiceAndStatus.TmdbService
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.*

class MovieAdapter(private val listner: (Long) -> Unit) :
        ListAdapter<Movie, MovieAdapter.MovieViewHolder>(diffUtilCallBack2()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }


    inner class MovieViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView),
            LayoutContainer {
        init {
            // if view_all is clicked , user will be directed to the month details page giving details of the selected month
            itemView.setOnClickListener {
                listner.invoke(getItem(adapterPosition).id)
            }
        }


        fun bindView(movie: Movie) {
            with(movie)
            {
                movie_title.text = movie.title
                Glide.with(movie_poster).load(TmdbService.POSTER_URL + this.poster).error(R.drawable.empty_movie).into(movie_poster)

            }

        }
    }


    class diffUtilCallBack2 : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }
}