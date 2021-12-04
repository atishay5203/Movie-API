package com.example.moviedatabase.MovieList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedatabase.*
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(movies_list)
        {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = MovieAdapter {
                findNavController().navigate(
                        MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                                it
                        )
                )
            }
            viewModel.movieList.observe(viewLifecycleOwner, Observer {


                (movies_list.adapter as MovieAdapter).submitList(it)
                if (it.isEmpty()) {

                    viewModel.fetchFromNetwork()
                    (movies_list.adapter as MovieAdapter).submitList(it)
                }
            })
            viewModel.status.observe(viewLifecycleOwner,
                    {
                        if (it.status == Status.Loading) {
                            progressBar.isVisible = true
                            error_msg.isVisible = false
                        } else if (it.status == Status.Success) {
                            progressBar.isVisible = false
                            error_msg.isVisible = false
                        } else {
                            progressBar.isVisible = false
                            error_msg.isVisible = true
                            errormsg(it.errorType, it.message)
                        }
                        swipe_refresh.isRefreshing=false
                    }


            )

        }
        swipe_refresh.isRefreshing = false
        swipe_refresh.setOnRefreshListener {
            viewModel.refresh()

        }
    }

    fun errormsg(errorType: ErrorType?, msg: String?) = when (errorType) {
        ErrorType.DATA_OFF ->
            error_msg.text = getString(R.string.msg1)
        ErrorType.NETWORK_ERROR -> {
            error_msg.text = getString(R.string.msg2)
        }

        else -> {
            error_msg.text = "Unknown error :${msg} occurred."
        }
    }
}
