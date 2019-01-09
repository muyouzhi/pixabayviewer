package com.muyouzhi.pixabayrxjava.network;

import android.net.Uri;
import android.util.Log;

import com.muyouzhi.pixabayrxjava.ImageDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PixabayNetworkUtils {
    public static String BASE_URL = "https://pixabay.com/";
    public static String KEY = "11185459-cd4566f6bd25795758f7ec467";
    public static String QUERY_KEY = "q";

    public static String buildUrl(String query) {
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("key", KEY)
                .appendQueryParameter(QUERY_KEY, query).build();
        return uri.toString();
    }

    public static List<ImageDetails> createImageDetailsFromJson(String jsonString) {
        List<ImageDetails> result = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray hitsArray = (JSONArray) jsonObject.get("hits");
            for (int i = 0; i < hitsArray.length(); i++) {
                JSONObject imageDetailsObject = (JSONObject) hitsArray.get(i);
                String user = imageDetailsObject.getString("user");
                String previewUrl = imageDetailsObject.getString("previewURL");
                String imageUrl = imageDetailsObject.getString("webformatURL");
                ImageDetails imageDetails = new ImageDetails(previewUrl, user, imageUrl);
                result.add(imageDetails);
            }
        } catch (JSONException e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return result;
    }
}
