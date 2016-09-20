package rsoni.Model;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DS1 on 03/08/16.
 */
public class User {
    public int id = 0;
    public String username = "";
    public String email = "";
    public String password = "";
    public int userCategory = 0;
    public String mobile = "";

    public static User getUsers(Cursor cursor) {
        return null;
    }

    public User(int id, String username, String email, String password, int userCategory, String mobile) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userCategory = userCategory;
        this.mobile = mobile;
    }

    public static User getUser(JSONObject data){

        User user = new User(data.optInt("id"),
                data.optString("news_type"),
                data.optString("news_title"),
                data.optString("news_text"),
                data.optInt("news_img"),
                data.optString("news_date"));

        return user;

    }

    public static ArrayList<User> getUsers(JSONArray jsson_array_news) {
        ArrayList<User> users = new ArrayList<User>();
        try {
            JSONObject json_news;
            for (int i = 0; i < jsson_array_news.length(); i++) {
                json_news = (JSONObject) jsson_array_news.get(i);
                System.out.println("json_comment : "+json_news.toString());
                users.add(getUser(json_news));

            }
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return users;
    }
}
