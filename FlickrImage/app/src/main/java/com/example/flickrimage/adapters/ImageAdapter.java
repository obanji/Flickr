package com.example.flickrimage.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickrimage.R;
import com.example.flickrimage.model.Image;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    List<Image> images;
   public ImageAdapter(List<Image> images) {
       this.images = images;
   }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;

       public ImageViewHolder(View view) {
           super(view);
           imageView = view.findViewById(R.id.imageView);
       }
        public void loadImage(Image image) {
            Picasso.get().load(image.url)
                    .resize(400, 400)
                    .centerCrop()
                    .into(imageView);
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.image, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, final int position) {
       viewHolder.loadImage(this.images.get(position));
    }

    @Override
    public int getItemCount() {
       return this.images.size();
    }
}
