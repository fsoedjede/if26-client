package fr.utt.if26.resto.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.utt.if26.resto.Model.Restaurant;
import fr.utt.if26.resto.R;
import fr.utt.if26.resto.Resto;
import fr.utt.if26.resto.Tools.MapsUtility;

/**
 * Created by soedjede on 23/12/14 for Resto
 */
public class ListRestoAdapter extends ArrayAdapter {

    private List<Restaurant> restos = new ArrayList<>();

    public ListRestoAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Restaurant object) {
        restos.add(object);
        //super.add(object);
    }

    public void addMultiple(List<Restaurant> object) {
        //restos = object;
        for (Restaurant resto : object) {
            restos.add(resto);
            //super.add(resto);
        }
    }

    public int getCount() {
        return restos.size();
    }

    public Restaurant getItem(int index) {
        return restos.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_list_resto, parent, false);
        }
        //LinearLayout wrapper = (LinearLayout) row.findViewById(R.id.list_item_resto);

        Restaurant resto = getItem(position);
        String name = resto.getName();
        String address = resto.getPosition().getAddress();

        double distance = MapsUtility.haversine(resto.getPosition(), Resto.userPosition);

        TextView tv_distance = (TextView) row.findViewById(R.id.resto_distance);
        if(distance < 1) {
            tv_distance.setText(String.format("%.2f", distance * 1000) + " m");
        } else {
            tv_distance.setText(String.format("%.2f", distance) + " km");
        }
        RatingBar resto_rate = (RatingBar) row.findViewById(R.id.resto_rate);
        resto_rate.setRating((float)resto.getRatings().getReceived());

        TextView tv_description = (TextView) row.findViewById(R.id.resto_description);
        tv_description.setText(String.format("%s\n%s", name, address));
        /*if (website.length() < 5){
        tv_description.setText(
                String.format("%s\n%s\n"
                        + getContext().getString(R.string.item_content_telephone) + " : %s",
                        name, address, telephone)
        );
        } else {
            tv_description.setText(
                    String.format("%s\n%s\n"
                                    +getContext().getString(R.string.item_content_telephone)+" : %s\n"
                                    +getContext().getString(R.string.item_content_website)+" : %s",
                            name, address, telephone, website)
            );
        }*/
        return row;
    }

}
