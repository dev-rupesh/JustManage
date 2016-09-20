package rsoni.Model;

import android.database.Cursor;

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
}
