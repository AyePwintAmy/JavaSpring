package com.example.CardinatlityTest;

import javax.persistence.*;

@Entity
@Table(name="emprelation")
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private double salary;
	@OneToOne
	@JoinColumn(name="PSPACE_ID")
	private ParkingSpace parkingSpace;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(String name, double salary, ParkingSpace parkingSpace) {
		super();
		this.name = name;
		this.salary = salary;
		this.parkingSpace = parkingSpace;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public ParkingSpace getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(ParkingSpace parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	@Override
	public String toString() {
		//return "Employee [id=" + id + ", name=" + name + ", parkingSpace=" + parkingSpace + "]";
		return "Employee id: "+ getId() + " name: "+getName()+" with "+getParkingSpace().toString();
	}

	
	

}
