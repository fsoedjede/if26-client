package fr.utt.if26.resto.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.List;

import fr.utt.if26.resto.Model.Restaurant;
import fr.utt.if26.resto.R;

/**
 * Created by soedjede on 26/12/14 for Resto
 */
public class SearchListRestoAdapter extends CursorAdapter {

    private List<Restaurant> restos;
    private TextView text;

    public SearchListRestoAdapter(Context context, Cursor c, List<Restaurant> restos) {
        super(context, c, false);
        this.restos = restos;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item, parent, false);
        text = (TextView) view.findViewById(R.id.item);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        text.setText(restos.get(cursor.getPosition()).getName());
    }
}
