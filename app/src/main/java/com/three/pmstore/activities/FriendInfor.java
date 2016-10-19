package com.three.pmstore.activities;

import android.support.v7.app.AppCompatActivity;

public class FriendInfor extends AppCompatActivity{

	private String name;
	private boolean gender;
    String tname;
    String tgen;
    private String image;
	String testimonialmessage;
	String city;
	String profession;
    
	public String getTgen() {
		return tgen;
	}

	public void setTgen(String tgen) {
		this.tgen = tgen;
	}
	public String getTestimonialmessage() {
		return testimonialmessage;
	}

	public void setTestimonialmessage(String testimonialmessage) {
		this.testimonialmessage = testimonialmessage;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname, boolean gender) {
		this.tname = tname;
		this.gender = gender;
	}
	public String getImg() {
		return image;
	}
	public void setImg(String image) {
		this.image = image;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String Profession) {
		this.profession = Profession;
	}


	public FriendInfor(String tname, String testimonialmessage , String image, String city
			, String profession
			) {
		this.tname = tname;
		this.testimonialmessage = testimonialmessage;
		this.image = image;
		this.city = city;
		this.profession = profession;


	}
	
//	public String getName() {
//		return name;
//	}
	public boolean isGender() {
		return gender;
	}
	
}
