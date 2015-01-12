package fr.utt.if26.resto.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by soedjede on 23/12/14 for Resto
 */
public class Position implements Serializable{

    private String latitude, longitude, address;

    public Position() {
    }

    public Position(String latitude, String longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void JsonPositionParse(JSONObject json_position) throws JSONException {
        this.address = json_position.getString("name");
        JSONArray coordinates = json_position.getJSONArray("coordinates");
        this.latitude = coordinates.getString(0);
        this.longitude = coordinates.getString(1);
        /*this.latitude = json_position.getString("latitude");
        this.longitude = json_position.getString("longitude");
        this.address = json_position.getString("address");*/
    }
}
