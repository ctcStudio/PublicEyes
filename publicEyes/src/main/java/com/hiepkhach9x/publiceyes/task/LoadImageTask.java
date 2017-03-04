package com.hiepkhach9x.publiceyes.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by hungh on 3/4/2017.
 */

public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return null;
    }


    public interface LoadImageListener {
        void onStartLoad();

        void onLoadFinish(Bitmap bitmap);
    }
}
