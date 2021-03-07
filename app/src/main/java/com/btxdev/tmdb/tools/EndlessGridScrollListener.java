package com.btxdev.tmdb.tools;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessGridScrollListener extends RecyclerView.OnScrollListener {

    public EndlessGridScrollListener(){
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if(dy > 0){ // only when scrolling up

            final int visibleThreshold = 1;
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

            int lastItem  = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            int currentTotalCount = gridLayoutManager.getItemCount();

            if(currentTotalCount <= lastItem + visibleThreshold){
                onLoadMore(currentTotalCount, recyclerView);
            }
        }
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int totalItemsCount, RecyclerView view);
}