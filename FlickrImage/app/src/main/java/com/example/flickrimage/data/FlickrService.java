package com.example.flickrimage.data;

import com.example.flickrimage.model.PhotoApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface FlickrService {

    @GET("?method=flickr.photos.search&api_key=2bfe9d005495203edd8d7932725b933c&format=json&nojsoncallback=1&safe_search=1")
    Call<PhotoApiResponse> getPhotos(@Query("text") String searchTerm);
}
