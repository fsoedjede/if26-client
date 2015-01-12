package fr.utt.if26.resto.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import fr.utt.if26.resto.R;

/**
 * Created by soedjede on 06/01/15 for Resto
 */
public class Rating implements Serializable {
    private int received;
    private double total;

    public Rating() {
        received = 0;
        total = 0;
    }

    public Rating(int received, double total) {
        this.received = received;
        this.total = total;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void JsonRatingParse(JSONObject json_rating) throws JSONException {
        this.received = json_rating.getInt("received");
        this.total = json_rating.getDouble("total");
    }

    public static int RatingStars(double rate) {
        if(rate < 1.5)
            return R.drawable.star_rating_one;
        else
            if (rate < 2.5)
                return R.drawable.star_rating_two;
            else
                if (rate < 3.5)
                    return R.drawable.star_rating_three;
                else
                    if (rate < 4.5)
                        return R.drawable.star_rating_four;
                    else
                        return R.drawable.star_rating_five;
    }
}
