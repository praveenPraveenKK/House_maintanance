package com.project.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
	public class Employee {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    private String location;
	    private String Employeemobile_no;
		public Employee() {
			super();
			this.id = id;
			this.name = name;
			this.location = location;
		}
		public List<Users> getHistory() {
			return History;
		}
		public void setHistory(List<Users> history) {
			History = history;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getEmployeemobile_no() {
			return Employeemobile_no;
		}
		public void setEmployeemobile_no(String employeemobile_no) {
			Employeemobile_no = employeemobile_no;
		}
		@ManyToMany(cascade= {CascadeType.PERSIST,CascadeType.DETACH})
		  @JoinTable(name="History",joinColumns=@JoinColumn(name="name"),
		  inverseJoinColumns=@JoinColumn(name="user_name"))
		private List<Users> History;
	    
}
