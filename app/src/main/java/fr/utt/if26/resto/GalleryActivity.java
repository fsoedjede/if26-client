package fr.utt.if26.resto;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import fr.utt.if26.resto.Adapters.ImageGalleryAdapter;
import fr.utt.if26.resto.AsyncTasks.RestoPhotosLinksTask;
import fr.utt.if26.resto.Interfaces.OnRestoPhotoLinksTaskCompleted;

/**
 * Created by soedjede on 12/01/15 for Resto
 */
public class GalleryActivity extends BaseActivity implements OnRestoPhotoLinksTaskCompleted {

    private String[] imageUrls;

    private static final int ACTION_TAKE_PHOTO_S = 2;
    private Bitmap mImageBitmap;
    String _id;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Bundle bundle = getIntent().getExtras();
        _id = bundle.getString("fr.utt.if26._id");
        new RestoPhotosLinksTask(this, GalleryActivity.this).execute(_id);

        mImageBitmap = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getPhotosURLs(String[] URLs, Bitmap bm) {
        imageUrls = URLs;
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageGalleryAdapter(getImageUrls(), getLayoutInflater(), bm));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (imageUrls[position].equals("addnew")) {
                    if (isIntentAvailable(GalleryActivity.this, MediaStore.ACTION_IMAGE_CAPTURE)) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO_S);
                        //Toast.makeText(GalleryActivity.this, "You could add a picture soon", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(GalleryActivity.this, "You camera is not available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    startImageGalleryActivity(position);
                }
            }
        });
    }

    private String[] getImageUrls() {
        if (Resto.user != null) {
            return append(imageUrls, "addnew");
        }
        return imageUrls;
    }

    private void startImageGalleryActivity(int position) {
        Intent intent = new Intent(this, ImageGalleryActivity.class);
        intent.putExtra("fr.utt.if26.photoURLs", imageUrls);// Arrays.copyOfRange(imageUrls, 0, imageUrls.length - 1));
        intent.putExtra("fr.utt.if26.position", position);
        startActivity(intent);
    }

    static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("resultCode", String.valueOf(resultCode));
        switch (requestCode) {
            case ACTION_TAKE_PHOTO_S: {
                if (resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    mImageBitmap = (Bitmap) extras.get("data");
                    //TODO : Fix Post Picture bug
                    this.getPhotosURLs(append(Arrays.copyOfRange(imageUrls, 0, imageUrls.length - 1), "mypicture"), mImageBitmap);
                    //new AddPhotoTask(this).execute(_id, ImageUtility.BitMapToString(mImageBitmap));
                }
                break;
            }
        } // switch
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if(Resto.user != null){
            getMenuInflater().inflate(R.menu.menu_main_connected, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        if (i != 0)
            getPhotosURLs(imageUrls, null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        i++;
    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

}