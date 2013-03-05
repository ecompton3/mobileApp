package com.example.wheresmystuff.models;

import java.util.ArrayList;

public class Account {
	private String username, password;
	private boolean locked;
	private int loginAttempts;
	private ArrayList<Item> lostItems, foundItems;

	public Account(String name, String pass) {
		username = name;
		password = pass;
		locked = false;
		setLoginAttempts(0);
	}
	
	public void setUsername(String name) {
		username = name;
	}
	
	public void setPassword(String pass) {
		password = pass;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(int loginAttempts) {
		this.loginAttempts = loginAttempts;
	}
	
	public void failedAttempt() {
		loginAttempts +=1;
		if(loginAttempts >= 3) {
			locked = true;
		}
	}
	
	public void unlock() {
		loginAttempts = 0;
		locked = false;
	}

	public ArrayList<Item> getLostItems() {
		return lostItems;
	}

	public void setLostItems(ArrayList<Item> lostItems) {
		this.lostItems = lostItems;
	}
	
	public void addLostItem(Item item) {
		lostItems.add(item);
	}

	public ArrayList<Item> getFoundItems() {
		return foundItems;
	}

	public void setFoundItems(ArrayList<Item> foundItems) {
		this.foundItems = foundItems;
	}
	
	public void addFoundItem(Item item) {
		foundItems.add(item);
	}
}
