package fr.utt.if26.resto.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import fr.utt.if26.resto.R;

/**
 * Created by soedjede on 06/01/15 for Resto
 */
public class Rating implements Serializable {
    private double received;
    private int total;

    public Rating() {
        received = 0;
        total = 0;
    }

    public Rating(double received, int total) {
        this.received = received;
        this.total = total;
    }

    public double getReceived() {
        return received;
    }

    public void setReceived(double received) {
        this.received = received;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void JsonRatingParse(JSONObject json_rating) throws JSONException {
        this.received = json_rating.getDouble("received");
        this.total = json_rating.getInt("total");
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
