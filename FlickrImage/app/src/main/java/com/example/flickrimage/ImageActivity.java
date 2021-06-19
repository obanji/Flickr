package com.example.flickrimage;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickrimage.adapters.ImageAdapter;
import com.example.flickrimage.databinding.ActivityImagesBinding;
import com.example.flickrimage.model.Image;
import com.example.flickrimage.viewmodels.ImageViewModel;

import java.util.List;

public class ImageActivity extends AppCompatActivity{
    private RecyclerView imageRecycleView;
    private ImageAdapter imageAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        ImageViewModel imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        imageRecycleView = findViewById(R.id.imageRecyclerView);


        imageViewModel.getImages().observe(this, images -> {
            imageAdapter = new ImageAdapter(images);
            imageRecycleView.setAdapter(imageAdapter);
        });
        imageRecycleView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
