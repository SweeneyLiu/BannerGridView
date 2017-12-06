package com.lsw.bannergridview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liushuwei on 2017/12/6.
 */

public class BannerView extends LinearLayout {

    private Context mContext;
    private ViewPager mBanner;
    private BannerAdapter mBannerAdapter;
    private ImageView[] mIndicators;
    private Timer mTimer = new Timer();

    private int mBannerPosition = 0;
    private final int FAKE_BANNER_SIZE = 100;
    private final int DEFAULT_BANNER_SIZE = 5;
    private boolean mIsUserTouched = false;

    private int[] mImagesSrc = {
            R.mipmap.img1,
            R.mipmap.img2,
            R.mipmap.img3,
            R.mipmap.img4,
            R.mipmap.img5
    };

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (!mIsUserTouched) {
                mBannerPosition = (mBannerPosition + 1) % FAKE_BANNER_SIZE;
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startBanner();
                    }
                });
            }
        }
    };


    public BannerView(Context context) {
        super(context);
        init(context);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.banner_view, this);
        mContext = context;
        initView();
        mTimer.schedule(mTimerTask, 5000, 5000);
    }

    private void initView() {
        mIndicators = new ImageView[] {
                (ImageView)findViewById(R.id.indicator1),
                (ImageView)findViewById(R.id.indicator2),
                (ImageView)findViewById(R.id.indicator3),
                (ImageView)findViewById(R.id.indicator4),
                (ImageView)findViewById(R.id.indicator5)
        };
        mBanner = (ViewPager) findViewById(R.id.banner);
        mBannerAdapter = new BannerAdapter(mContext);
        mBanner.setAdapter(mBannerAdapter);
        mBanner.setOnPageChangeListener(mBannerAdapter);
        mBanner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN
                        || action == MotionEvent.ACTION_MOVE) {
                    mIsUserTouched = true;
                } else if (action == MotionEvent.ACTION_UP) {
                    mIsUserTouched = false;
                }
                return false;
            }
        });
    }

    private void setIndicator(int position) {
        position %= DEFAULT_BANNER_SIZE;
        for(ImageView indicator : mIndicators) {
            indicator.setImageResource(R.mipmap.indicator_unchecked);
        }
        mIndicators[position].setImageResource(R.mipmap.indicator_checked);
    }


    public void startBanner(){
        if (mBannerPosition == FAKE_BANNER_SIZE - 1) {
            mBanner.setCurrentItem(DEFAULT_BANNER_SIZE - 1, false);
        } else {
            mBanner.setCurrentItem(mBannerPosition);
        }
    }

    public void cancel() {
        mTimer.cancel();
    }

    private class BannerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        private LayoutInflater mInflater;

        public BannerAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return FAKE_BANNER_SIZE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= DEFAULT_BANNER_SIZE;
            View view = mInflater.inflate(R.layout.item, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setImageResource(mImagesSrc[position]);
            final int pos = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "click banner item :" + pos, Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            int position = mBanner.getCurrentItem();
            if (position == 0) {
                position = DEFAULT_BANNER_SIZE;
                mBanner.setCurrentItem(position, false);
            } else if (position == FAKE_BANNER_SIZE - 1) {
                position = DEFAULT_BANNER_SIZE - 1;
                mBanner.setCurrentItem(position, false);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mBannerPosition = position;
            setIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }



}
