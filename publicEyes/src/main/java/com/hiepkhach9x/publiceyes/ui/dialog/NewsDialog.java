package com.hiepkhach9x.publiceyes.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hiepkhach9x.base.AppLog;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.adapter.NewsAdapter;
import com.hiepkhach9x.publiceyes.entities.News;

import java.util.ArrayList;

import co.utilities.Utils;

/**
 * Created by HungHN on 4/5/2016.
 */
public class NewsDialog extends Dialog {

    private Context mContext;
    private ListView mListView;
    private NewsAdapter mNewAdapter;
    private Button mDone;

    private ArrayList<News> mNewsList;

    public NewsDialog(Context context, ArrayList<News> newsList) {
        super(context, R.style.news_dialog);
        this.mContext = context;
        this.mNewsList = newsList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_fragment_news);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && AppLog.isEnabled) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        int[] screenSize = Utils.getScreenSize(mContext);
        int height = (int) (screenSize[1] * 0.95); //set height to 90% of total
        int width = (int) (screenSize[0] * 0.96); //set width to 90% of total
        getWindow().setLayout(width, height); //set layout


        mListView = (ListView) findViewById(R.id.list_news);
        mDone = (Button) findViewById(R.id.news_done);

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDialog.this.dismiss();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Tap anywhere to close dialog.
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);
        if (isShowing() && event.getAction() == MotionEvent.ACTION_DOWN && !dialogBounds.contains((int) event.getX(),
                (int) event.getY())) {
            dismiss();
            // stop dialog closing
        } else {
            AppLog.d("HungHN", "Touch: " + event.getX() + " ; " + event.getY());
        }
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mNewAdapter == null) {
            if (mNewsList == null) {
                mNewsList = new ArrayList<>();
            }
            mNewAdapter = new NewsAdapter(mContext, mNewsList);
        }
        mListView.setAdapter(mNewAdapter);
    }
}
