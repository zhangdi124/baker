package com.example.android.baker.services;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class RestServiceBase {
    protected static OkHttpClient client;

    protected String getJSON(String path) throws IOException {
        final Request request = new Request.Builder()
                .url(path)
                .build();

        final Response response = getClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Response notes unsuccessful request to " + path);
        }

        return response.body().string();
    }

    protected static OkHttpClient getClient() {
        if (client == null)
            client = new OkHttpClient();

        return client;
    }
}
