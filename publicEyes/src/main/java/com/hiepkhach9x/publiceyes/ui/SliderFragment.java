package com.hiepkhach9x.publiceyes.ui;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.publiceyes.R;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by hungh on 3/3/2017.
 */

public class SliderFragment extends BaseAppFragment implements View.OnClickListener {

    @Override
    protected boolean isHasActionbar() {
        return false;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_slider;
    }

    private ViewPager mPager;
    private CirclePageIndicator pageIndicator;
    private View mSkip, mSuccess;
    private int[] images = new int[]{R.drawable.joyride02, R.drawable.joyride03, R.drawable.joyride04
            , R.drawable.joyride05, R.drawable.joyride06};

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager = (ViewPager) view.findViewById(R.id.pager);
        pageIndicator = (CirclePageIndicator) view.findViewById(R.id.page_indicator);
        mSkip = view.findViewById(R.id.skip);
        mSuccess = view.findViewById(R.id.success);

        mSkip.setVisibility(View.VISIBLE);
        mSuccess.setVisibility(View.INVISIBLE);

        mSkip.setOnClickListener(this);
        mSuccess.setOnClickListener(this);

        SliderAdapter adapter = new SliderAdapter();
        mPager.setAdapter(adapter);
        pageIndicator.setViewPager(mPager);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == images.length - 1) {
                    mSuccess.setVisibility(View.VISIBLE);
                    mSkip.setVisibility(View.INVISIBLE);
                } else {
                    mSkip.setVisibility(View.VISIBLE);
                    mSuccess.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip:
            case R.id.success:
                if (mNavigationManager != null) {
                    mNavigationManager.swapPage(new HomeFragment());
                }
                break;
        }
    }

    private class SliderAdapter extends PagerAdapter {

        LayoutInflater inflater;

        public SliderAdapter() {
            inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = inflater.inflate(R.layout.item_image, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
            imageView.setImageResource(images[position]);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }
}
