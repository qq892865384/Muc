package com.twc.afview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.AbsListView;

public class AFRecyclerView extends RecyclerView {
    private Context context;
    private boolean refreshFlag = false;
    private int lastVisibleItem = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private AFRListener afrListener;
    private boolean isLastPage = false;
    private String scrollertitle;


    private interface getRecyclerState {
        void getState();
    }

    public void setLoadNextPageListener(AFRListener loadNextPageListener) {
        this.afrListener = loadNextPageListener;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public interface AFRListener {
        void isRefresh(boolean falg);

        void loadData();
    }

    public AFRecyclerView(Context context) {
        this(context, null);
    }

    private LinearLayoutManager mg;

    public AFRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AFRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initF();
    }

    private void initF() {

        this.setHasFixedSize(true);
        this.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mg == null) {
                    if (getLayoutManager() instanceof LinearLayoutManager) {
                        mg = (LinearLayoutManager) getLayoutManager();
                    } else if (getLayoutManager() instanceof GridLayoutManager) {
                        mg = (GridLayoutManager) getLayoutManager();
                    }
                }
                totalItemCount = mg.getItemCount();
                lastVisibleItem = mg.findLastVisibleItemPosition() + 1;
                visibleItemCount = mg.getChildCount();
                if (mg.findFirstCompletelyVisibleItemPosition() == 0) {
                    if (afrListener != null) {
                        afrListener.isRefresh(true);
                    }
                } else {
                    if (afrListener != null) {
                        afrListener.isRefresh(false);
                    }
                }
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (mg.findLastVisibleItemPosition() == totalItemCount - 1) {
                        if (afrListener != null)
                            if (!isLastPage)
                                afrListener.loadData();
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    refreshFlag = true;
                } else {
                    refreshFlag = false;
                }
            }
        });

    }
}
