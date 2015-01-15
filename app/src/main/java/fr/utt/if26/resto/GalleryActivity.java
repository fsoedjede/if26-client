package fr.utt.if26.resto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Arrays;

import fr.utt.if26.resto.Adapters.ImageGalleryAdapter;
import fr.utt.if26.resto.AsyncTasks.RestoPhotosLinksTask;
import fr.utt.if26.resto.Interfaces.OnRestoPhotoLinksTaskCompleted;

/**
 * Created by soedjede on 12/01/15 for Resto
 */
public class GalleryActivity extends Activity implements OnRestoPhotoLinksTaskCompleted {

    private String[] imageUrls;

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
        super.onDestroy();
    }

    @Override
    public void getPhotosURLs(String[] URLs) {
        imageUrls = URLs;
        imageUrls = append(imageUrls, "addnew");
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new ImageGalleryAdapter(imageUrls, getLayoutInflater()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(imageUrls[position].equals("addnew")) {
                    Toast.makeText(GalleryActivity.this, "You could add a picture soon", Toast.LENGTH_SHORT).show();
                } else {
                    startImageGalleryActivity(position);
                }
            }
        });
    }

    private void startImageGalleryActivity(int position) {
        Intent intent = new Intent(this, ImageGalleryActivity.class);
        intent.putExtra("fr.utt.if26.photoURLs", Arrays.copyOfRange(imageUrls, 0, imageUrls.length - 1));
        intent.putExtra("fr.utt.if26.position", position);
        startActivity(intent);
    }

    static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

}