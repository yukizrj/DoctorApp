package com.rjt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="leave_tbl")
@NamedQueries(value = { @NamedQuery(name = "leave_search", 
	query = "FROM Leave WHERE id=NVL(:id, id) AND doc_id=NVL(:doc_id, doc_id)") })
public class Leave {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="doc_id", nullable=false)
	private Doctor doctor;
	
	@Column(nullable=false)
	private Date begin_date;
	
	@Column(nullable=false)
	private Date end_date;
	
	@Column(nullable=true)
	private Integer approved;

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
	 * @return the doctor
	 */
	public Doctor getDoctor() {
		return doctor;
	}

	/**
	 * @param doctor the doctor to set
	 */
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	/**
	 * @return the begin_date
	 */
	public Date getBegin_date() {
		return begin_date;
	}

	/**
	 * @param begin_date the begin_date to set
	 */
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}

	/**
	 * @return the end_date
	 */
	public Date getEnd_date() {
		return end_date;
	}

	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	/**
	 * @return the approved
	 */
	public Integer getApproved() {
		return approved;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Integer approved) {
		this.approved = approved;
	}
}
