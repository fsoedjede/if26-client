package fr.utt.if26.resto;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DecodingType;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.FailReason;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoadingListener;

import fr.utt.if26.resto.AsyncTasks.RestoPhotosLinksTask;
import fr.utt.if26.resto.Interfaces.OnRestoPhotoLinksTaskCompleted;

/**
 * Created by soedjede on 12/01/15 for Resto
 */
public class GalleryActivity extends Activity implements OnRestoPhotoLinksTaskCompleted {

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private String[] imageUrls;
    //private GridView gridview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Bundle bundle = getIntent().getExtras();
        String _id = bundle.getString("fr.utt.if26._id");
        new RestoPhotosLinksTask(this, GalleryActivity.this).execute(_id);

        /*gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(GalleryActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        imageLoader.stop();
        super.onDestroy();
    }

    @Override
    public void getPhotosURLs(String[] URLs) {
        imageUrls = URLs;
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter());
    }

    public class ImageAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return imageUrls.length;
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
                imageView = (ImageView) getLayoutInflater().inflate(R.layout.item_gallery_image, parent, false);
            } else {
                imageView = (ImageView) convertView;
            }

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showStubImage(R.drawable.ajax_loader)
                    .cacheInMemory()
                    .cacheOnDisc()
                    .decodingType(DecodingType.FAST)
                    .build();
            imageLoader.displayImage(imageUrls[position], imageView, options, new ImageLoadingListener() {
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
                }
            });
            return imageView;
        }
    }

}