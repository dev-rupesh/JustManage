package rsoni.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import rsoni.Model.AppUser;
import rsoni.Model.Commodity;
import rsoni.Model.NewsItem;
import rsoni.Model.User;
import rsoni.Model.UserProfile;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;


public class DataParser {
	String error_msg = "";

	private JSONObject Start(String json, DataResult result) {
		System.out.println("in Start");
		JSONObject jsonResponse = null;
		if (json == null) {
			result.Status = false;
			result.msg = "Responce is blank";
			return null;
		}
		try {
			json.trim();
			System.out.println("get json : "+json);
			jsonResponse = new JSONObject(json);
			result.Status = jsonResponse.optBoolean("status");
			//System.out.println("jsonResponse.getString(Status) : "
			//		+ jsonResponse.optString("Status"));
			System.out.println("result.Status : "+result.Status);
			result.msg = jsonResponse.optString("msg");
			result.extras = jsonResponse.optJSONArray("non_field_errors");
			result.extras2 = jsonResponse.optJSONArray("field_errors");

		} catch (JSONException e) {
			result.Status = false;
			result.msg = "server error";
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	private JSONObject StartForSuccess(String json, DataResult result) {
		System.out.println("in Start");
		JSONObject jsonResponse = null;
		if (json == null) {
			result.Status = false;
			result.msg = "Responce is blank";
			return null;
		}
		try {
			json.trim();
			jsonResponse = new JSONObject(json);
			int success = jsonResponse.optInt("success");
			result.Status = (success==1)?true:false;
			System.out.println("success : "+success);
			//System.out.println("jsonResponse.getString(Status) : "
			//		+ jsonResponse.optString("Status"));
			System.out.println("result.Status : "+result.Status);
			result.msg = jsonResponse.optString("msg");

		} catch (JSONException e) {
			result.Status = false;
			result.msg = "server error";
			e.printStackTrace();
		}
		return jsonResponse;
	}
	
	private JSONObject StartForSuccessBoolean(String json, DataResult result) {
		System.out.println("in Start");
		JSONObject jsonResponse = null;
		if (json == null) {
			result.Status = false;
			result.msg = "Responce is blank";
			return null;
		}
		try {
			json.trim();
			jsonResponse = new JSONObject(json);
			boolean success = jsonResponse.optBoolean("status");
			result.Status = success;
			System.out.println("success : "+success);
			//System.out.println("jsonResponse.getString(Status) : "
			//		+ jsonResponse.optString("Status"));
			System.out.println("result.Status : "+result.Status);
			result.msg = jsonResponse.optString("msg");

		} catch (JSONException e) {
			result.Status = false;
			result.msg = "server error";
			e.printStackTrace();
		}
		return jsonResponse;
	}


	
	
	public DataResult UserAuth(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if ( mode == Task.mobile_login || mode == Task.fb_login || mode == Task.g_login  ) {
					result.Data = AppUser.getAppUserByJsonObject(response.getJSONObject("data"));
				} 

			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}

	public DataResult User(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if ( mode == Task.add_user || mode == Task.update_user ) {
					result.Data = User.getUser(response.getJSONObject("data"));
				}else if ( mode == Task.user_list ) {
					result.Data = User.getUsers(response.getJSONArray("data"));
				}
			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}

	public DataResult Commodity(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if ( mode == Task.add_commodity || mode == Task.update_commodity ) {
					result.Data = Commodity.getCommodity(response.getJSONObject("data"));
				}else if ( mode == Task.list_commodity || mode == Task.search_commodity) {
					result.Data = Commodity.getCommodities(response.getJSONArray("data"));
				}
			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}

	public DataResult News(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if ( mode == Task.add_news || mode==Task.get_news_details) {
					result.Data = AppUser.getAppUserByJsonObject(response.getJSONObject("data"));
				}else if ( mode == Task.news_list_web) {
					result.Data = NewsItem.getNewsItems(response.getJSONArray("data"));
				}else if ( mode == Task.delete_news) {
					result.Data = AppUser.getAppUserByJsonObject(response.getJSONObject("data"));
				}
			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}

	
	public DataResult Profile(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if (mode == Task.get_profile || mode == Task.update_profile ) {
					result.Data = UserProfile.getUserProfileByJsonObject(response.getJSONObject("data"));
				}
			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}
	

}