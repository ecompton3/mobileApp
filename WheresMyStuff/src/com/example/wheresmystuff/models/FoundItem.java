package com.example.wheresmystuff.models;

public class FoundItem extends Item{

	public FoundItem(String name, String owner, String description, String category, String city,
			String state, int reward, int month, int day, int year) {
		super(name, owner, description, category, city, state, reward, month, day, year);
		// TODO Auto-generated constructor stub
		type = "found";
	}

}
