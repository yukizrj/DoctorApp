package com.rjt.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rjt.dao.DoctorDao;
import com.rjt.dao.LeaveDao;
import com.rjt.model.Leave;
import com.rjt.service.DoctorSvc;
import com.rjt.service.LeaveSvc;

@RestController
public class LeaveController {
	@Autowired
	LeaveDao service;
	
	@Autowired
	DoctorDao service_doctor;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, false));
    }
	
	@RequestMapping(value="/leave-search-form", method=RequestMethod.GET)
	public ModelAndView search_form(HttpServletRequest request){
		ModelAndView mv=new ModelAndView();
		
		mv.addObject("privilege", request.getSession().getAttribute("privilege"));
		
		mv.setViewName("leave-search-form");
		
		return mv;
	}
	
	@RequestMapping(value="/leave-save-form/{id}", method=RequestMethod.GET)
	public ModelAndView save_form(@PathVariable("id") String id, HttpServletRequest request){
		ModelAndView mv=new ModelAndView();
		
		Leave o;
		
		if(id.equals("add")){
			o=new Leave();
			o.setDoctor(service_doctor.get(request.getSession().getAttribute("id") + ""));
		}
		else{
			o=service.get(id);
		}
		
		mv.addObject("o", o);
		mv.addObject("privilege", request.getSession().getAttribute("privilege"));
		mv.setViewName("leave-save-form");
		
		return mv;
	}
	
	@RequestMapping(value="/leave-search", method=RequestMethod.GET)
	public @ResponseBody Object search(Leave o, HttpServletRequest request) {
			String privilege=request.getSession().getAttribute("privilege").toString();
			
			String id="";
			
			if(privilege.equals("doctor")){
				id=request.getSession().getAttribute("id").toString();
			}
			
			return service.search(o, id);
		
	}
	
	@RequestMapping(value="/leave-delete/{id}", method=RequestMethod.GET)
	public @ResponseBody String delete(@PathVariable("id") String id) {
		try{
			service.delete(id);
			return "1";
		}
		catch(Exception e){
			
			return e.getMessage();
		}
	}
	
	@RequestMapping(value="/leave-save", method=RequestMethod.POST)
	public @ResponseBody String save(Leave o) {
			try{
				if(o.getApproved()==null){
					o.setApproved(0);
				}
				
				service.saveOrUpdate(o);
				return "1";
			}
			catch(Exception e){
				
				return e.getMessage();
			}
	}
}
