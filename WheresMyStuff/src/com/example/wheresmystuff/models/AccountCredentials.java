package com.example.wheresmystuff.models;

import java.util.HashMap;
import java.util.Map;



public class AccountCredentials {
	
	public static Map<String,Account> accounts = new HashMap<String,Account>();
	
	public AccountCredentials() {
		if(accounts.size() < 1) {
			accounts.put("tester@test.com",new Account("tester@test.com", "testing"));
		}
	}
	
	public boolean checkUserExists(String name) {
		if(accounts.containsKey(name)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkCredentials(String name, String password) {
		Account cur = accounts.get(name);
		if(cur.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void addAccount(Account newAccount) {
		accounts.put(newAccount.getUsername(), newAccount);
	}
	
	public void removeAccount(Account remove) {
		accounts.remove(remove.getUsername());
	}
	
	public Map<String, Account> getAccounts() {
		return accounts;
	}
	
	
}
