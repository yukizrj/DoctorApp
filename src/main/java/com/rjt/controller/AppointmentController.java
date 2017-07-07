package com.rjt.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rjt.dao.AppointmentDao;
import com.rjt.dao.DayDao;
import com.rjt.dao.DoctorDao;
import com.rjt.dao.PatientDao;
import com.rjt.dao.SpecialityDao;
import com.rjt.model.Appointment;
import com.rjt.model.Day;
import com.rjt.model.Doctor;
import com.rjt.model.Speciality;
import com.rjt.service.AppointmentSvc;
import com.rjt.service.PatientSvc;
import com.rjt.service.SpecialitySvc;
import com.rjt.util.DateTransformer;


@RestController
public class AppointmentController {
	@Autowired
	AppointmentDao service;
	
	@Autowired
	PatientDao service_patient;
	
	@Autowired
	DoctorDao service_doctor;
	
	@Autowired
	DayDao service_day;
	
	@Autowired
	SpecialityDao service_speciality;
	
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));
    }
	


	/*@RequestMapping(value="/appointment-browse", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public Map<String, Object> browse(HttpServletRequest request) {
		String spec=request.getParameter("spec");
		Speciality sepcobject=service_speciality.getSpecialityByName(spec);
		Integer spe_id=sepcobject.getId();
		request.setAttribute("spe_id", spe_id);
		Map<Integer, List<Day>> map=(Map)service.browse(request);
		
		Appointment o=new Appointment();
		o.setPatient(service_patient.get(request.getParameter("id")));
		o.setDt(DateTransformer.getDate(request.getParameter("dt")));
		
		Map<String, Object> objmap=new HashMap<String, Object>();
		objmap.put("o", o);
		objmap.put("map", map);
		objmap.put("count", map.size());
		
		return objmap;
	}*/
	
	
	
	@RequestMapping(value="/appointment-browse", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public List<Map<String,String>> browse(HttpServletRequest request) {
		String spec=request.getParameter("spec");
		Speciality sepcobject=service_speciality.getSpecialityByName(spec);
		Integer spe_id=sepcobject.getId();
		request.setAttribute("spe_id", spe_id);
		List<Map<String,String>> mlist=(List<Map<String, String>>) service.browse(request);
		
		return mlist;
	}
	
	
	@RequestMapping(value="/appointment-delete/{id}", method=RequestMethod.GET)
	@Transactional
	public @ResponseBody String delete(@PathVariable("id") String id) {
		try{
			Appointment o=service.get(id);
			
			Appointment app=new Appointment();
			app.setId(o.getId());
			app.setDt(o.getDt());
			app.setDay(o.getDay());
			app.setDoctor(o.getDoctor());
			app.setPatient(o.getPatient());
			
			service.delete(id);
			
			
			return "1";
		}
		catch(Exception e){

			return e.getMessage();
		}
	}
	
	/*@RequestMapping(value="/doctor-schedule-save/{id}", method=RequestMethod.POST)
	public @ResponseBody String schedule_save(@PathVariable("id") String id, HttpServletRequest request){
		ModelAndView mv=new ModelAndView();
		String[] schedule=request.getParameterValues("schedule");
		
		service.schedule(id, schedule);
		
		return null;
	}*/
	
	@RequestMapping(value="/appointment-save", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public String save(HttpServletRequest request) {
			try{
				Appointment o=new Appointment();
				o.setPatient(service_patient.get(request.getParameter("pat_id")));
				o.setDt(DateTransformer.getDate(request.getParameter("dt")));
				o.setDoctor(service_doctor.get(request.getParameter("doc_id")));
				o.setDay(service_day.get(request.getParameter("day_id")));
				
				Appointment app=service.get(service.saveOrUpdate(o).getId().toString());
				if(app!=null)
				{
					return "1";
				}else{
					return "0";
				}
				
			}
			catch(Exception e){	
				
				//return e.getMessage();
				return "0";
			}
		
	}
	
	@RequestMapping(value="/appointment-view-patient", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public List<Map<String, String>> searchByLicense(HttpServletRequest request) {
		List mlist=new ArrayList<Map<String, String>>();
		mlist=service.getByPatient(request.getParameter("pat_id").toString());
			return mlist;	
	}
	
	
}
