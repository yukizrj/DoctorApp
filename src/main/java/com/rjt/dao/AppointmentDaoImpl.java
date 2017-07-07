package com.rjt.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.rjt.model.Admin;
import com.rjt.model.Appointment;
import com.rjt.model.Day;
import com.rjt.model.Doctor;
import com.rjt.model.Speciality;
import com.rjt.util.DateTransformer;

@Service
public class AppointmentDaoImpl implements AppointmentDao{
	@Autowired
	SessionFactory session;
	
	@Autowired
	SpecialityDao service_peciality;
	
	@Transactional
	public Appointment saveOrUpdate(Appointment o) {
		// TODO Auto-generated method stub
		String begin_time=session.getCurrentSession().get(Day.class, o.getDay().getId()).getBegin_time();
		String end_time=session.getCurrentSession().get(Day.class, o.getDay().getId()).getEnd_time();
		
		Query query = session.getCurrentSession().createQuery("FROM Appointment as app, Day as da "
				+ "WHERE app.day=da.id AND pat_id='" + o.getPatient().getId() + "' "
				+ "AND dt=TO_DATE('" + DateTransformer.getDateString(o.getDt()) + "', 'rrrr-mm-dd') "
				+ "AND '" + begin_time + "' = da.begin_time "
				+ "AND '" + end_time + "' = da.end_time");
		
		List list = query.list();
		List<Appointment> applist=list;
		
		if(list.size()==0){
			//template.saveOrUpdate(o);
			session.getCurrentSession().save(o);
			return o;	
		}
		else{
			return null;
		}
	}
	@Transactional
	public void delete(String id) {
		Appointment  app=get(id);
		session.getCurrentSession().delete(app);
	}
	@Transactional
	public List search(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map map=new LinkedHashMap();
		
		String id=request.getParameter("id");
		String min=request.getParameter("min");
		String max=request.getParameter("max");
		
		map.put("id", id);
		map.put("min", min);
		map.put("max", max);
		
		String privilege=(String)request.getSession().getAttribute("privilege");
		
		String doc_id="";
		String pat_id="";
		
		if(privilege.equals("doctor")){
			doc_id=request.getSession().getAttribute("id") + "";
		}
		else if(privilege.equals("patient")){
			pat_id=request.getSession().getAttribute("id") + "";
		}
		
		map.put("doc_id", doc_id);
		map.put("pat_id", pat_id);
		
		Query query = session.getCurrentSession().getNamedQuery("appointment_search");
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
	public Appointment get(String id) {
		// TODO Auto-generated method stub
		return session.getCurrentSession().get(Appointment.class, Integer.parseInt(id));
	}
/*	@Transactional
	public Object browse(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String spe_id=request.getParameter("spe_id");
		String dt=request.getParameter("dt");
		String day;
		
		if(dt!=null && !dt.equals("")){
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(DateTransformer.getDate(dt));
	        day=calendar.get(Calendar.DAY_OF_WEEK)-1 + "";
		}
		else{
			day="";
		}
		
		Map map=new LinkedHashMap();
		
		map.put("spe_id", spe_id==null?"":spe_id);
		map.put("day", day);
		map.put("dt", dt);
		
		Query query = session.getCurrentSession().getNamedQuery("appointment_browse");
		System.out.println(query);
		Set set=map.entrySet();
		
		Iterator iterator=set.iterator();
		
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry=(Map.Entry)iterator.next();
			
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<Object[]> list= query.list();
		Doctor doctor=null;
		List<Day> l=null;
		//Map<Doctor, List<Day>> m=new LinkedHashMap();
		Map<Integer, List<Day>> m=new LinkedHashMap();
		for(Object[] o:list){
			Integer doc_id=Integer.parseInt(o[0].toString());
			Integer docid = null;
			
			if(doctor==null || !doctor.getId().equals(doc_id)){
				doctor=session.getCurrentSession().get(Doctor.class, doc_id);
				docid=doctor.getId();
				l=new ArrayList();
				//m.put(doctor, l);
				m.put(docid, l);
			}
			else{
				//l=m.get(doctor);
				l=m.get(docid);
			}
			
			Day d=new Day();
			d.setDay(Integer.parseInt(o[1].toString()));
			d.setId(Integer.parseInt(o[2].toString()));
			d.setBegin_time( (String) o[3]);
			d.setEnd_time( (String) o[4]);
			
			l.add(d);
		}
		
		return m;
	}
	*/
	
	
	
	@Transactional
	public Object browse(HttpServletRequest request) {
		// TODO Auto-generated method stub
		//String spe_id=request.getParameter("spe_id");
		String spec=request.getParameter("spec");
		Speciality sepcobject=service_peciality.getSpecialityByName(spec);
		Integer spe_id=sepcobject.getId();
		String dt=request.getParameter("dt");
		String day;
		
		if(dt!=null && !dt.equals("")){
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(DateTransformer.getDate(dt));
	        day=calendar.get(Calendar.DAY_OF_WEEK)-1 + "";
		}
		else{
			day="";
		}
		
		Map map=new LinkedHashMap();
		
		map.put("spe_id", spe_id==null?"":spe_id);
		map.put("day", day);
		map.put("dt", dt);
		
		Query query = session.getCurrentSession().getNamedQuery("appointment_browse");
		Set set=map.entrySet();
		Iterator iterator=set.iterator();
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry=(Map.Entry)iterator.next();
			
			query.setParameter(entry.getKey(), entry.getValue());
		}

		List<Object[]> list= query.list();
		List<Map<String,String>> mlist = new ArrayList<Map<String,String>>();
		
		for(Object[] o:list){
			Integer doc_id=Integer.parseInt(o[0].toString());
			
				Doctor doctor=session.getCurrentSession().get(Doctor.class, doc_id);
				Map<String, String>mm=new HashMap<String, String>();
				mm.put("doc_id", o[0].toString());
				mm.put("doc_name", doctor.getName());
				mm.put("day", o[1].toString());
				mm.put("day_id", o[2].toString());
				mm.put("begin_time", (String) o[3]);
				mm.put("end_time", (String) o[4]);
				mlist.add(mm);
			
			
		}
		
		return mlist;
	}
	
	
	
	
	
	@Transactional
	public void saveOrUpdateReview(Appointment o) {
		// TODO Auto-generated method stub
		session.getCurrentSession().saveOrUpdate(o);
	}
	@Transactional
	public List getByPatient(String pat_id) {
		Integer patid=Integer.parseInt(pat_id);
		Query query=session.getCurrentSession().createQuery("FROM Appointment WHERE pat_id="+patid);
		List<Appointment> list=query.list();
		List<Map<String,String>> mlist=new ArrayList<Map<String, String>>();
		for(Appointment o:list){
				Map<String, String>mm=new HashMap<String, String>();
				mm.put("dt", o.getDt().toString());
				mm.put("day_begin", o.getDay().getBegin_time());
				mm.put("day_end", o.getDay().getEnd_time());
				mm.put("doc_name", o.getDoctor().getName());
				Speciality spec=o.getDoctor().getSpeciality();
				mm.put("spec", spec.getName());
				
				mlist.add(mm);
			
			
		}

		return mlist;
	}
	@Transactional
	public List getByDoctor(String doc_id) {
			Integer docid=Integer.parseInt(doc_id);
			Query query=session.getCurrentSession().createQuery("FROM Appointment WHERE doc_id="+docid);
			List<Appointment> list=query.list();
			List<Map<String,String>> mlist=new ArrayList<Map<String, String>>();
			for(Appointment o:list){
					Map<String, String>mm=new HashMap<String, String>();
					mm.put("dt", o.getDt().toString());
					mm.put("day_begin", o.getDay().getBegin_time());
					mm.put("day_end", o.getDay().getEnd_time());
					mm.put("pat_name", o.getPatient().getName());
					
					mlist.add(mm);
				
				
			}

			return mlist;
		}
	
	
	
}
