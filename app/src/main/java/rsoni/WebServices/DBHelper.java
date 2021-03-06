package rsoni.WebServices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rsoni.Model.User;
import rsoni.Model.Commodity;
import rsoni.Model.CommodityCat;
import rsoni.Model.District;
import rsoni.Model.Market;
import rsoni.Model.NewsItem;
import rsoni.Model.State;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "MyDBName.db";
	public static final String TABLE_STATE = "state";
	public static final String TABLE_DISTRICT = "district";
	public static final String TABLE_MARKET = "market";
	public static final String TABLE_SALNODE = "salenode";
	public static final String TABLE_BUYNODE = "buynode";
	public static final String TABLE_NEWS = "news";
	public static final String TABLE_COMMODITYCAT = "commoditycat";
	public static final String TABLE_COMMODITY = "commodity";
	public static final String TABLE_COMMODITY_PRICE = "commodityprice";

	private HashMap hp;
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table state "
				+ "(id integer primary key, state_id integer, state_name text,country_id integer)");
		db.execSQL("create table district "
				+ "(id integer primary key, district_id integer,state_id integer, district_name text)");
		db.execSQL("create table market "
				+ "(id integer primary key, mandi_id integer, mandi_name text,district text)");
		db.execSQL("create table business "
				+ "(id integer primary key, business_id integer, business text)");
		db.execSQL("create table buynode "
				+ "(id integer primary key, user_id integer, buy_note text,state_id integer,district_id integer,market_id integer, commodity_cat_id integer,commodity_id integer,business_id integer,usercat integer,note_date integer)");
		db.execSQL("create table salenode "
				+ "(id integer primary key, user_id integer, sale_note text,state_id integer,district_id integer,market_id integer, commodity_cat_id integer,commodity_id integer,business_id integer,usercat integer,note_date integer)");
		db.execSQL("create table news "
				+ "(id integer primary key, news_type text, news_url text,news_title text,news_text text,news_ing text, news_date text)");
		db.execSQL("create table commoditycat "
				+ "(id integer primary key, commodity_cat text, commodity_desc text)");
		db.execSQL("create table commodity"
				+ "(id integer primary key, commodity_cat_id integer, commodity_name text, commodity_desc text)");
		db.execSQL("create table commodityprice "
				+ "(id integer primary key, user_id integer, price_note text,state_id integer,district_id integer,market_id integer, commodity_cat_id integer,commodity_id integer,commodity_name text,price_date integer)");


	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		onCreate(db);
	}

	public void truncateDB(){
		SQLiteDatabase db = this.getReadableDatabase();
		db.delete(TABLE_BUYNODE, null, null);
		db.delete(TABLE_SALNODE, null, null);
	}
	
	public int getLastNewsId() {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "";
			sql = "select max(newsitemid) from news";		
		Cursor res = db.rawQuery(sql, null);
		res.moveToFirst();		
		int id = res.getInt(0);
		db.close();
		return id;
	}

	public List<NewsItem> getAllNews() {
		List<NewsItem> newsItems = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from contacts", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				newsItems.add(NewsItem.getNewsItem(cursor));
				cursor.moveToNext();
			}
		}
		return newsItems;
	}

	public List<User> getUsers(){
		List<User> commodityPrices = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from commodityprice", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				commodityPrices.add(User.getUsers(cursor));
				cursor.moveToNext();
			}
		}
		return commodityPrices;
	}

	public User saveUser(User user){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues insertValues = new ContentValues();
		insertValues.put("id", user.id);
		insertValues.put("user_id", user.username);
		db.insert(TABLE_COMMODITY_PRICE, null, insertValues);
		return user;
	}
	public boolean saveCommodityPrices(List<User> users){
		SQLiteDatabase db = this.getReadableDatabase();
		// Begin the transaction
		db.beginTransaction();
		try{
			ContentValues insertValues = new ContentValues();
			for(User user : users){
				insertValues.clear();
				insertValues.put("id", user.id);
				insertValues.put("user_id", user.username);
				db.insert(TABLE_COMMODITY_PRICE,null,insertValues);
			}
			// Transaction is successful and all the records have been inserted
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.toString();
		}finally{
			//End the transaction
			db.endTransaction();
		}
		return true;
	}

	public List<NewsItem> getNews(int count){
		List<NewsItem> newsItems = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from news ORDER BY id DESC", null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				newsItems.add(NewsItem.getNewsItem(cursor));
				cursor.moveToNext();
			}
		}
		return newsItems;
	}
	public boolean saveNews(List<NewsItem> newsItems){
		SQLiteDatabase db = this.getReadableDatabase();
		// Begin the transaction
		db.beginTransaction();
		try{
			ContentValues insertValues = new ContentValues();
			for(NewsItem newsItem : newsItems){
				insertValues.clear();
				insertValues.put("id", newsItem.id);
				insertValues.put("news_title", newsItem.news_title);
				insertValues.put("news_text", newsItem.news_text);
				insertValues.put("news_img", newsItem.news_img);
				insertValues.put("news_date", newsItem.news_date);
				insertValues.put("link", newsItem.link);
				db.insert(TABLE_NEWS,null,insertValues);
			}
			// Transaction is successful and all the records have been inserted
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.toString();
		}finally{
			//End the transaction
			db.endTransaction();
		}
		return true;
	}

	public List<State> getStates(boolean with_select_option){
		List<State> states = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_STATE, null, null, null, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				states.add(State.getState(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) states.add(0,new State(with_select_option));
		return states;
	}

	public List<District> getDistricts(boolean with_select_option, int state_id){
		List<District> districts = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		//Cursor cursor = db.rawQuery("select * from state", null);
		//Cursor cursor = db.query(TABLE_DISTRICT, null, null, null, null, null, null);
		Cursor cursor = db.query(TABLE_DISTRICT, null, "state_id=?", new String[] { ""+state_id }, null, null, null);

		//String[] columnNames = cursor.getColumnNames();

		//System.out.println("columns : "+ App.gson.toJson(columnNames));

		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				districts.add(District.getDistrict(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) districts.add(0,new District(with_select_option));
		return districts;
	}

	public List<Market> getMarkets(boolean with_select_option, String district){
		List<Market> markets = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_MARKET, null, "district=?", new String[] { district }, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				markets.add(Market.getMarket(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) markets.add(0,new Market(with_select_option));
		return markets;
	}

	public List<CommodityCat> getCommodityCat(boolean with_select_option){
		List<CommodityCat> commodityCats = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_COMMODITYCAT, null, null, null, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				commodityCats.add(CommodityCat.getCommodityCat(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) commodityCats.add(0,new CommodityCat(with_select_option));
		return commodityCats;
	}
	public List<Commodity> getCommodity(boolean with_select_option, int cat_id){
		List<Commodity> commodities = new ArrayList<>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_COMMODITY, null, "commodity_cat_id=?", new String[] { ""+cat_id }, null, null, null);
		if (cursor .moveToFirst()) {
			while (cursor.isAfterLast() == false) {
				commodities.add(Commodity.getCommodity(cursor));
				cursor.moveToNext();
			}
		}
		if(with_select_option) commodities.add(0,new Commodity(with_select_option));
		return commodities;
	}

	public void AddMasterDataFromJson(Context context){

		SQLiteDatabase db = this.getWritableDatabase();
		try {

			db.beginTransaction();
			ContentValues values = new ContentValues();
			//add states
			List<State> states = State.getStateList(context);
			values.clear();
			for (State state : states) {
				values.put("state_id", state.state_id);
				values.put("state_name", state.state_name);
				db.insert(TABLE_STATE, null, values);
			}
			//db.setTransactionSuccessful();

			//add Districts
			//db.beginTransaction();
			List<District> districts = District.getDistricts(context);
			values.clear();
			for (District district : districts) {
				values.put("district_id", district.district_id);
				values.put("district_name", district.district_name);
				values.put("state_id", district.state_id);
				db.insert(TABLE_DISTRICT, null, values);
			}
			//db.setTransactionSuccessful();

			//add Markets
			//db.beginTransaction();
			List<Market> markets = Market.getMarkets(context);
			values.clear();
			for (Market  market : markets) {
				System.out.println("Market : "+market.mandi_name+" >> "+market.mandi_id);
				values.put("mandi_id", market.mandi_id);
				values.put("mandi_name", market.mandi_name);
				values.put("district", market.district);
				db.insert(TABLE_MARKET, null, values);
			}
			//db.setTransactionSuccessful();

			//add CommodityCats
			//db.beginTransaction();
			List<CommodityCat> commodityCats = CommodityCat.getCommodityCat(context);
			values.clear();
			for (CommodityCat commodityCat : commodityCats) {
				values.put("id", commodityCat.id);
				values.put("commodity_cat", commodityCat.commodity_cat);
				db.insert(TABLE_COMMODITYCAT, null, values);
			}
			//db.setTransactionSuccessful();

			//add Commodity
			//db.beginTransaction();
			List<Commodity> commodities = Commodity.getCommodities(context);
			values.clear();
			for (Commodity  commodity : commodities) {
				values.put("id", commodity.id);
				values.put("commodity_name", commodity.commodity_name);
				values.put("commodity_cat_id", commodity.commodity_cat_id);
				db.insert(TABLE_COMMODITY, null, values);
			}
			db.setTransactionSuccessful();


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

	}


	public boolean insertContact(String name, String phone, String email,
								 String street, String place) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("name", name);
		contentValues.put("phone", phone);
		contentValues.put("email", email);
		contentValues.put("street", street);
		contentValues.put("place", place);

		db.insert("contacts", null, contentValues);
		db.close();
		return true;
	}

	public Cursor getData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from contacts where id=" + id + "",
				null);
		return res;
	}

	public int numberOfRows() {
		SQLiteDatabase db = this.getReadableDatabase();
		int numRows = (int) DatabaseUtils.queryNumEntries(db,
				TABLE_NEWS);
		return numRows;
	}

	public boolean updateContact(Integer id, String name, String phone,
								 String email, String street, String place) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", name);
		contentValues.put("phone", phone);
		contentValues.put("email", email);
		contentValues.put("street", street);
		contentValues.put("place", place);
		db.update("contacts", contentValues, "id = ? ",
				new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteContact(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete("contacts", "id = ? ",
				new String[] { Integer.toString(id) });
	}

	public ArrayList getAllCotacts() {
		ArrayList array_list = new ArrayList();
		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from contacts", null);
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			array_list.add(res.getString(res
					.getColumnIndex(TABLE_NEWS)));
			res.moveToNext();
		}
		return array_list;
	}

}
