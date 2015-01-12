package fr.utt.if26.resto.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by soedjede on 04/01/15 for Resto
 */
public class User {
    private String _id, email, username, first_name, last_name, password, salt;
    private Position position;

    public User() {
    }

    public User(String _id, String email, String username, String first_name, String last_name, String password, String salt, Position position) {
        this._id = _id;
        this.email = email;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.salt = salt;
        this.position = position;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void JsonUserParse(JSONObject json_user) throws JSONException {
        this._id = json_user.getString("_id");
        this.email = json_user.getString("email");
        this.username = json_user.getString("username");
        this.first_name = json_user.getString("first_name");
        this.last_name = json_user.getString("last_name");
        this.password = null;
        this.salt = null;
        Position pos = new Position();
        pos.JsonPositionParse(json_user.getJSONObject("position"));
        this.position = pos;
    }
}
