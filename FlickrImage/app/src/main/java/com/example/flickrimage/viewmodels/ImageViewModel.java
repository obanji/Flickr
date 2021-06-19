package com.example.flickrimage.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flickrimage.data.ImageRepository;
import com.example.flickrimage.model.Image;

import java.util.List;

public class ImageViewModel extends ViewModel {
    private MutableLiveData<List<Image>> images;
    ImageRepository imageRepository;
    public MutableLiveData<String> searchTerm;

    public ImageViewModel() {
        imageRepository = new ImageRepository();
    }

    public LiveData<List<Image>> getImages() {

        if (images == null){
            images = new MutableLiveData<>();
            images = imageRepository.search("heidelberg");
        }
        return images;
    }


}
