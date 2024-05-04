package com.project.DAO;

import java.util.List;

import org.hibernate.Session;

import com.project.model.Employee;
import com.project.util.ProjectUtill;

import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;

public class EmployeeDAO {
	    public void saveEmployee(Employee employee) throws IllegalStateException, SystemException {
	        Transaction transaction = null;
	        try (Session session = ProjectUtill.getSessionFactory().openSession()) {
	            transaction = (Transaction) session.beginTransaction();
	            session.save(employee);
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

//	    public List<Employee> getEmployeeByLocation(String location) {
//	        try (Session session = ProjectUtill.getSessionFactory().openSession()) {
//	            return session.createQuery("from Employee where location = :location", Employee.class)
//	                    .setParameter("location", location)
//	                    .list();
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return null;
//	        }
//	    }
	

	public List<Employee> getEmployeesByLocation(String location) {
		try (Session session = ProjectUtill.getSessionFactory().openSession()) {
            return session.createQuery("from Employee where location = :location", Employee.class)
                    .setParameter("location", location)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}

}
