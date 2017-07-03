package com.rjt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="speciality_tbl")
@NamedQueries(value = { @NamedQuery(name = "speciality_search", 
	query = "FROM Speciality WHERE UPPER(name) LIKE '%' || UPPER(:name) || '%' ORDER BY name") })
public class Speciality {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@NotEmpty(message="Name can''t be empty.")
	@Column(nullable=false, unique=true)
	private String name;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getId().toString();
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
}
