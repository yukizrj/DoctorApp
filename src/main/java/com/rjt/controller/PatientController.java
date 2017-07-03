package com.rjt.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rjt.dao.DoctorDao;
import com.rjt.dao.PatientDao;
import com.rjt.model.Admin;
import com.rjt.model.Appointment;
import com.rjt.model.Doctor;
import com.rjt.model.Patient;
import com.rjt.service.DoctorSvc;
import com.rjt.service.PatientSvc;

@RestController
public class PatientController {
	@Autowired
	PatientDao service;
	
	@Autowired
	DoctorDao doc_svc;
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));
    }
	
	@RequestMapping(value="/patient", method=RequestMethod.GET)
	public ModelAndView patient(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("patient");
		
		return mv;
	}
	
	@RequestMapping(value="/patient-login", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public String login(HttpServletRequest request){
		Patient p=service.getByEmail(request.getParameter("email"));
		if(p!=null){
			Patient o=service.login(p);
			if(o!=null){
				return o.getId().toString();
			}
		}
		return "0";	
	}
	
	@RequestMapping(value="/patient-update", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public String update(HttpServletRequest request){
		try{
		Patient p=service.get(request.getParameter("pat_id"));
		p.setPassword(request.getParameter("pwd"));
		service.save(p);
		return "1";
		}catch(Exception e){
			return "0";
		}
	}
	
	
	@RequestMapping(value="/patient-search-form", method=RequestMethod.GET)
	public ModelAndView search_form(){
		ModelAndView mv=new ModelAndView();
		
		mv.addObject("o", new Patient());
		
		mv.setViewName("patient-search-form");
		
		return mv;
	}
	
	@RequestMapping(value="/patient-save-form/{id}", method=RequestMethod.GET)
	public ModelAndView save_form(@PathVariable("id") String id){
		ModelAndView mv=new ModelAndView();
		
		Patient o;
		
		if(id.equals("add")){
			o=new Patient();
			o.setGender('m');
		}
		else{
			o=service.get(id);
		}
		
		mv.addObject("o", o);
		
		mv.setViewName("patient-save-form");
		
		return mv;
	}
	
	
	
	/*@RequestMapping(value="/doctor-schedule-form/{id}", method=RequestMethod.GET)
	public ModelAndView schedule_form(@PathVariable("id") String id){
		ModelAndView mv=new ModelAndView();
		
		Doctor o=service.get(id);
		
		Map<Integer, Day> day=o.getDay();
		
		Set set=day.keySet();
		
		Iterator iterator=set.iterator();
		
		Map<Integer, String> map=new LinkedHashMap();
		
		while(iterator.hasNext()){
			int key=(Integer)iterator.next();
			
			map.put(day.get(key).getDay(), DateTime.getTimeString(day.get(key).getBegin_time()));
		}
		
		mv.addObject("o", o);
		mv.addObject("map", map);
		
		mv.setViewName("doctor-schedule-form");
		
		return mv;
	}*/
	
	/*@RequestMapping(value="/patient-search", method=RequestMethod.POST)
	//@CrossOrigin(origins="http://localhost:4200")
	public @ResponseBody Object search(HttpServletRequest request) {
			Patient p=new Patient();
			p.setName(request.getParameter("name"));
			if(request.getParameter("d")!=null){
				p.setId(Integer.parseInt(request.getParameter("d")));
			}
			return service.search(p);
	}*/
	 @Transactional
	@RequestMapping(value="/patient-search", method=RequestMethod.POST)
	//@CrossOrigin(origins="http://localhost:4200")
	public String search(@RequestBody Patient body){
		 
		return service.search(body).toString();
		
	}
	
	@RequestMapping(value="/patient-delete", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public String delete(HttpServletRequest request) {
		
			Patient p=service.get(request.getParameter("id"));
			p.setPassword(request.getParameter("pwd"));
			Patient varify=service.login(p);
			if(varify!=null){
				service.delete(request.getParameter("id"));
				return "1";
			}
			else{
				return "0";
			}
			
	}
	
	/*@RequestMapping(value="/doctor-schedule-save/{id}", method=RequestMethod.POST)
	public @ResponseBody String schedule_save(@PathVariable("id") String id, HttpServletRequest request){
		ModelAndView mv=new ModelAndView();
		String[] schedule=request.getParameterValues("schedule");
		
		service.schedule(id, schedule);
		
		return null;
	}*/
	
	@RequestMapping(value="/patient-save", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public String save(HttpServletRequest request) {
		
				Patient p=new Patient();
				 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				 Date dat = null;
				try {
					dat = format.parse(request.getParameter("dob"));
				} catch (ParseException e) {
					return "date error";
				}
				try{
				p.setDob(dat);
				p.setEmail(request.getParameter("email"));
				char[] c= request.getParameter("gender").toCharArray();
				p.setGender(c[0]);
				p.setName(request.getParameter("name"));
				p.setPassword(request.getParameter("pwd"));
				service.saveOrUpdate(p);
				}catch(Exception e){
					return "0";
				}
				return "1";	
	}
	
	@RequestMapping(value="/displaytable", method=RequestMethod.GET)
	public ModelAndView displaytable(){
		ModelAndView mv=new ModelAndView();
		
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -5);
        cal.setTime(cal.getTime());
        Date nowmin5 = cal.getTime();
		
        Doctor doctor=doc_svc.get("146");
		
        List<Patient> list=(List<Patient>)service.displaytable(nowmin5, doctor);
        
		/*Appointment a=(Appointment)service.displaytable(nowmin5, doctor);
		
		//for(Appointment a:list){
			System.out.println("doctor: " + a.getDoctor().getId());
			System.out.println("patient: " + a.getPatient().getName());
			System.out.println("date: " + a.getDt());
			System.out.println("------------------------------------------------");
		//}*/
		
		mv.addObject("list", list);
		
		mv.setViewName("displaytable");
		
		return mv;
	}
}
