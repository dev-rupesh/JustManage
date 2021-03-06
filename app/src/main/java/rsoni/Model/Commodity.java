package rsoni.Model;

import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by soni on 8/13/2016.
 */
public class Commodity {

    public int id;
    public String commodity_name;
    public int commodity_cat_id;

    public Commodity(){}

    public Commodity(boolean root){
        this.id = -1;
        this.commodity_name = " -- Select One -- ";
    }

    public Commodity(int id, String commodity_name, int commodity_cat_id) {
        this.id = id;
        this.commodity_name = commodity_name;
        this.commodity_cat_id = commodity_cat_id;
    }

    public static Commodity getCommodity(Cursor cursor){
        Commodity commodity = new Commodity(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("commodity_name")),
                cursor.getInt(cursor.getColumnIndex("commodity_cat_id")));

        System.out.println("commodity : "+commodity.id+">>"+commodity.commodity_name);

        return commodity;
    }

    public static List<Commodity> getCommodities(Context context) throws IOException {
        Type listType = new TypeToken<List<Commodity>>() {}.getType();
        InputStream input = context.getAssets().open("commodity.json");
        Reader reader = new InputStreamReader(input, "UTF-8");
        List<Commodity> commodities = new Gson().fromJson(reader,listType);
        return commodities;
    }

    public static Commodity getCommodity(JSONObject data){

        Commodity commodity = new Commodity(data.optInt("id"),
                data.optString("news_type"),
                data.optInt("news_title"));

        return commodity;

    }

    public static ArrayList<Commodity> getCommodities(JSONArray jsson_array_commodity) {
        ArrayList<Commodity> commodities = new ArrayList<Commodity>();
        try {
            JSONObject json_commodity;
            for (int i = 0; i < jsson_array_commodity.length(); i++) {
                json_commodity = (JSONObject) jsson_array_commodity.get(i);
                System.out.println("json_comment : "+json_commodity.toString());
                commodities.add(getCommodity(json_commodity));

            }
        } catch (JSONException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return commodities;
    }

    @Override
    public String toString() {
        return commodity_name.trim();
    }


}
