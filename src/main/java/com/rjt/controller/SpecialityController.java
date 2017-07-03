package com.rjt.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rjt.dao.SpecialityDao;
import com.rjt.model.Speciality;
import com.rjt.service.SpecialitySvc;

@RestController
public class SpecialityController {
	@Autowired
	SpecialityDao service;

	
	@RequestMapping(value="/speciality-search-form", method=RequestMethod.GET)
	public ModelAndView search_form(){
		ModelAndView mv=new ModelAndView();
		
		mv.setViewName("speciality-search-form");
		
		return mv;
	}
	
	@RequestMapping(value="/speciality-save-form/{id}", method=RequestMethod.GET)
	public ModelAndView save_form(@PathVariable("id") String id){
		ModelAndView mv=new ModelAndView();
		
		Speciality o;
		
		if(id.equals("add")){
			o=new Speciality();
		}
		else{
			o=service.get(id);
		}
		
		mv.addObject("o", o);
		mv.setViewName("speciality-save-form");
		
		return mv;
	}
	
	@RequestMapping(value="/speciality-search", method=RequestMethod.GET)
	public @ResponseBody Object search(Speciality o) {
		
			return service.search(o);
		
	}
	
	@RequestMapping(value="/speciality-delete/{id}", method=RequestMethod.GET)
	public @ResponseBody String delete(@PathVariable("id") String id) {
		try{
			service.delete(id);
			return "1";
		}
		catch(Exception e){
			return e.getMessage();
		}
	}
	
	@RequestMapping(value="/speciality-save", method=RequestMethod.POST)
	public @ResponseBody String save(Speciality o) {
			try{
				service.saveOrUpdate(o);
				return "1";
			}
			catch(Exception e){
				return e.getMessage();
			}
		
	}
}
