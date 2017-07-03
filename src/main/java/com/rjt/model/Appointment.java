package com.rjt.model;

import java.util.Date;
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
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="appointment_tbl")
@NamedQueries(value = { @NamedQuery(name = "appointment_search", 
	query = "FROM Appointment WHERE id=NVL(:id, id) AND "
	+ "dt>=NVL(TO_DATE(:min, 'rrrr-mm-dd'), dt) AND dt<=NVL(TO_DATE(:max, 'rrrr-mm-dd'), dt) "
	+ "AND doc_id=NVL(:doc_id, doc_id) AND pat_id=NVL(:pat_id, pat_id) ORDER BY dt DESC")})

@NamedNativeQueries(value = { @NamedNativeQuery(name = "appointment_browse", 
	query = "WITH temp AS (SELECT doctor.id doc_id, day.day, day.id day_id, day.begin_time, day.end_time "
	+ "FROM doctor_tbl doctor, doctor_day_tbl dd, day_tbl day "
	+ "WHERE doctor.id=dd.doc_id AND dd.day_id=day.id "
	+ "AND doctor.spe_id=NVL(:spe_id, doctor.spe_id) AND day.day=NVL(:day, day) AND day.id NOT IN "
	+ "(SELECT day_id FROM appointment_tbl WHERE dt=NVL(TO_DATE(:dt, 'rrrr-mm-dd'), dt)) "
	+ "AND doctor.id NOT IN (SELECT doc_id FROM leave_tbl WHERE approved=1 "
	+ "AND begin_date<=NVL(TO_DATE(:dt, 'rrrr-mm-dd'), begin_date) "
	+ "AND NVL(TO_DATE(:dt, 'rrrr-mm-dd'), end_date)<=end_date) ORDER BY doctor.id, day.day, day.begin_time) "
	+ "SELECT * FROM temp") })
public class Appointment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="doc_id", nullable=false)
	private Doctor doctor;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="pat_id", nullable=false)
	private Patient patient;
	
	@Column(nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dt;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="day_id", nullable=false)
	private Day day;

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
	 * @return the patient
	 */
	public Patient getPatient() {
		return patient;
	}

	/**
	 * @param patient the patient to set
	 */
	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	/**
	 * @return the review
	 */

	public Date getDt() {
		return dt;
	}

	/**
	 * @param dt the dt to set
	 */
	public void setDt(Date dt) {
		this.dt = dt;
	}

	/**
	 * @return the day
	 */
	public Day getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(Day day) {
		this.day = day;
	}
	
	
}
