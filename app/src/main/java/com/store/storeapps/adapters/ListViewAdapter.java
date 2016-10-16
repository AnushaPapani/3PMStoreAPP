package com.store.storeapps.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.store.storeapps.R;
import com.store.storeapps.activities.FriendInfor;

import java.util.List;


public class ListViewAdapter extends ArrayAdapter<FriendInfor> {

	private Activity activity;
	public BaseAdapter adapter;
    int leftMargin;

	public ListViewAdapter(Activity activity, int resource, List<FriendInfor> objects) {
		super(activity, resource, objects);
		this.activity = activity;
	}

	@Override
	public int getViewTypeCount() {
		// return the total number of view types. this value should never change
		// at runtime
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		// return a value between 0 and (getViewTypeCount - 1)
		return position % 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
        Drawable image;

		// inflate layout from xml
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		int layoutResource = 0; // determined by view type
		int viewType = getItemViewType(position);
		switch (viewType) {
		case 0:
			layoutResource = R.layout.item_even_listview;
			break;

		case 1:
			layoutResource = R.layout.item_odd_listview;
			break;
		}

		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = inflater.inflate(layoutResource, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		// set data to views
		if (getItem(position).isGender()) {
			holder.gender.setImageResource(R.drawable.newimg);
		} else {
			holder.gender.setImageResource(R.drawable.newimg);
		}
		String img= getItem(position).getImg();
		System.out.println("image string 3pm"+img);
		holder.gender.setImageResource(R.drawable.logo);
//	     Picasso.with(activity)
//		.load(img)
//		.into(holder.gender);

	     Picasso.with(activity).load(img).transform(new CircleTransform()).into(holder.gender);


	//	new DownloadImageTask(holder.gender).execute(getItem(position).getImg());
		holder.name.setText(getItem(position).getTname());
//		holder.message.setText(getItem(position).getTestimonialmessage());
		holder.city.setText(getItem(position).getCity());
		holder.profession.setText(getItem(position).getProfession());


//        final ViewHolder finalHolder = holder;
//        Target target = new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                finalHolder.gender.setImageBitmap(bitmap);
//                Drawable image = finalHolder.gender.getDrawable();
//                leftMargin = image.getIntrinsicWidth() + 10;
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {}
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {}
//        };
//
//        Picasso.with(activity).load(img).into(target);


		// Get the icon and its width
//		Drawable DICON = holder.gender.getDrawable();
//        int leftMargin = DICON.getIntrinsicWidth() + 10;


//		holder.gender.setBackgroundDrawable (DICON) ;
//		SpannableString SS = new SpannableString (getItem(position).getTestimonialmessage());
//		//Expose the indent for the first three rows
//		SS.setSpan(new SpanString(3,leftMargin),0,SS.length(),0);
//        holder.message.setText ( SS ) ;


//		holder.webv.setText(getItem(position).getProfession());

		String text = "<html><body>"
		+ "<p align=\"justify\">"
		+ getItem(position).getTestimonialmessage()
		+ "</p> "
		+ "</body></html>";

		holder.webv.loadData(text, "text/html", "utf-8");
		holder.webv.setBackgroundColor(0x00000000);
		return convertView;
	}

	private class ViewHolder {
        private ImageView gender;
		private TextView name;
		private TextView message;
		private TextView city;
		private TextView profession;
		private WebView webv;
		
		public ViewHolder(View v) {
			gender = (ImageView) v.findViewById(R.id.gender_image);
			name = (TextView) v.findViewById(R.id.name);
			message = (TextView) v.findViewById(R.id.message);
			city = (TextView) v.findViewById(R.id.city);
			profession= (TextView) v.findViewById(R.id.profession);
			webv = (WebView) v.findViewById(R.id.webview);
		}
	}
	
//	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//		ImageView bmImage;
//
//		public DownloadImageTask(ImageView bmImage) {
//			this.bmImage = bmImage;
//		}
//
//		protected Bitmap doInBackground(String... urls) {
//			String urldisplay = urls[0];
//			Bitmap mIcon11 = null,resized = null;
//			try {
//				InputStream in = new java.net.URL(urldisplay).openStream();
//				mIcon11 = BitmapFactory.decodeStream(in);
//				 resized = Bitmap.createScaledBitmap(mIcon11,(int)(mIcon11.getWidth()*0.4), 
//						(int)(mIcon11.getHeight()*0.4), true);
//			} catch (Exception e) {
//				Log.e("Error", e.getMessage());
//				e.printStackTrace();
//			}
//			
//			return resized;
//		}
//
//		protected void onPostExecute(Bitmap result) {
//
////			adapter.notifyDataSetChanged();
//			bmImage.setImageBitmap(result);
//		}
//
//	}

	public class CircleTransform implements Transformation {
	    @Override
	    public Bitmap transform(Bitmap source) {
	        int size = Math.min(source.getWidth(), source.getHeight());

	        int x = (source.getWidth() - size) / 2;
	        int y = (source.getHeight() - size) / 2;

	        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
	        if (squaredBitmap != source) {
	            source.recycle();
	        }

	        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

	        Canvas canvas = new Canvas(bitmap);
	        Paint paint = new Paint();
	        BitmapShader shader = new BitmapShader(squaredBitmap,
	                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
	        paint.setShader(shader);
	        paint.setAntiAlias(true);

	        float r = size / 2f;
	        canvas.drawCircle(r, r, r, paint);

	        squaredBitmap.recycle();
	        return bitmap;
	    }

	    @Override
	    public String key() {
	        return "circle";
	    }
	}
	
}
