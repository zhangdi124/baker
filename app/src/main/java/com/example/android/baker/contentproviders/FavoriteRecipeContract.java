package com.example.android.baker.contentproviders;

import android.net.Uri;

public final class FavoriteRecipeContract {
    public static final String AUTHORITY =
            "com.example.android.baker.contentproviders.favoriterecipes";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String MIME_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY;
}
