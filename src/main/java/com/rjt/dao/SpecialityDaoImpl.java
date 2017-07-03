package com.rjt.dao;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjt.model.Speciality;

@Service
public class SpecialityDaoImpl implements SpecialityDao{
	@Autowired
	SessionFactory session;

	@Transactional
	public void saveOrUpdate(Speciality o) {
		// TODO Auto-generated method stub
		session.getCurrentSession().saveOrUpdate(o);
	}
	@Transactional
	public void delete(String id) {
		Speciality spec=get(id);
		session.getCurrentSession().delete(spec);
	}
	@Transactional
	public List search(Speciality o) {
		// TODO Auto-generated method stub
		Map map=new LinkedHashMap();
		
		map.put("name", o.getName());
		Query query = session.getCurrentSession().getNamedQuery("speciality_search");
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
	public Speciality get(String id) {
		// TODO Auto-generated method stub
		return session.getCurrentSession().get(Speciality.class, Integer.parseInt(id));
	}
	@Transactional
	public Speciality getSpecialityByName(String name) {
		Query query=session.getCurrentSession().createQuery("FROM Speciality WHERE UPPER(name)= '"+name+"' ");
		List<Speciality> s=query.list();
		Speciality sp=s.get(0);
		return sp;
		
	}
}
