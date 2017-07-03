package com.rjt.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rjt.dao.AppointmentDao;
import com.rjt.dao.DoctorDao;
import com.rjt.dao.SpecialityDao;
import com.rjt.model.Admin;
import com.rjt.model.Day;
import com.rjt.model.Doctor;
import com.rjt.model.Patient;
import com.rjt.model.Speciality;
import com.rjt.service.DoctorSvc;
import com.rjt.service.SpecialitySvc;
import com.rjt.util.DateTransformer;

@RestController
public class DoctorController {
	@Autowired
	DoctorDao service;
	
	@Autowired
	SpecialityDao service_speciality;
	
	@Autowired
	AppointmentDao service_appointment;
	

	
	@RequestMapping(value="/doctor", method=RequestMethod.GET)
	public ModelAndView doctor(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("doctor");
		
		return mv;
	}
	
	@RequestMapping(value="/doctor-login", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public String login(HttpServletRequest request){
		Doctor o=service.getByLicense(request.getParameter("license"));
		o.setPassword(request.getParameter("pwd"));
		if(o!=null){
			Doctor oo=service.login(o);
			if(oo!=null){
				return oo.getId().toString();
			}
		}
		return "0";	
	}
	
	@RequestMapping(value="/doctor-search-form", method=RequestMethod.GET)
	public ModelAndView search_form(){
		ModelAndView mv=new ModelAndView();
		
		List<Speciality> speciality=service_speciality.search(new Speciality());
		
		mv.addObject("o", new Doctor());
		mv.addObject("speciality", speciality);
		
		mv.setViewName("doctor-search-form");
		
		return mv;
	}
	
	@RequestMapping(value="/doctor-save-form/{id}", method=RequestMethod.GET)
	public ModelAndView save_form(@PathVariable("id") String id){
		ModelAndView mv=new ModelAndView();
		
		Doctor o;
		
		if(id.equals("add")){
			o=new Doctor();
		}
		else{
			o=service.get(id);
		}
		
		List<Speciality> speciality=service_speciality.search(new Speciality());
		
		mv.addObject("o", o);
		mv.addObject("speciality", speciality);
		
		mv.setViewName("doctor-save-form");
		
		return mv;
	}
	
	@RequestMapping(value="/doctor-schedule-form/{id}", method=RequestMethod.GET)
	public String schedule_form(@PathVariable("id") String id){
		
		Doctor o=service.getByLicense(id);
		
		Map<Integer, Day> day=o.getDay();
		
		Set set=day.keySet();
		
		Iterator iterator=set.iterator();
		
		Map<Integer, List> map=new LinkedHashMap();
		
		while(iterator.hasNext()){
			int key=(Integer)iterator.next();
			
			List<String> list=map.get(day.get(key).getDay());
			
			if(list==null){
				list=new ArrayList();
			}
			
			list.add(day.get(key).getBegin_time());
			
			map.put(day.get(key).getDay(), list);
		}
		
		
		return null;
	}
	
	@RequestMapping(value="/doctor-search", method=RequestMethod.POST)
	public @ResponseBody Object search(HttpServletRequest request) {
		try{
			Doctor o=new Doctor();
			o.setLicense_no(request.getParameter("license_no"));
			o.setName(request.getParameter("name"));
			if(request.getParameter("spc_id")!=null){
				o.setSpeciality(service_speciality.get(request.getParameter("spc_id")));
			}
			return service.search(o);
		}catch(Exception e){
			return e.getMessage()+"error";
		}
		
	}
	
	@RequestMapping(value="/doctor-search-license", method=RequestMethod.GET)
	@CrossOrigin(origins="http://localhost:3000")
	public Map<String, String> searchByLicense(HttpServletRequest request) {
		List list=new ArrayList<String>();
		Map<String, String> map=new HashMap<String,String>();
			Doctor o=service.getByLicense(request.getParameter("license"));
			if(o!=null){
				map.put("doc_id", o.getId().toString());
				map.put("doc_email", o.getEmail());
				map.put("doc_name", o.getName());
				map.put("doc_spe", o.getSpeciality().getName());
				map.put("doc_pwd", o.getPassword());
				map.put("doc_license", o.getLicense_no());
				/*list.add(o.getId().toString());
				list.add(o.getEmail());
				list.add(o.getName());
				list.add(o.getSpeciality().getName());*/
			}
			return map;	
	}
	
	@RequestMapping(value="/doctor-search-name", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public List searchName(HttpServletRequest request) {
		try{
			Doctor o=new Doctor();
			o.setName(request.getParameter("name"));
			return service.searchByName(o);
		}catch(Exception e){
			return null;
		}
		
	}
	
	@RequestMapping(value="/doctor-delete/{id}", method=RequestMethod.GET)
	public @ResponseBody String delete(@PathVariable("id") String id) {
		try{
			service.delete(id);
			return "1";
		}
		catch(Exception e){
			
			return e.getMessage();
		}
	}
	
	@RequestMapping(value="/doctor-schedule-save/{id}", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public String schedule_save(@PathVariable("id") String id, HttpServletRequest request){
		try{
			String schedule=request.getParameter("schedule");
			
			Map<Integer, Day> day=service.schedule(id, schedule);
			System.out.println(day.toString());
			return "1";
		}
		catch (Exception e) {
			// TODO: handle exception
			/*return "0";*/
			return "0";
		}
	}
	
	@RequestMapping(value="/doctor-save", method=RequestMethod.POST)
	@CrossOrigin(origins= "http://localhost:3000" )
	public String save(HttpServletRequest request) {
		try{
			
			Doctor o=new Doctor();
			o.setLicense_no(request.getParameter("license"));
			o.setName(request.getParameter("name"));
			o.setPassword(request.getParameter("pwd"));
			o.setEmail(request.getParameter("email"));
			Speciality sp=service_speciality.getSpecialityByName(request.getParameter("spe_id"));
			//Integer id=sp.getId();
			o.setSpeciality(sp);
			service.saveOrUpdate(o);
			return "1";
		}
		catch(Exception e){
			return "0";
		}
	}
	@RequestMapping(value="/doctor-edit", method=RequestMethod.POST)
	@CrossOrigin(origins= "http://localhost:3000" )
	public String edit(HttpServletRequest request) {
		try{
			
			Doctor o=new Doctor();
			o.setId(Integer.parseInt(request.getParameter("id")));
			o.setName(request.getParameter("name"));
			o.setEmail(request.getParameter("email"));
			Speciality sp=service_speciality.getSpecialityByName(request.getParameter("spe_id"));
			//Integer id=sp.getId();
			o.setSpeciality(sp);
			o.setLicense_no(request.getParameter("license"));
			o.setPassword(request.getParameter("pwd"));
			service.saveOrUpdate(o);
			return "1";
		}
		catch(Exception e){
			return "0";
		}
		
	}
	
	@RequestMapping(value="/doctor-update", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public String update(HttpServletRequest request){
		try{
			Doctor d=service.get(request.getParameter("doc_id"));
		d.setPassword(request.getParameter("pwd"));
		service.saveOrUpdate(d);
		return "1";
		}catch(Exception e){
			return "0";
		}
	}
	
	@RequestMapping(value="/appointment-view-doctor", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public List<Map<String, String>> viewApp(HttpServletRequest request) {
		List mlist=new ArrayList<Map<String, String>>();
		mlist=service_appointment.getByDoctor((request.getParameter("doc_id").toString()));
			return mlist;	
	}
}
