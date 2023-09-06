package com.example.maps.data;

import android.net.Uri;

public class UriExtension {
    public static Uri parseStringToUri(String urlString) {
        return Uri.parse(urlString);
    }
}
