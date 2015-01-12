package fr.utt.if26.resto.Model;

/**
 * Created by soedjede on 06/01/15 for Resto
 */
public class Comment {
    private String author_name, text;
    private int rating;
    private String posted_at;

    public Comment() {
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPosted_at() {
        return posted_at;
    }

    public void setPosted_at(String posted_at) {
        this.posted_at = posted_at;
    }
}
