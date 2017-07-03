package com.rjt.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="doctor_tbl")
@NamedQueries(value = { @NamedQuery(name = "doctor_search", 
	query = "FROM Doctor WHERE license_no=NVL(:license_no, license_no) AND UPPER(name) LIKE '%' || UPPER(:name) || '%'"
	+ " AND spe_id=NVL(:spe_id, spe_id)") })
public class Doctor implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable=false, unique=true)
	private String license_no;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=true)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="spe_id", nullable=false)
	private Speciality speciality;
	
	//private String image;
	
	@ElementCollection(fetch=FetchType.EAGER)
    @JoinTable(name = "doctor_day_tbl", joinColumns = @JoinColumn(name = "doc_id"))
    @MapKeyColumn(name="ref", nullable=false)
	@Column(name = "day_id", nullable=false)
	@OrderBy("day, begin_time")
	private Map<Integer, Day> day=new LinkedHashMap();

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Doctor){
			Doctor o=(Doctor)obj;
			
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
	 * @return the license_no
	 */
	public String getLicense_no() {
		return license_no;
	}

	/**
	 * @param license_no the license_no to set
	 */
	public void setLicense_no(String license_no) {
		this.license_no = license_no;
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

	/**
	 * @return the speciality
	 */
	public Speciality getSpeciality() {
		return speciality;
	}

	/**
	 * @param speciality the speciality to set
	 */
	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	/**
	 * @return the day
	 */
	public Map<Integer, Day> getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(Map<Integer, Day> day) {
		this.day = day;
	}
}
