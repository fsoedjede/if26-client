package fr.utt.if26.resto.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DecodingType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.FailReason;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

import fr.utt.if26.resto.R;

/**
 * Created by soedjede on 12/01/15 for Resto
 */
public class ImageGalleryAdapter extends BaseAdapter {

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private String[] images;
    private LayoutInflater inflater;

    public ImageGalleryAdapter(String[] images, LayoutInflater inflater) {
        this.images = images;
        this.inflater = inflater;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        if (convertView == null) {
            imageView = (ImageView) inflater.inflate(R.layout.item_gallery_image, parent, false);
        } else {
            imageView = (ImageView) convertView;
        }
        if(images[position].equals("addnew")){
            imageView.setImageResource(R.drawable.image_add);
        } else {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.ajax_loader)
                    .cacheInMemory()
                    .cacheOnDisc()
                    .decodingType(DecodingType.FAST)
                    .build();
            imageLoader.displayImage(images[position], imageView, options, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted() {
                    // do nothing
                }

                @Override
                public void onLoadingFailed(FailReason failReason) {
                    imageView.setImageResource(android.R.drawable.ic_delete);
                    switch (failReason) {
                        case MEMORY_OVERFLOW:
                            imageLoader.clearMemoryCache();
                            break;
                    }
                }

                @Override
                public void onLoadingComplete() {
                    // do nothing
                    imageLoader.stop();
                }
            });
        }
        return imageView;
    }
}