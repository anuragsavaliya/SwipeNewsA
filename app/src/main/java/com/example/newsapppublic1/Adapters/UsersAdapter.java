package com.example.newsapppublic1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.ItemUsersBinding;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_users, parent, false);
        return new UserViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ItemUsersBinding binding;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = ItemUsersBinding.bind(itemView);
        }
    }
}
