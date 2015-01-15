package fr.utt.if26.resto.Adapters;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DecodingType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.FailReason;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

import fr.utt.if26.resto.R;

/**
 * Created by soedjede on 15/01/15 for Resto
 */
public class ImagePagerAdapter extends PagerAdapter {

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private String[] images;
    private LayoutInflater inflater;

    public ImagePagerAdapter(String[] images, LayoutInflater inflater) {
        this.images = images;
        this.inflater = inflater;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public void finishUpdate(View container) {
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object instantiateItem(View view, int position) {
        final FrameLayout imageLayout = (FrameLayout) inflater.inflate(R.layout.item_image_gallery, null);
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        final ProgressBar loading = (ProgressBar) imageLayout.findViewById(R.id.loading);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .decodingType(DecodingType.MEMORY_SAVING)
                .build();
        imageLoader.displayImage(images[position], imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted() {
                loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(FailReason failReason) {
                loading.setVisibility(View.GONE);
                imageView.setImageResource(android.R.drawable.ic_delete);

                switch (failReason) {
                    case MEMORY_OVERFLOW:
                        imageLoader.clearMemoryCache();
                        break;
                }
            }

            @Override
            public void onLoadingComplete() {
                loading.setVisibility(View.GONE);
            }
        });
        ((ViewPager) view).addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}