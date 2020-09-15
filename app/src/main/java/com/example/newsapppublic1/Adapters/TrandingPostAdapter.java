package com.example.newsapppublic1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.ItemTrandingpostBinding;

public class TrandingPostAdapter extends RecyclerView.Adapter<TrandingPostAdapter.TrandingPostViewHolder> {
    @NonNull
    @Override
    public TrandingPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trandingpost, parent, false);
        return new TrandingPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrandingPostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class TrandingPostViewHolder extends RecyclerView.ViewHolder {
        ItemTrandingpostBinding binding;

        public TrandingPostViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTrandingpostBinding.bind(itemView);
        }
    }
}
