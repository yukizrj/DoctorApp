package com.rjt.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rjt.dao.DoctorDao;
import com.rjt.dao.PatientDao;
import com.rjt.model.Appointment;
import com.rjt.model.Doctor;
import com.rjt.model.Patient;
import com.rjt.service.DoctorSvc;
import com.rjt.service.PatientSvc;

@RestController
public class HomeController {
	
	@Autowired
	PatientDao patient_svc;
	
	@Autowired
	DoctorDao doctor_svc;
	
	@RequestMapping(value="/preparation", method=RequestMethod.GET)
	public ModelAndView preparation(){
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("home");
		
		return mv;
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public ModelAndView homepage(){
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("home");
		
		return mv;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request){
		ModelAndView mv=new ModelAndView();
		
		request.getSession().invalidate();
		
		mv.setViewName("redirect:/home");
		
		return mv;
	}
	
	@RequestMapping(value="/myown/{id}", method=RequestMethod.GET)
	public @ResponseBody Map myown(@PathVariable("id") String id){
		
		Doctor doctor=doctor_svc.get(id);
		
		List<Appointment> list=patient_svc.listOfPatientByDoctor(doctor);
		
		Map<Doctor, Set<Patient>> map=new HashMap<Doctor, Set<Patient>>();
		
		for(Appointment a:list){
			if(map.get(doctor)==null){
				Set<Patient> listOfPatients=new HashSet<Patient>();
				listOfPatients.add(a.getPatient());
				map.put(doctor, listOfPatients);
			}
			else{
				Set<Patient> listOfPatients=map.get(doctor);
				listOfPatients.add(a.getPatient());
			}
		}
		
		return map;
	}
}
