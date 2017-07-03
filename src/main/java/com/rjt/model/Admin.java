package com.rjt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="administrator_tbl")
public class Admin {
	@Id
	private String username;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=true)
	private String name;
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Admin){
			Admin o=(Admin)obj;
			
			return o.getUsername().equals(username) && o.getPassword().equals(password);
		}
		
		return false;
	}
	
	
	@Override
	public String toString() {
		return "Admin [username=" + username + ", password=" + password + ", name=" + name + "]";
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
