package com.example.wheresmystuff.models;

/**
 * 
 * @author Evan
 *Name
Description
Location (either the place lost from or the place it was found)
Status either it is open (still not matched up) or resolved (the finder and loser have matched the item)
Category (A way to group things to filter searches). It is required to support the following categories: Keepsakes (things like family photo albums), Heirlooms (jewelry and items belonging to the family for awhile), Misc (miscellaneous items not fitting into another category).
Reward either none, or some monetary amount of the loser is willing to pay to get their stuff back.
Type lost - this is a lost item being looked for, found - this is a found item that someone may be looking for.
Date Entered - the date this item was first created in the system
 */



public class Item {
	
	protected String name, description, city, state, category, type, owner;
	protected int reward, month, day, year;
	protected boolean status;
	
	public Item(String name, String owner, String description, String category, String city, String state, int reward, int month, int day, int year) {
		this.name = name;
		this.owner = owner;
		this.description = description;
		this.city = city;
		this.state = state;
		this.reward = reward;
		this.month = month;
		this.day = day;
		this.year = year;
		this.category = category;
		status = false; //not resolved by default
		
		
	}
	
	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	
}
