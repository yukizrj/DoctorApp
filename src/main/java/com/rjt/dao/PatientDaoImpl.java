package com.rjt.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjt.model.Appointment;
import com.rjt.model.Doctor;
import com.rjt.model.Patient;

@Service
public class PatientDaoImpl implements PatientDao{
	@Autowired
	SessionFactory session;
	@Transactional
	public Patient login(Patient o) {
		// TODO Auto-generated method stub
		Patient temp=session.getCurrentSession().get(Patient.class, o.getId());
		
		if(temp!=null && temp.equals(o)){
			return temp;
		}
		
		return null;
	}
	@Transactional
	public void saveOrUpdate(Patient o) {
		// TODO Auto-generated method stub
		session.getCurrentSession().saveOrUpdate(o);
	}
	@Transactional
	public void delete(String id) {
		Patient p=get(id);
		session.getCurrentSession().delete(p);
	}
	@Transactional
	public List search(Patient oo) {
		// TODO Auto-generated method stub
		Map map=new LinkedHashMap();
		
		map.put("id", oo.getId()==null?"":oo.getId());
		map.put("name", oo.getName());
		Query query = session.getCurrentSession().getNamedQuery("patient_search");
					System.out.println(query);
					Set set=map.entrySet();
					
					Iterator iterator=set.iterator();
					
					while(iterator.hasNext()){
						Map.Entry<String, Object> entry=(Map.Entry)iterator.next();
						
						query.setParameter(entry.getKey(), entry.getValue());
					}
		
		return query.list();
	}
	@Transactional
	public Patient get(String id) {
		// TODO Auto-generated method stub
		return session.getCurrentSession().get(Patient.class, Integer.parseInt(id));
	}
	@Transactional
	public Patient save(Patient o) {
		session.getCurrentSession().saveOrUpdate(o);;
		return o;
	}
	

	/*public void schedule(String id, String[] schedule) {
		// TODO Auto-generated method stub
		Doctor o=get(id);
		
		jdbc.execute("DELETE FROM doctor_day_tbl WHERE doc_id=" + o.getId());
		
		Map<Integer, Day> day=o.getDay();
		
		Set set=day.keySet();
		
		Iterator iterator=set.iterator();
		
		while(iterator.hasNext()){
			int temp=day.get(iterator.next()).getId();
			jdbc.execute("DELETE FROM day_tbl WHERE id=" + temp);
		}
		
		day=new LinkedHashMap();
		
		for(String s:schedule){
			String[] temp=s.split("-");
			
			Day d=new Day();
			d.setDay(Integer.parseInt(temp[0]));
			d.setBegin_time(DateTime.getTime(temp[1]));
			d.setEnd_time(DateTime.getTime(temp[1]));
			
			d=template.get(Day.class, template.save(d));
			
			day.put(d.getId(), d);
		}
		
		o.setDay(day);
		
		template.saveOrUpdate(o);
	}*/
	@Transactional
	public Object displaytable(Date dt, Doctor doctor) {
		
		List<Patient> pList = new ArrayList<Patient>();
		Criteria criteria = session.getCurrentSession().createCriteria(Patient.class);
		pList = criteria.list();
		return pList;
		
		
	}
	@Transactional
	public List<Appointment> listOfPatientByDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		
		List<Appointment> applist= new ArrayList<Appointment>();
		Criteria c=session.getCurrentSession().createCriteria(Appointment.class).add(Restrictions.eqOrIsNull("doctor", doctor));
		applist = c.list();
		return applist;
	}
	@Transactional
	public Patient getByEmail(String email) {
		Query query=session.getCurrentSession().createQuery("FROM Patient WHERE email= '"+email+"'");
		List<Patient> d=query.list();
		if(d.size()!=0){
		Patient o=d.get(0);
		return o;
		}
		return null;
	}
}
