package com.rjt.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="patient_tbl")
@NamedQueries(value = { @NamedQuery(name = "patient_search", 
	query = "FROM Patient WHERE id=NVL(:id, id) AND UPPER(name) LIKE '%' || UPPER(:name) || '%'")})
public class Patient implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=true)
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column(nullable=false)
	private char gender;
	
	@Column(nullable=false , unique=true)
	private String email;
	
	@Column(nullable=false)
	private String password;

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Patient){
			Patient o=(Patient)obj;
			
			return o.getId().equals(id) && o.getPassword().equals(password);
		}
		
		return false;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	/**
	 * @return the gender
	 */
	public char getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(char gender) {
		this.gender = gender;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", dob=" + dob + ", gender=" + gender + ", email=" + email
				+ ", password=" + password + "]";
	}
	
	
}
