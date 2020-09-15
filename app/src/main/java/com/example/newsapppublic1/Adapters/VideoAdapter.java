package com.example.newsapppublic1.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapppublic1.Model.Videofacer;
import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.ItemImagesBinding;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private ArrayList<Videofacer> listVideos;
    private Context contex;

    public VideoAdapter(ArrayList<Videofacer> listVideos) {

        this.listVideos = listVideos;
        Log.d("tt", "listvideosizevidadapter " + listVideos.size());
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        contex = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        Videofacer videofacer = listVideos.get(position);
        Log.d("yyyyy", "rtkk: " + videofacer.getThumbPath());
        Log.d("yyyyy", "rtkk: " + videofacer.toString());
        Log.d("yyyyy", "rtkksize: " + listVideos.size());
        try {
            holder.binding.images.setImageURI(Uri.parse(videofacer.getThumbPath()));
            Log.d("yyyyy", "onBindViewHolder: " + videofacer.getThumbPath());
        } catch (Exception o) {
            Log.d("yyyyyy", "onBindViewHolder: " + o);
            holder.binding.images.setImageDrawable(contex.getResources().getDrawable(R.drawable.ic_launcher_foreground));
        }


    }

    @Override
    public int getItemCount() {
        return listVideos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        ItemImagesBinding binding;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemImagesBinding.bind(itemView);
        }
    }
}
