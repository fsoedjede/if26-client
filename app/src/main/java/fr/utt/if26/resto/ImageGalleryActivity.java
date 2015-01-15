package fr.utt.if26.resto;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import fr.utt.if26.resto.Adapters.ImagePagerAdapter;


public class ImageGalleryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        Bundle bundle = getIntent().getExtras();
        String[] imageUrls = bundle.getStringArray("fr.utt.if26.photoURLs");
        int galleryPosition = bundle.getInt("fr.utt.if26.position", 0);

        ViewPager gallery = (ViewPager) findViewById(R.id.pager);
        gallery.setAdapter(new ImagePagerAdapter(imageUrls, getLayoutInflater()));
        gallery.setCurrentItem(galleryPosition);
    }

}
