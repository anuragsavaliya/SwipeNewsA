package com.example.newsapppublic1.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapppublic1.Model.PictureFacer;
import com.example.newsapppublic1.Model.Videofacer;
import com.example.newsapppublic1.R;
import com.example.newsapppublic1.databinding.ItemImagesBinding;

import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

    private ArrayList<PictureFacer> listImages;
    private int i;
    private ArrayList<Videofacer> listVideos;

    public ImagesAdapter(ArrayList<PictureFacer> listImages) {
        this.listImages = listImages;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_images, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        PictureFacer pic = listImages.get(position);
        holder.binding.images.setImageURI(Uri.parse(pic.getPicturePath()));
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ItemImagesBinding binding;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemImagesBinding.bind(itemView);
        }
    }
}
