package rsoni.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import rsoni.Model.User;
import rsoni.justagriagro.manage.R;


public class UserListAdaptor extends BaseAdapter {

	//ImageLoader imageLoader;
	List<User> users;
	User user;
	Context context;
	LayoutInflater layoutInflater;
	int mGMTOffset ;

	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-mm");

	public UserListAdaptor(Context context, List<User> users) {
		this.context = context;
		this.users = users;
		//imageLoader = new ImageLoader(activity);
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TimeZone mTimeZone = TimeZone.getDefault();
		mGMTOffset = mTimeZone.getRawOffset();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return users.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return users.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		user = users.get(position);

		viewHolder holder;

		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.list_item_commodity_price, null);
			holder = new viewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (viewHolder) convertView.getTag();
		}
		holder.setData(user);
		return convertView;
	}

	class viewHolder {

		TextView tv_business_type, tv_note_date, tv_note;
		//ImageView iv_news_thumb;

		public viewHolder(View convertView) {
			tv_business_type = (TextView) convertView
					.findViewById(R.id.tv_business_type);
			tv_note_date = (TextView) convertView
					.findViewById(R.id.tv_note_date);
			tv_note = (TextView) convertView.findViewById(R.id.tv_note);
			// iv_news_thumb = (ImageView)
			// convertView.findViewById(R.id.iv_news_thumb);
		}

		void setData(User user) {
			tv_business_type.setText(user.username);
			tv_note.setText(user.mobile);
			// tv_new_date.setText(newsItem.pubDate);
			// try {
			// tv_new_date.setText(App.MonthDateYearTime.format(formatter.parse(newsItem.pubDate)));
			// } catch (ParseException e) {
			//
			// e.printStackTrace();
			// }
			// System.out.println("newsItem.thumburl : "+newsItem.thumburl);
			// iv_news_thumb.setVisibility(View.GONE);
			/*
			 * if(newsItem.thumburl!=null &&
			 * newsItem.thumburl.startsWith("http") ){
			 * iv_news_thumb.setVisibility(View.VISIBLE);
			 * imageLoader.DisplayImage(newsItem.thumburl, iv_news_thumb); }
			 */
		}

	}

}
