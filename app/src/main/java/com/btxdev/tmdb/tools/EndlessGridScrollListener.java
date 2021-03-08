package com.btxdev.tmdb.tools;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class EndlessGridScrollListener extends RecyclerView.OnScrollListener {

    public EndlessGridScrollListener(){
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if(dy > 0){
            final int visibleThreshold = 1;
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

            int lastItem  = gridLayoutManager.findLastCompletelyVisibleItemPosition();
            int currentTotalCount = gridLayoutManager.getItemCount();

            if(currentTotalCount <= lastItem + visibleThreshold){
                onLoadMore(currentTotalCount, recyclerView);
            }
        }
    }

    public abstract void onLoadMore(int totalItemsCount, RecyclerView view);
}