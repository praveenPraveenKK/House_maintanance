package com.project.util;

import java.util.Scanner;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.project.model.Employee;
import com.project.model.Users;

public class ProjectUtill {
	private static SessionFactory sessionFactory;

	public static SessionFactory buildSessionFactory() {
		Configuration cfg = new Configuration();
		cfg.configure("Project.cfg.xml");
		cfg.addAnnotatedClass(Employee.class);
		cfg.addAnnotatedClass(Users.class);
		System.out.println("hibernate configuration loaded sucessfully");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties())
				.build();
		SessionFactory sessionFactory = cfg.buildSessionFactory(serviceRegistry);
		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = buildSessionFactory();
		return sessionFactory;
	}

	

}




