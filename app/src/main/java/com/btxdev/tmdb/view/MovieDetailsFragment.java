package com.btxdev.tmdb.view;

import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.btxdev.tmdb.R;
import com.btxdev.tmdb.api.Genre;
import com.btxdev.tmdb.api.Movie;
import com.btxdev.tmdb.presenter.MovieDetailsPresenter;
import com.btxdev.tmdb.presenter.MovieDetailsPresenterImpl;
import com.btxdev.tmdb.tools.Util;
import com.btxdev.tmdb.viewmodel.MovieDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailsView {


    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMovieDetails.
     */
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
      /*  Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    private Toolbar toolbar;
    private MovieDetailsViewModel viewModel;
    private ImageView imgBackdrop;
    private ScrollView scrollViewBackdrop;
    private TextView txtSummary, txtReleaseDate, txtPopularityScore, txtGenre;
    private MovieDetailsPresenter presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = view.findViewById(R.id.toolbarMovieDetails);
        imgBackdrop = view.findViewById(R.id.imgBackdrop);
        scrollViewBackdrop = view.findViewById(R.id.scrollViewBackdrop);
        txtSummary = view.findViewById(R.id.txtSummary);
        txtReleaseDate = view.findViewById(R.id.txtReleaseDate);
        txtPopularityScore = view.findViewById(R.id.txtPopularity);
        txtGenre = view.findViewById(R.id.txtGenre);

        presenter = new MovieDetailsPresenterImpl(this);

        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        Point screenSizePx = Util.getScreenSizePx(getContext());
        scrollViewBackdrop.getLayoutParams().height = (int) (screenSizePx.y/2.8);

        Bundle args = getArguments();

        if(viewModel.getMovie()!=null){
            presenter.getMovieDetails(viewModel.getMovie());
            if(viewModel.getGenres()!=null){
                showMovieGenreDetails(viewModel.getGenres());
            }
        }else{
            if(args!=null){
                Movie movie = getArguments().getParcelable(getString(R.string.arg_movie));
                viewModel.setMovie(movie);
                presenter.getMovieDetails(movie);
                presenter.getGenreDetails(movie);
                setArguments(null);
            }
        }
    }

    @Override
    public void showMovieDetails(String title, String releaseDate, String popularityScore, String summary, String pictureUrl) {
        toolbar.setTitle(title);

        if(!summary.isEmpty()){
            txtSummary.setText(summary);
        }

        if(!releaseDate.isEmpty()){
            txtReleaseDate.setText(releaseDate);
        }

        if(!popularityScore.isEmpty()){
            txtPopularityScore.setText(popularityScore);
        }
        
        txtGenre.setText("...");

        Picasso.with(getContext()).load(pictureUrl)
                .placeholder(R.drawable.movie_backdrop_placeholder).into(imgBackdrop);
    }

    @Override
    public void showMovieGenreDetails(List<Genre> genres) {
        viewModel.setGenres(genres);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<genres.size();i++){
            if(i>0){
                stringBuilder.append(", ");
            }
            stringBuilder.append(genres.get(i).name);
        }
        txtGenre.setText(stringBuilder.toString());
    }

    @Override
    public void showMovieGenreDetailsNotAvailable() {
        txtGenre.setText(R.string.no_data_available);
    }
}