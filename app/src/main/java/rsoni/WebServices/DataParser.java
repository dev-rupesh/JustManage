package rsoni.WebServices;

import org.json.JSONException;
import org.json.JSONObject;

import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.modal.AppUser;
import rsoni.modal.BuyNode;
import rsoni.modal.CommodityPrice;
import rsoni.modal.NewsItem;
import rsoni.modal.SaleNode;
import rsoni.modal.SearchResult;
import rsoni.modal.UserProfile;


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
	public DataResult Search(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = StartForSuccessBoolean(json, result);
		if (result.Status ) {
			try {				
				switch (mode) {	
					case buyer_or_seller_search:
						result.Data = SearchResult.getBuyerSerch(response.getJSONObject("data"));
						break;
					case buyer_search:
						result.Data = SearchResult.getBuyerSerch(response.getJSONObject("data"));
						break;
					case seller_search:
						result.Data = SearchResult.getSellerSerch(response.getJSONObject("data"));
						break;
					case crop_search:
						result.Data = SearchResult.getCropSerch(response.getJSONObject("data"));
						break;
					default:
					break;
				}
			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	
	/*public DataResult SearchJson(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		if(mode == Task.cuisines_search_json || mode == Task.cuisines_search){
			try {
				response = new JSONObject(json);
				result.Status = true;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			response = StartForSuccessBoolean(json, result);
		}
		
		if (result.Status ) {
			try {				
				switch (mode) {	
				case categories_search_json:
					result.Data = response.getJSONArray("categories");
					break;
				case cuisines_search_json:
					result.Data = response.getJSONArray("cuisines");
					break;
				case areas_search_json:
					result.Data = response.getJSONArray("areas");
					break;
				case features_search_json:
					result.Data = response.getJSONArray("filters");
					break;
				case filters_search_json:
					result.Data = response.getJSONArray("filters");
					break;
				case restaurants_search_json:
					result.Data = response.getJSONArray("restaurants");
					break;
				default:
					break;
				}
			} catch (JSONException e) {
				result.Status = false;
				result.msg = "" + e;
				e.printStackTrace();
			}
		}
		return result;
	}*/


	
	
	public DataResult UserAuth(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if ( mode == Task.mobile_login || mode == Task.mobile_register || mode == Task.fb_login || mode == Task.g_login  ) {
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

	public DataResult SaleNode(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if ( mode == Task.add_sale_node || mode == Task.add_sale_node ) {
					result.Data = SaleNode.getSaleNode(response.getJSONObject("data"));
				}else if ( mode == Task.list_sale_node || mode == Task.seller_search) {
					result.Data = SaleNode.getSaleNodeItems(response.getJSONArray("data"));
				}else if ( mode == Task.delete_sale_node || mode == Task.add_sale_node ) {
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
	public DataResult BuyNode(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if ( mode == Task.add_buy_node || mode == Task.add_buy_node ) {
					result.Data = BuyNode.getBuyNode(response.getJSONObject("data"));
				}else if ( mode == Task.list_buy_node || mode == Task.buyer_search) {
					result.Data = BuyNode.getBuyNodeItems(response.getJSONArray("data"));
				}else if ( mode == Task.delete_buy_node || mode == Task.add_buy_node ) {
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

	public DataResult CommodityPrice(String json, Task mode) {
		JSONObject response = null;
		DataResult result = new DataResult();
		response = Start(json, result);
		if (result.Status ) {
			try {
				if ( mode == Task.add_commodity_price || mode == Task.update_commodity_price ) {
					result.Data = CommodityPrice.getBuyNode(response.getJSONObject("data"));
				}else if ( mode == Task.list_commodity_price || mode == Task.search_commodity_price) {
					result.Data = CommodityPrice.getCommodityPrice(response.getJSONArray("data"));
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