package com.rjt.dao;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.rjt.model.Day;
import com.rjt.model.Doctor;
import com.rjt.model.Speciality;
import com.rjt.util.DateTransformer;

@Service
public class DoctorDaoImpl implements DoctorDao{
	@Autowired
	SessionFactory session;
	@Transactional
	public Doctor login(Doctor o) {
		// TODO Auto-generated method stub
		Doctor temp=session.getCurrentSession().get(Doctor.class, o.getId());
		
		if(temp!=null && temp.equals(o)){
			return temp;
		}
		
		return null;
	}
	@Transactional
	public void saveOrUpdate(Doctor o) {
		// TODO Auto-generated method stub
		session.getCurrentSession().saveOrUpdate(o);
	}
	@Transactional
	public void delete(String id) {
		Doctor d=get(id);
		session.getCurrentSession().delete(d);
	}
	@Transactional
	public List search(Doctor o) {
		// TODO Auto-generated method stub
		Map map=new LinkedHashMap();
		
		map.put("license_no", o.getLicense_no());
		map.put("name", o.getName());
		map.put("spe_id", o.getSpeciality().getId()==null?"":o.getSpeciality().getId());
		
		Query query = session.getCurrentSession().getNamedQuery("doctor_search");
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
	public List searchByName(Doctor o) {
		String name=o.getName();
		Query query=session.getCurrentSession().createQuery("FROM Doctor WHERE name= '"+name+"' ");
		List<Doctor> s=query.list();

		return s;
	}
	@Transactional
	public Doctor getByLicense(String license) {
		Query query=session.getCurrentSession().createQuery("FROM Doctor WHERE license_no= '"+license+"'");
		List<Doctor> d=query.list();
		Doctor o=d.get(0);
		return o;
	}
	
	@Transactional
	public Doctor get(String id) {
		// TODO Auto-generated method stub
		return session.getCurrentSession().get(Doctor.class, Integer.parseInt(id));
	}
	
	@Transactional
	public void doctordaytbl(Doctor o){
	
	Query query= session.getCurrentSession().createQuery("DELETE FROM DoctorDay WHERE doc_id=" + o.getId());
		int result = query.executeUpdate();
		
	}
	
	@Transactional
	public void deleteday(Map<Integer, Day> day){
		Set set=day.keySet();
		
		Iterator iterator=set.iterator();
		
		while(iterator.hasNext()){
			int temp=day.get(iterator.next()).getId();
			
			Query query2=session.getCurrentSession().createQuery("DELETE FROM Day WHERE id=" + temp);
			int result2 = query2.executeUpdate();
			
			System.out.println("result2"+result2);
		}
	}
	
	@Transactional
	public Map<Integer, Day> schedule(String id, String schedule) {
		// TODO Auto-generated method stub
		Doctor o=getByLicense(id);
		doctordaytbl(o);
		Map<Integer, Day> day=o.getDay();
		deleteday(day);
		day=new LinkedHashMap();
		String[] sche=schedule.split(",");
		
		for(int i=0;i<sche.length;i++){
			String s=sche[i];
			String[] temp=s.split("-");
			Integer begin=Integer.parseInt(temp[1]);
			Integer end=Integer.parseInt(temp[2]);
			while(end>begin){
				Day d=new Day();
				d.setDay(Integer.parseInt(temp[0]));
				d.setBegin_time(begin.toString());
				Integer bend=begin+1;
				d.setEnd_time(bend.toString());
				session.getCurrentSession().save(d);
				day.put(d.getId(), d);
				begin++;
			}
			/*Day d=new Day();
			d.setDay(Integer.parseInt(temp[0]));
			d.setBegin_time(temp[1]);
			d.setEnd_time(temp[2]);
			session.getCurrentSession().save(d);
			
			day.put(d.getId(), d);
			System.out.println(day.size());*/
		}
		
		o.setDay(day);
		
		session.getCurrentSession().saveOrUpdate(o);
		return o.getDay();
	}
}
