package rsoni.WebServices;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

import rsoni.Model.Commodity;
import rsoni.Utils.DataResult;
import rsoni.Utils.Task;
import rsoni.Model.AppUser;
import rsoni.Model.NewsItem;
import rsoni.justagriagro.manage.App;


public class NetworkService {

	WebConnection connection = new WebConnection();
	DataParser dataParser = new DataParser();


	public DataResult UserAuth(Task task,AppUser appUser){
		String url = App.ServiceUrl ;
		String json = "";
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		System.out.println("22222");
		if (task == Task.mobile_login){
			url+="auth/sign-in";
			param.add(new BasicNameValuePair("opt", "sign-in"));
			param.add(new BasicNameValuePair("mobile", appUser.mobile));
			param.add(new BasicNameValuePair("pass", appUser.password));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.email_login){
			url+="auth";
			param.add(new BasicNameValuePair("email", appUser.email));
			param.add(new BasicNameValuePair("password", appUser.password));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.fb_login){
			url+="accounts";
			param.add(new BasicNameValuePair("email", appUser.email));

			return getResponce(url,Task.post,task,param);
		}else if (task == Task.g_login){
			url+="accounts/forgot-password";
			param.add(new BasicNameValuePair("email", appUser.email));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.forgot_pass){
			url+="accounts/forgot-password";
			param.add(new BasicNameValuePair("email", appUser.email));
			return getResponce(url,Task.post,task,param);
		}

		return null;

	}

	public DataResult Commodity(Task task,Commodity commodity) {
		System.out.println("SaleNode()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		if (task == Task.list_commodity){
			url+="commodity/list-commodity-price";
			param.add(new BasicNameValuePair("opt", "list-commodity-price"));
			param.add(new BasicNameValuePair("user_id", ""+App.appUser.id));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.search_commodity){
			url+="commodity/search-commodity-price";
			param.add(new BasicNameValuePair("opt", "search-commodity-price"));
			param.add(new BasicNameValuePair("id", ""+commodity.id));
			param.add(new BasicNameValuePair("commodity_name", ""+commodity.commodity_name));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.add_commodity){
			url+="commodity/add-commodity-price";
			param.add(new BasicNameValuePair("opt", "add-commodity-price"));
			param.add(new BasicNameValuePair("id", ""+commodity.id));
			param.add(new BasicNameValuePair("commodity_name", commodity.commodity_name));
			param.add(new BasicNameValuePair("commodity_cat_id", ""+commodity.commodity_cat_id));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.update_commodity){
			url+="commodity/update-sale-node";
			param.add(new BasicNameValuePair("opt", "update-commodity-price"));
			return getResponce(url,Task.post,task,param);
		}
		return null;
	}


	public DataResult News(Task task,NewsItem newsItem) {
		System.out.println("News()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		if (task == Task.get_news_details) { //
			url+="news/get-news-details";
			//param.add(new BasicNameValuePair("opt", "get-news-details"));
			param.add(new BasicNameValuePair("id", ""+newsItem.id));
			return getResponce(url,Task.get,task,param);
		}else if (task == Task.news_list_web){
			url+="news/news_list";
			//param.add(new BasicNameValuePair("opt", "news_list_sort"));
			param.add(new BasicNameValuePair("latest_id", ""+newsItem.id));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.add_news){
			url+="news.php";
			param.add(new BasicNameValuePair("opt", "add-news"));
			//param.add(new BasicNameValuePair("news_id", ""+newsItem.news_id));
			//param.add(new BasicNameValuePair("state_id", ""+newsItem.author));
			//param.add(new BasicNameValuePair("district_id", ""+newsItem.description));
			//param.add(new BasicNameValuePair("market_id", ""+newsItem.pub_date));
			//param.add(new BasicNameValuePair("usercat", ""+newsItem.link));
			//param.add(new BasicNameValuePair("usersubcat_id", ""+newsItem.thumburl));
			//param.add(new BasicNameValuePair("commodity_cat_id", ""+newsItem.title));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.delete_news){
			url+="news/delete-news";
			param.add(new BasicNameValuePair("opt", "delete-news"));
			param.add(new BasicNameValuePair("id", ""+newsItem.id));
			return getResponce(url,Task.post,task,param);
		}
		return null;
	}


	public DataResult Profile(Task task,AppUser appUser) {
		System.out.println("Profile()...");
		String url = App.ServiceUrl;
		ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
		if (task == Task.get_profile) { //
			url+="auth/get-profile";
			param.add(new BasicNameValuePair("opt", "get-profile"));
			param.add(new BasicNameValuePair("user_id", ""+appUser.id));
			return getResponce(url,Task.post,task,param);
		}else if (task == Task.update_profile){
			url+="auth/update-profile";
			param.add(new BasicNameValuePair("opt", "update-profile"));
			param.add(new BasicNameValuePair("id", ""+appUser.id));
			param.add(new BasicNameValuePair("mobile", appUser.mobile));
			param.add(new BasicNameValuePair("company_name", appUser.userProfile.company_name));
			param.add(new BasicNameValuePair("owner_name", appUser.userProfile.owner_name));
			param.add(new BasicNameValuePair("address", appUser.userProfile.address));
			param.add(new BasicNameValuePair("pincode", ""+appUser.userProfile.pincode));
			param.add(new BasicNameValuePair("state_id", ""+appUser.userProfile.state_id));
			param.add(new BasicNameValuePair("state_name", appUser.userProfile.state_name));
			param.add(new BasicNameValuePair("district_id", ""+appUser.userProfile.district_id));
			param.add(new BasicNameValuePair("district_name", appUser.userProfile.district_name));
			param.add(new BasicNameValuePair("market_id", ""+appUser.userProfile.market_id));
			param.add(new BasicNameValuePair("market_name", appUser.userProfile.market_name));
			param.add(new BasicNameValuePair("usersubcat_id", ""+appUser.userProfile.usersubcat_id));
			param.add(new BasicNameValuePair("business_id", ""+appUser.userProfile.business_id));
			return getResponce(url,Task.post,task,param);
		}
		return null;
	}

	private DataResult getResponce(String url, Task url_type , Task mode, ArrayList<NameValuePair> param) {
		System.out.println("getResponce()...");
		String json = null;
		if(url_type == Task.post) {
			json = connection.getJsonFromUrl2(url, param);
		}else
			json = connection.getJsonFromUrlGet(url, param);
		System.out.println("json : "+json);
		DataResult dataResult = null;
		if (json == null)
			dataResult = connection.invalidResponse();

		switch (mode) {

			case mobile_login:
			case email_login:
			case fb_login:
			case g_login:
				dataResult = dataParser.UserAuth(json, mode);
				break;

			case get_profile:
			case update_profile:
				dataResult = dataParser.Profile(json, mode);
				break;

			case forgot_pass:
				dataResult = dataParser.UserAuth(json, mode);
				break;


			case user_list:
			case add_user:
			case update_user:
				dataResult = dataParser.User(json, mode);
				break;

			case list_commodity:
			case add_commodity:
			case update_commodity:
			case search_commodity:
			case delete_commodity:
				dataResult = dataParser.Commodity(json, mode);
				break;

			case get_news_details:
			case news_list_sort:
			case news_list_web:
			case add_news:
			case delete_news:
				dataResult = dataParser.News(json, mode);
				break;


		default:
			break;
		}

		return dataResult;
	}




}
