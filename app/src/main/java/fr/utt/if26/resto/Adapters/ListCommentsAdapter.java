package fr.utt.if26.resto.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.utt.if26.resto.Model.Comment;
import fr.utt.if26.resto.Model.Rating;
import fr.utt.if26.resto.R;

/**
 * Created by soedjede on 23/12/14 for Resto
 */
public class ListCommentsAdapter extends ArrayAdapter {

    private List<Comment> comments = new ArrayList<>();

    public ListCommentsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Comment object) {
        comments.add(object);
        //super.add(object);
    }

    public void addMultiple(List<Comment> object) {
        //restos = object;
        for (Comment comment : object) {
            comments.add(comment);
            //super.add(comment);
        }
    }

    public int getCount() {
        return comments.size();
    }

    public Comment getItem(int index) {
        return comments.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_resto_comment, parent, false);
        }
        //LinearLayout wrapper = (LinearLayout) row.findViewById(R.id.comment_layout);

        Comment comment = getItem(position);

        TextView comment_user_name = (TextView) row.findViewById(R.id.comment_user_name);
        ImageView comment_user_rate = (ImageView) row.findViewById(R.id.comment_user_rate);
        TextView comment_user_date = (TextView) row.findViewById(R.id.comment_user_date);
        TextView comment_user_comment = (TextView) row.findViewById(R.id.comment_user_comment);

        comment_user_name.setText(comment.getAuthor_name());
        comment_user_rate.setImageResource(Rating.RatingStars(comment.getRating()));
        comment_user_comment.setText(comment.getText());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = format.parse(comment.getPosted_at().substring(0, 10));
        } catch (ParseException e) {
            date = new Date();
        }
        comment_user_date.setText(formatter.format(date));

        return row;
    }

}
