package com.item.demo;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

/**
 * EditText的一个Demo 实现EditText + PopupWindow来实现搜索的功能
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends Activity {
	private PopupWindow popupWindow;
	private ListView lView;
	private MyAdapter adapter;
	private EditText edtText;
	private List<String> list = new ArrayList<String>();
	private List<SpannableString> carNumber = new ArrayList<SpannableString>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		edtText =  findViewById(R.id.edt_hello);
		initData();
		adapter = new MyAdapter(this, carNumber);
		edtText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(s.length() > 0){
					if(popupWindow == null){
						View contentView = View.inflate(MainActivity.this, R.layout.item_list, null);
						lView = (ListView)contentView.findViewById(R.id.item_list);
						popupWindow = new PopupWindow(contentView,edtText.getWidth(),LayoutParams.WRAP_CONTENT,true);
					}
					carNumber.clear();
					for(String str : list){
						if(str.startsWith("" + s)){
							SpannableString ss = new SpannableString(str);
							ss.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
							carNumber.add(ss);
						}
					}
					if(carNumber.size() == 0){
						SpannableString ssString = new SpannableString("无该车牌号");
						ssString.setSpan(new ForegroundColorSpan(Color.RED), 0, ssString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						carNumber.add(ssString);
					}
					adapter.notifyDataSetChanged();
					lView.setAdapter(adapter);
					popupWindow.setOutsideTouchable(true);
					popupWindow.setFocusable(false);
					popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					popupWindow.showAsDropDown(edtText, 0, 0);
					if(carNumber.size() >=5){
						popupWindow.update(edtText.getWidth(), 250);
					}else {
						popupWindow.update(edtText.getWidth(), LayoutParams.WRAP_CONTENT);
					}
					lView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							edtText.setText(carNumber.get(arg2).toString());
							if(popupWindow !=null && popupWindow.isShowing()){
								popupWindow.dismiss();
							}
						}
					});
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	/**
	 * 模拟数据
	 */
	private void initData(){
		list.add("皖A0000");
		list.add("皖A1000");
		list.add("皖A1100");
		list.add("皖A1110");
		list.add("皖A1111");
		list.add("皖A2000");
		list.add("皖A2100");
		list.add("皖A2110");
		list.add("皖A2111");
		list.add("皖A2200");
		list.add("皖A2201");
		list.add("皖A2210");
		list.add("皖A2211");
		list.add("皖A2223");
		list.add("皖A3000");
		list.add("皖A4030");
		list.add("皖A5000");
		list.add("皖A6200");
		list.add("皖A7060");
		list.add("皖A8070");
		list.add("皖A9000");
	}
	/**
	 * 适配器
	 * @author Administrator
	 *
	 */
	private class MyAdapter extends BaseAdapter{
		private Context context;
		private List<SpannableString> carList;
		
		public MyAdapter(Context context, List<SpannableString> carList) {
			super();
			this.context = context;
			this.carList = carList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return carList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHoler holer = null;
			if(arg1 == null) {
				holer = new ViewHoler();
				arg1 = View.inflate(context, R.layout.item_adpter, null);
				holer.tv_name = (TextView)arg1.findViewById(R.id.item_carnum);
				arg1.setTag(holer);
			}else {
				holer = (ViewHoler)arg1.getTag();
			}
			holer.tv_name.setText(carList.get(arg0));
			return arg1;
		}
		class ViewHoler{
			private TextView tv_name;
		}
	}

}
