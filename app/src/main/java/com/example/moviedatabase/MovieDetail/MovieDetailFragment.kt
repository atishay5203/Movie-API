package com.example.moviedatabase.MovieDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.moviedatabase.*
import com.example.moviedatabase.ServiceAndStatus.TmdbService
import com.example.moviedatabase.database.Movie
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import java.text.SimpleDateFormat
import java.util.*


class MovieDetailFragment : Fragment() {
    private lateinit var viewModel: MovieDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = MovieDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel.setId(id)
        viewModel.movie.observe(viewLifecycleOwner, {
            setData(it)
        })
    }

    private fun setData(movie: Movie) {
        with(movie)
        {
            detail_title.text = this.title
            movie_overview.text = this.details
            movie_release_date.text = this.releaseDate.readableFormat()
            Glide.with(detail_poster).load(TmdbService.POSTER_URL + this.poster).error(R.drawable.empty_movie).into(detail_poster)
            Glide.with(movie_backdrop).load(TmdbService.BACKDROP_URL + this.backdrop).error(R.drawable.empty_movie).into(movie_backdrop)
            vote_counts.text = this.votes.toString()
        }

    }

    fun Date.readableFormat(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(this)
    }
}