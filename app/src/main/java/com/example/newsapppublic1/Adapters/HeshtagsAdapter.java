package com.example.newsapppublic1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.ItemHeshtagsBinding;

public class HeshtagsAdapter extends RecyclerView.Adapter<HeshtagsAdapter.HeshtagViewHolder> {
    @NonNull
    @Override
    public HeshtagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHeshtagsBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_heshtags, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_heshtags, parent, false);
        return new HeshtagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeshtagViewHolder holder, int position) {
        holder.binding.tvHeshtag.setText("#aaaaaa1");
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public class HeshtagViewHolder extends RecyclerView.ViewHolder {
        ItemHeshtagsBinding binding;

        public HeshtagViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemHeshtagsBinding.bind(itemView);
        }
    }
}
