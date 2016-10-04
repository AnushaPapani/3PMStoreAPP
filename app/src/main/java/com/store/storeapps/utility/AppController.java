package com.store.storeapps.utility;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.store.storeapps.Volley.LruBitmapCache;


import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	LruBitmapCache mLruBitmapCache;
	String Coupon = "";
	String Orderid;
	String RegName;
	String RegEmailid;
	String name;
	String Proid;
	String Password;
	String Proname;
	String newprocode;
	String userid;
	String Cash;
	String AdminCod;
	String ProductCost;
	String qproduct;
	String emailid;
	String Gender;
	String Ugender;
	String Pgender;
	String USize;
	String baddress;
	String bmobile;
	String bcity;
	String bstate;
	String bpincode;
	String bemail;
	String uname;
	String uadres;
	String ucity;
	String ustate;
	String umobile;
	String uemail;
	String upincode;
	String dob;
	String popupmail;
	String FB_Email;
	String addressID;
	String spinnerID,spinnerDATA;
	int ReviewQuantity;
	int productrating,customerrating;
	String imagePath;
	int quantity;
	int storecash;
	String otp;
	int Scost;
	String newquantity;
	String PromoType;
	List<String> Tname = new LinkedList<String>();
	List<String> testimonial = new LinkedList<String>();
	List<String> img = new LinkedList<String>();
	List<String> Tcity = new LinkedList<String>();
	List<String> Tprofession = new LinkedList<String>();

	public String getPromoType() {
		return PromoType;
	}

	public void setPromoType(String promoType) {
		PromoType = promoType;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getReviewQuantity() {
		return ReviewQuantity;
	}

	public void setReviewQuantity(int reviewQuantity) {
		ReviewQuantity = reviewQuantity;
	}

	public String getNewquantity() {
		return newquantity;
	}

	public void setNewquantity(String newquantity) {
		this.newquantity = newquantity;
	}

	public String getFB_Email() {
		return FB_Email;
	}

	public void setFB_Email(String fB_Email) {
		FB_Email = fB_Email;
	}

	public List<String> getImg() {
		return img;
	}

	public void setImg(String timg) {
//		this.img = img;
		img.add(timg);
	}


	public List<String> getTestimonial() {
		return testimonial;
	}
	public void setTestimonial(String testimonialmsg) {
//		this.testimonial = testimonial;
		testimonial.add(testimonialmsg);
	}

	public List<String> getTname() {
		return Tname;
	}

	public void setTname(String testimonialname) {
		Tname.add(testimonialname);
	}
	public List<String> getTCity() {
		return Tcity;
	}
	public void setTCity(String testimonialcity) {
		Tcity.add(testimonialcity);
	}

	public List<String> getTProfession() {
		return Tprofession;
	}
	public void setTProfession(String profession) {
		
		Tprofession.add(profession);
	}
	
	

	public String getRegName() {
		return RegName;
	}

	public void setRegName(String regName) {
		RegName = regName;
	}

	public String getRegEmailid() {
		return RegEmailid;
	}

	public void setRegEmailid(String regEmailid) {
		RegEmailid = regEmailid;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getProductCost() {
		return ProductCost;
	}

	public void setProductCost(String productCost) {
		ProductCost = productCost;
	}

	public String getAdminCod() {
		return AdminCod;
	}

	public void setAdminCod(String adminCod) {
		AdminCod = adminCod;
	}

	public String getProid() {
		return Proid;
	}

	public void setProid(String proid) {
		Proid = proid;
	}

	public int getScost() {
		return Scost;
	}

	public void setScost(int scost) {
		Scost = scost;
	}

	public String getCoupon() {
		return Coupon;
	}

	public void setCoupon(String coupon) {
		Coupon = coupon;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getNewprocode() {
		return newprocode;
	}

	public void setNewprocode(String newprocode) {
		this.newprocode = newprocode;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getProname() {
		return Proname;
	}

	public void setProname(String proname) {
		Proname = proname;
	}
	
	public int getStoreCash() {
		return storecash;
	}

	public void setStoreCash(int storecash) {
		this.storecash = storecash;
	}

	public String getUpincode() {
		return upincode;
	}

	public void setUpincode(String upincode) {
		this.upincode = upincode;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUadres() {
		return uadres;
	}

	public void setUadres(String uadres) {
		this.uadres = uadres;
	}

	public String getUcity() {
		return ucity;
	}

	public void setUcity(String ucity) {
		this.ucity = ucity;
	}

	public String getUstate() {
		return ustate;
	}

	public void setUstate(String ustate) {
		this.ustate = ustate;
	}

	public String getUmobile() {
		return umobile;
	}

	public void setUmobile(String umobile) {
		this.umobile = umobile;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public String getQproduct() {
		return qproduct;
	}

	public void setQproduct(String qproduct) {
		this.qproduct = qproduct;
	}

	public String getBemail() {
		return bemail;
	}

	public void setBemail(String bemail) {
		this.bemail = bemail;
	}
	public String getdob() {
		return dob;
	}

	public void setdob(String dob) {
		this.dob = dob;
	}
	public String getPopupmail() {
		return popupmail;
	}

	public void setPopupmail(String pop) {
		this.popupmail = pop;
	}
//	public String getProductrating() {
//		return productrating;
//	}
//
//	public void setProductrating(String product) {
//		this.productrating = product;
//	}
	public int getCustomerrating() {
		return customerrating;
	}

	public void setCustomerrating(int customer) {
		this.customerrating = customer;
	}
	public int getProductrating() {
		return productrating;
	}

	public void setProductrating(int product) {
		this.productrating = product;
	}
	
	
	

	long fbid;
	String fuid;
	String fbname;
	String fbemail;
	String Amount;
	String fbgender;
	int cashused;
	String paytype;
	String otpgenerate;
	String finalcodamounttotal;
	long min;
	long sec;
	long hour;
	long h;
	
	public long geth() {
		return h;
	}

	public void seth(long h) {
		this.h = h;
	}

	
	public String getFinalcodamounttotal() {
		return finalcodamounttotal;
	}

	public void setFinalcodamounttotal(String finalcodamounttotal) {
		this.finalcodamounttotal = finalcodamounttotal;
	}
	
	public long getHour() {
		return hour;
	}

	public void setHour(long hour) {
		this.hour = hour;
	}
	
	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}
	
	public long getSec() {
		return sec;
	}

	public void setSec(long sec) {
		this.sec = sec;
	}
	public String getOtpgenerate() {
		return otpgenerate;
	}

	public void setOtpgenerate(String otpgenerate) {
		this.otpgenerate = otpgenerate;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public int getCashused() {
		return cashused;
	}

	public void setCashused(int cashused) {
		this.cashused = cashused;
	}

	private static Context context;

	//	@Override
	//	public void onCreate() {
	//		super.onCreate();
	//		context = getApplicationContext();
	//	}
	public String getCash() {
		return Cash;
	}

	public void setCash(String cash) {
		Cash = cash;
	}
	public static Context getAppContext(){
		return context;

	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		this.Amount = amount;
		
	}

	public String getBaddress() {
		return baddress;
	}

	public void setBaddress(String baddress) {
		this.baddress = baddress;
	}

	public String getBmobile() {
		return bmobile;
	}

	public void setBmobile(String bmobile) {
		this.bmobile = bmobile;
	}

	public String getBcity() {
		return bcity;
	}

	public void setBcity(String bcity) {
		this.bcity = bcity;
	}

	public String getBstate() {
		return bstate;
	}

	public void setBstate(String bstate) {
		this.bstate = bstate;
	}

	public String getBpincode() {
		return bpincode;
	}

	public void setBpincode(String bpincode) {
		this.bpincode = bpincode;
	}

	public String getFuid() {
		return fuid;
	}

	public void setFuid(String fuid) {
		this.fuid = fuid;
	}

	public String getUgender() {
		return Ugender;
	}
	public void setUgender(String ugender) {
		Ugender = ugender;
	}
	public String getPgender() {
		return Pgender;
	}
	public void setPgender(String pgender) {
		Pgender = pgender;
	}
	public String getUSize() {
		return USize;
	}
	public void setUSize(String uSize) {
		USize = uSize;
	}
	public String getOrderid() {
		return Orderid;
	}
	public void setOrderid(String orderid) {
		Orderid = orderid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public long getFbid() {
		return fbid;
	}

	public void setFbid(long fbid) {
		this.fbid = fbid;
	}

	public String getFbname() {
		return fbname;
	}

	public void setFbname(String fbname) {
		this.fbname = fbname;
	}

	public String getFbemail() {
		return fbemail;
	}

	public void setFbemail(String fbemail) {
		this.fbemail = fbemail;
	}

	public String getFbgender() {
		return fbgender;
	}

	public void setFbgender(String fbgender) {
		this.fbgender = fbgender;
	}
	public String getAddressID() {
		return addressID;
	}
	public void setAddressID(String addressID) {
		this.addressID = addressID;
	}
	public String getSpinnerID() {
		return spinnerID;
	}
	public void setSpinnerID(String ID) {
		this.spinnerID = ID;
	}
	public String getSpinnerData() {
		return spinnerDATA;
	}
	public void setSpinnerData(String data) {
		this.spinnerDATA = data;
	}
	
	
	

	public  MyApplication getInstance2()
	{
		return (MyApplication) context;
	}

	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		context = getApplicationContext();
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			getLruBitmapCache();
			mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
		}

		return this.mImageLoader;
	}

	public LruBitmapCache getLruBitmapCache() {
		if (mLruBitmapCache == null)
			mLruBitmapCache = new LruBitmapCache();

		return this.mLruBitmapCache;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}
	class MyApplication extends Application {

		private Context context;


		@Override
		public void onCreate() {
			super.onCreate();
			context = getApplicationContext();

		}

		public Context getAppContext(){
			return context;

		}
		public  MyApplication getInstance()
		{
			return (MyApplication) context;
		}

	}

	
	
	public void clearApplicationData() {
		File cache = getCacheDir();
		File appDir = new File(cache.getParent());
		if(appDir.exists()){
			String[] children = appDir.list();
			for(String s : children){
				if(!s.equals("lib")){
					deleteDir(new File(appDir, s));
					Log.i("TAG", "File /data/data/APP_PACKAGE/" + s +" DELETED");
				}
			}
		}
	}
	
	public static boolean deleteDir(File dir) {
	    if (dir != null && dir.isDirectory()) {
	        String[] children = dir.list();
	        for (int i = 0; i < children.length; i++) {
	            boolean success = deleteDir(new File(dir, children[i]));
	            if (!success) {
	                return false;
	            }
	        }
	    }

	    return dir.delete();
	}

	
	
	

}
