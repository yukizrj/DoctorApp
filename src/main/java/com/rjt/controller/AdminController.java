package com.rjt.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rjt.dao.AdminDao;
import com.rjt.model.Admin;
@RestController
public class AdminController {
	@Autowired
	AdminDao service;
	
	@Autowired
	SessionFactory session;
	
	@RequestMapping(value="/testing", method=RequestMethod.GET)
	public Admin testing(){
		
		Admin o=new Admin();
		o.setUsername("eee");
		o.setPassword("admin");
		o.setName("addd");
		
		service.save(o);
		
		return o;
	}
	
	@RequestMapping(value="/admin-update", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	public String update(HttpServletRequest request){
		try{
		Admin o=new Admin();
		o.setUsername(request.getParameter("username"));
		o.setPassword(request.getParameter("pwd"));
		service.save(o);
		return "1";
		}catch(Exception e){
			return "0";
		}
	}
	
	/*@RequestMapping(value="/admin", method=RequestMethod.POST)
	@Transactional
	public String admin(@RequestBody Admin admin){
		Query query = session.getCurrentSession().createQuery("from Admin where username ="+admin.getUsername()+" and password ="+admin.getPassword());
		query.setParameter("code", admin.getName());
		query.setParameter("pwd", admin.getPassword());
		List list = query.list();
		List<Admin> adlist=list;
				
		System.out.println(adlist);
		
		return adlist.toString();
	}*/
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public ModelAndView patient(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("admin");
		
		return mv;
	}
	
	@RequestMapping(value="/admin-login", method=RequestMethod.POST)
	@CrossOrigin(origins="http://localhost:3000")
	@Transactional
	public String login(HttpServletRequest request){
		
			Admin admin=new Admin();
			admin.setUsername(request.getParameter("username"));
			admin.setPassword(request.getParameter("pwd"));
			Admin o=service.login(admin);
			
			if(o!=null){
				/*HttpSession session=request.getSession();
				session.setAttribute("id", o.getUsername());
				session.setAttribute("name", o.getName());
				session.setAttribute("privilege", "admin");*/
				return admin.getUsername();
			}
			else{
				return "0";
			}
	}
}
