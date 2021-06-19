package com.example.flickrimage.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.flickrimage.model.Image;
import com.example.flickrimage.model.Photo;
import com.example.flickrimage.model.PhotoApiResponse;
import com.example.flickrimage.utilities.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImageRepository {
    private FlickrService flickrService;
    private Retrofit retrofit;
    private PhotoApiResponse apiResponse;

    public ImageRepository() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        this.flickrService = retrofit.create(FlickrService.class);
    }


    public void searchImage(String searchTerm) {
        Call<PhotoApiResponse> getRequestAsync = flickrService.getPhotos(searchTerm);
        getRequestAsync.enqueue(new Callback<PhotoApiResponse>() {
            @Override
            public void onResponse(Call<PhotoApiResponse> call, Response<PhotoApiResponse> response) {
                if (response.isSuccessful()){
                    setApiResponse(response);
                }
            }
            @Override
            public void onFailure(Call<PhotoApiResponse> call, Throwable t) {
            }
        });
    }

    public void setApiResponse(Response<PhotoApiResponse> res) {
        this.apiResponse = res.body();
    }

    public PhotoApiResponse getApiResponse() {
        return this.apiResponse;
    }


    public MutableLiveData <List<Image>> search(String searchTerm) {
        MutableLiveData<List<Image>> apiResponse = new MutableLiveData<>();
        Call<PhotoApiResponse> getRequestAsync = flickrService.getPhotos(searchTerm);
        getRequestAsync.enqueue(new Callback<PhotoApiResponse>() {
            @Override
            public void onResponse(Call<PhotoApiResponse> call, Response<PhotoApiResponse> response) {
                if (response.isSuccessful()) {
                    PhotoApiResponse body = response.body();
                    List<Image> imageList = new ArrayList<>();

                    for (Photo p: body.photos.photo) {
                        Image image = new Image();
                        image.id = p.id;
                        image.url = replaceUrlParameter(Constants.FLICKR_IMAGE_URL, p);
                        image.title = p.title;
                        imageList.add(image);
                    }
                    apiResponse.postValue(imageList);
                }
            }
            @Override
            public void onFailure(Call<PhotoApiResponse> call, Throwable t) {
                apiResponse.postValue(null);
            }
        });
        return apiResponse;
    }

    private String replaceUrlParameter(String url, Photo p) {
        HashMap<String, String> map = new HashMap<String, String>() {{
            put("p.farm", p.farm);
            put("p.server", p.server);
            put("p.id", p.id);
            put("p.secret", p.secret);
        }};

        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            url = url.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return url;
    }

}
