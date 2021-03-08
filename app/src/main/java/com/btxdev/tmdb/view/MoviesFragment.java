package com.btxdev.tmdb.view;

import android.graphics.Point;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.btxdev.tmdb.R;
import com.btxdev.tmdb.api.Movie;
import com.btxdev.tmdb.presenter.MoviesPresenter;
import com.btxdev.tmdb.presenter.MoviesPresenterImpl;
import com.btxdev.tmdb.tools.EndlessGridScrollListener;
import com.btxdev.tmdb.tools.GenericAdapter;
import com.btxdev.tmdb.tools.Util;
import com.btxdev.tmdb.viewmodel.MoviesViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment implements MoviesView{


    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopularMoviesFragment.
     */
    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
      /*  Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*   if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }


    private SearchView searchView;
    private MoviesPresenter moviesPresenter;
    private GenericAdapter<MovieViewHolder> adapterMovie;
    private OnBackPressedCallback searchOnBackPressedCallback;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MoviesViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MoviesViewModel.class);

        RecyclerView recMovies = view.findViewById(R.id.recMovies);
        toolbar = view.findViewById(R.id.toolbarMovies);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        recMovies.hasFixedSize();
        recMovies.setNestedScrollingEnabled(false);

        moviesPresenter = new MoviesPresenterImpl(this);

        Point screenSizePx = Util.getScreenSizePx(getContext());


        int gridSpace = screenSizePx.x/36;
        int gridSpan = 3;


        adapterMovie = new GenericAdapter<>(getContext(), R.layout.item_view_movie, new GenericAdapter.InstanceCallback<MovieViewHolder>() {
            @Override
            public MovieViewHolder onCreateViewHolder(View view) {
                MovieViewHolder movieViewHolder = new MovieViewHolder(view);
                ViewGroup.LayoutParams layoutParamsFrame = movieViewHolder.frame.getLayoutParams();
                ViewGroup.LayoutParams layoutParamsSpace = movieViewHolder.space.getLayoutParams();
                layoutParamsFrame.width = (screenSizePx.x/gridSpan)-gridSpace;
                layoutParamsFrame.height = (int) (layoutParamsFrame.width*1.5);
                layoutParamsSpace.height = gridSpace;
                movieViewHolder.space.setLayoutParams(layoutParamsSpace);
                movieViewHolder.frame.setLayoutParams(layoutParamsFrame);
                return movieViewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull MovieViewHolder viewHolder, int i) {
                Movie movie = viewModel.getMoviesList().get(i);
                Picasso.with(getContext()).load("https://image.tmdb.org/t/p/w200"+movie.posterPath)
                        .placeholder(R.drawable.movie_poster_placeholder).into(viewHolder.imgMovie);

                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(getString(R.string.arg_movie), movie);
                        Navigation.findNavController(view).navigate(R.id.action_moviesFragment_to_movieDetailsFragment, bundle);
                    }
                });

            }

            @Override
            public int getItemCount() {
                if(viewModel.getMoviesList()!=null){
                    return viewModel.getMoviesList().size();
                }else{
                    return 0;
                }


            }
        });

        adapterMovie.setOnGrid(recMovies,gridSpan);

        EndlessGridScrollListener endlessGridScrollListener = new EndlessGridScrollListener() {
            @Override
            public void onLoadMore(int totalItemsCount, RecyclerView view) {
                if (searchView.isIconified()) {
                    viewModel.setCurrentPage(viewModel.getNextPage());
                    viewModel.setNextPage(viewModel.getNextPage()+1);
                    moviesPresenter.getMovies(viewModel.getCurrentPage());
                } else {
                    viewModel.setCurrentPageSearch(viewModel.getNextPageSearch());
                    viewModel.setNextPageSearch(viewModel.getNextPageSearch()+1);
                    moviesPresenter.searchMovies(viewModel.getCurrentSearch(), viewModel.getCurrentPageSearch());
                }
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(viewModel.getMoviesList()!=null){
                    viewModel.getMoviesList().clear();
                }
                adapterMovie.notifyDataSetChanged();
                if (searchView.isIconified()) {
                    viewModel.setCurrentPage(1);
                    viewModel.setNextPage(2);
                    moviesPresenter.getMovies(viewModel.getCurrentPage());
                } else {
                    viewModel.setCurrentPageSearch(1);
                    viewModel.setNextPageSearch(2);
                    moviesPresenter.searchMovies(viewModel.getCurrentSearch(), viewModel.getCurrentPageSearch());
                }
            }
        });

        recMovies.addOnScrollListener(endlessGridScrollListener);

        searchOnBackPressedCallback = new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                if(!searchView.isIconified()){
                    if(searchView.getQuery().length()>0){
                        searchView.setQuery("",false);
                    }else{
                        searchView.setIconified(true);
                    }
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(searchOnBackPressedCallback);

        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);

        toolbar.inflateMenu(R.menu.movies_menu);

        MenuItem actionSearch = toolbar.getMenu().findItem(R.id.actionSearch);

        searchView = (SearchView) actionSearch.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchOnBackPressedCallback.setEnabled(true);
                viewModel.setMoviesTemp(viewModel.getMoviesList());
                viewModel.setMoviesList(new ArrayList<>());
                adapterMovie.notifyDataSetChanged();
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                viewModel.setMoviesList(viewModel.getMoviesTemp());
                viewModel.setMoviesTemp(null);
                adapterMovie.notifyDataSetChanged();
                searchOnBackPressedCallback.setEnabled(false);
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.setCurrentPageSearch(1);
                viewModel.setNextPageSearch(2);
                viewModel.setCurrentSearch(query);
                viewModel.getMoviesList().clear();
                moviesPresenter.searchMovies(query, viewModel.getCurrentPageSearch());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        if(viewModel.getMoviesList()==null) {
            moviesPresenter.getMovies(viewModel.getCurrentPage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!searchView.isIconified()){
            searchOnBackPressedCallback.setEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        searchOnBackPressedCallback.setEnabled(false);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        if(viewModel.getMoviesList()==null){
            viewModel.setMoviesList(new ArrayList<>());
        }
        int previousSize = viewModel.getMoviesList().size();
        viewModel.getMoviesList().addAll(movies);
        adapterMovie.notifyItemRangeInserted(previousSize,movies.size()-1);
    }

    @Override
    public void showError(Exception e) {
        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(),message ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showLoadingFinish() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSearchResults(List<Movie> movies) {
        if(viewModel.getMoviesList()==null){
            viewModel.setMoviesList(new ArrayList<>());
        }
        int previousSize = viewModel.getMoviesList().size();
        viewModel.getMoviesList().addAll(movies);
        adapterMovie.notifyItemRangeInserted(previousSize,movies.size()-1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }


}