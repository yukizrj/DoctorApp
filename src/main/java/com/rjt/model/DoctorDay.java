package com.rjt.model;

import javax.persistence.*;

@Entity
@Table(name="doctor_day_tbl")
public class DoctorDay {
	@Column
	private Integer doc_id;
	@Column
	private Integer day_id;
	@Id
	private Integer ref;
	public Integer getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(Integer doc_id) {
		this.doc_id = doc_id;
	}
	public Integer getDay_id() {
		return day_id;
	}
	public void setDay_id(Integer day_id) {
		this.day_id = day_id;
	}
	public Integer getRef() {
		return ref;
	}
	public void setRef(Integer ref) {
		this.ref = ref;
	}
	
	
	
}
