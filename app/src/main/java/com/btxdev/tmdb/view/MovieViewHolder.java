package com.btxdev.tmdb.view;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.btxdev.tmdb.R;
import com.btxdev.tmdb.tools.GenericViewHolder;

public class MovieViewHolder  extends GenericViewHolder {

    ImageView imgMovie;
    CardView cardView;
    LinearLayout frame;
    Space space;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        imgMovie = itemView.findViewById(R.id.imgMovie);
        cardView = itemView.findViewById(R.id.cardView);
        frame = itemView.findViewById(R.id.frame);
        space = itemView.findViewById(R.id.space);
    }
}
