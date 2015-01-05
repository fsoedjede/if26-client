package fr.utt.if26.resto.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by soedjede on 23/12/14 for Resto
 */
public class Restaurant {
    private String _id, name, description, url, tel, mail;
    private Position position;

    public Restaurant() {
    }

    public Restaurant(String _id, String name, String description, String url, String tel, String mail, Position position) {
        this._id = _id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.tel = tel;
        this.mail = mail;
        this.position = position;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void JsonRestaurantParse(JSONObject json_resto) throws JSONException {
        this.name = json_resto.getString("name");
        this.description = json_resto.getString("description");
        this.url = json_resto.getString("url");
        this.tel = json_resto.getString("tel");
        this.mail = json_resto.getString("mail");
        Position pos = new Position();
        pos.JsonPositionParse(json_resto.getJSONObject("position"));
        this.position = pos;
    }
}
