package com.project.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.project.DAO.EmployeeDAO;
import com.project.DAO.UserDAO;
import com.project.model.Employee;
import com.project.model.History;
import com.project.model.Users;
import com.project.util.ProjectUtill;

import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;

public class ProjectMain {
	static String type;
	public static void main(String[] args) throws IllegalStateException, SystemException {
		Scanner scanner = new Scanner(System.in);
		Session session = ProjectUtill.getSessionFactory().openSession();
		session.beginTransaction();
		System.out.println("Welcome to the House Cleaning Application!");

		System.out.println("Are you a user or an employee? (user/employee)");
		String userType = scanner.nextLine();

		if ("user".equalsIgnoreCase(userType)) {
			System.out.println("Are you a login or a signup?");
			 type = scanner.nextLine();

			  if ("signup".equalsIgnoreCase(type)) {
				System.out.println("User signup:");
				System.out.print("Enter username: ");
				String username = scanner.nextLine();
				System.out.print("Enter password: ");
				String password = scanner.nextLine();
				System.out.println("Enter Mobile_No");
				String mobileNo = scanner.nextLine();
				System.out.println("enter your location");
				String location1 = scanner.nextLine();

				Users newUser = new Users();
				newUser.setUsername(username);
				newUser.setPassword(password);
				newUser.setMobile_no(mobileNo);
//				newUser.setLocation(location1);

				session.save(newUser);
				session.getTransaction().commit();
				UserDAO userDAO = new UserDAO();
				userDAO.saveUser(newUser);
				
				System.out.println("User signed up successfully!");
				ProjectMain obj=new ProjectMain();
				obj.login();
			}
		else if ("employee".equalsIgnoreCase(userType)) {
			System.out.println("Employee Sign Up:");
			System.out.print("Enter name: ");
			String employeeName = scanner.nextLine();
			System.out.print("Enter location: ");
			String employeeLocation = scanner.nextLine();
			System.out.println("Enter the mobile number");
			String employeeMobileNo = scanner.nextLine();

			Employee newEmployee = new Employee();
			newEmployee.setName(employeeName);
			newEmployee.setLocation(employeeLocation);
			newEmployee.setEmployeemobile_no(employeeMobileNo);
			session.save(newEmployee);
			session.getTransaction().commit();
			/*
			 * History newhistory=new History (); session.persist(newhistory);
			 * session.getTransaction().commit();
			 */
			EmployeeDAO employeeDAO = new EmployeeDAO();
			employeeDAO.saveEmployee(newEmployee);
			System.out.println("Employee signed up successfully!");
		}
		}
		scanner.close();
	}

	public static void details(String location,Users user) {
		EmployeeDAO employeeDAO = new EmployeeDAO();
		List<Employee> employees = employeeDAO.getEmployeesByLocation(location);

		if (!employees.isEmpty()) {
			Scanner obj = new Scanner(System.in);
			System.out.println("Employees available in " + location + ":");
			for (Employee employee : employees) {
				System.out.println(employee.getName());
			}

			System.out.println("Enter the employee name:");
			String empname = obj.nextLine();

			boolean found = false;
			for (Employee employee : employees) {
				if (employee.getName().equalsIgnoreCase(empname)) {
					System.out.println("Your booking was successful: " + employee.getName());
					List<Employee> emp=new ArrayList<>();
					emp.add(employee);
					
					List<Users>u=new ArrayList<>();
					u.add(user);
				
					user.setEmployees(emp);
					employee.setHistory(u);
					
					org.hibernate.Transaction transaction = null;
			        try (Session session = ProjectUtill.getSessionFactory().openSession()) {
			            transaction =  session.beginTransaction();
			            session.save(employee);
			            session.save(user);
			            transaction.commit();
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
				
					
					found = true;
					break;
				}
			}

			if (!found) {
				System.out.println("Employee not available: " + empname);
			}
		} else {
			System.out.println("No employees available in " + location);
		}
	}
	public  void login() {
		Scanner scanner=new Scanner(System.in);
		Session session = ProjectUtill.getSessionFactory().openSession();
	if ("login".equalsIgnoreCase(type)) {
		System.out.println("User login:");
		System.out.print("Enter username: ");
		String loginName = scanner.nextLine();
		System.out.print("Enter password: ");
		String loginPass = scanner.nextLine();

		Query<Users> query = session.createQuery("FROM Users WHERE password = :login", Users.class);
		query.setParameter("login", loginPass);

		List<Users> usersList = query.getResultList();

		if (!usersList.isEmpty()) {
			Users user = usersList.get(0);
			if (user.getPassword().equalsIgnoreCase(loginPass)) {
				System.out.println("Login successful");
				System.out.println("Enter your location to find available employees:");
				String userLocation = scanner.nextLine();
				details(userLocation,user);
			}
				else {
					System.out.println("incorrect password");
				}
		} else {
			System.out.println("Invalid choice.");
		}
			}
		}
	}
	


