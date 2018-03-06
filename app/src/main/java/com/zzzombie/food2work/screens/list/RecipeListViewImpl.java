package com.zzzombie.food2work.screens.list;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzzombie.food2work.R;
import com.zzzombie.food2work.api.ImageLoader;
import com.zzzombie.food2work.entities.RecipeShort;

import java.util.List;

public class RecipeListViewImpl implements RecipeListView {

    private RecipeListViewListener listener;
    private List<RecipeShort> dataList;
    private boolean progressVisible = true;

    private Snackbar snackBar;
    private final ImageLoader imageLoader;
    private final CoordinatorLayout rootView;
    private final EditText searchQuery;
    private final RecyclerView recyclerView;
    private final RecyclerView.Adapter<SimpleViewHolder> adapter;
    private final LayoutInflater inflater;
    private final RecyclerView.ItemDecoration decoration;

    public RecipeListViewImpl(@NonNull View rootView) {
        this.rootView = (CoordinatorLayout) rootView;
        searchQuery = rootView.findViewById(R.id.edit_query);
        this.inflater = LayoutInflater.from(rootView.getContext());
        this.recyclerView = rootView.findViewById(R.id.recycler);
        this.adapter = new RecipeAdapter();
        this.decoration = getItemDecoration();
        this.imageLoader = new ImageLoader(new Handler());
        setUpRecyclerView();
        setUpSearchView();
    }

    private void setUpSearchView() {
        searchQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (listener != null) {
                    listener.onSearchQueryChanged(v.getText().toString());
                }
                return true;
            }
            return false;
        });
    }

    private void setUpRecyclerView() {
        adapter.setHasStableIds(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(
                recyclerView.getContext(),
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (listener != null) {
                    listener.scrolledTo(dy);
                }
            }
        });
    }

    private RecyclerView.ItemDecoration getItemDecoration() {
        DividerItemDecoration decoration = new DividerItemDecoration(
                recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        decoration.setDrawable(
                ContextCompat.getDrawable(recyclerView.getContext(), R.drawable.divider_shape)
        );
        return decoration;
    }

    @Override
    public void setProgressVisible(boolean progressVisible) {
        if (this.progressVisible != progressVisible) {
            this.progressVisible = progressVisible;
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setSearchQuery(String query) {
        searchQuery.setText(query);
    }

    @Override
    public void setListener(RecipeListViewListener listener) {
        this.listener = listener;
    }

    @Override
    public void refreshList(List<RecipeShort> data) {
        if (data == null || data.size() == 0) {
            recyclerView.removeItemDecoration(decoration);
        } else if (recyclerView.getItemDecorationAt(0) == null) {
            recyclerView.addItemDecoration(decoration);
        }
        dataList = data;
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        snackBar = Snackbar.make(rootView,
                rootView.getResources().getString(R.string.network_error),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(
                        rootView.getResources().getString(R.string.retry), v -> {
                            if (listener != null) {
                                listener.onRetryClick();
                            }
                        });
        snackBar.show();
    }

    @Override
    public void hideError() {
        if (snackBar != null) {
            snackBar.dismiss();
        }
    }

    @Override
    public void scrollTo(int scrollPosition) {
        recyclerView.scrollTo(0, scrollPosition);
    }

    private class RecipeAdapter extends RecyclerView.Adapter<SimpleViewHolder> {

        private final int PROGRESS = 1;
        private final int ITEM = 2;

        @Override
        public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == PROGRESS) {
                View view = inflater.inflate(R.layout.item_progress, parent, false);
                return new SimpleViewHolder(view);
            } else {
                View view = inflater.inflate(R.layout.item_recipe, parent, false);
                return new RecipeViewHolder(view);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return (getItemCount() == position + 1) ? PROGRESS : ITEM;
        }

        @Override
        public void onBindViewHolder(SimpleViewHolder holder, int position) {
            if (getItemViewType(position) == PROGRESS && listener != null) {
                holder.itemView.setVisibility(progressVisible ? View.VISIBLE : View.INVISIBLE);
                listener.onBottomScrolled();
            } else {
                RecipeShort recipe = dataList.get(position);
                ((RecipeViewHolder) holder).titleView.setText(recipe.getTitle());
                ((RecipeViewHolder) holder).iconView.setImageResource(R.drawable.ic_image_template);
                imageLoader.loadImage(((RecipeViewHolder) holder).iconView, recipe.getImageUrl());
            }
        }

        @Override
        public int getItemCount() {
            return dataList == null ? 1 : dataList.size() + 1;
        }

        @Override
        public long getItemId(int position) {
            if (getItemViewType(position) == PROGRESS) {
                return -1;
            } else {
                String stringId = dataList.get(position).getId();
                try {
                    return Long.parseLong(stringId);
                } catch (NumberFormatException ex) {
                    return stringId.hashCode();
                }
            }
        }
    }

    private class SimpleViewHolder extends RecyclerView.ViewHolder {

        SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class RecipeViewHolder extends SimpleViewHolder {
        final TextView titleView;
        final ImageView iconView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onItemClicked(dataList.get(getAdapterPosition()));
                }
            });
            titleView = itemView.findViewById(R.id.txt_title);
            iconView = itemView.findViewById(R.id.img_icon);
        }
    }
}
