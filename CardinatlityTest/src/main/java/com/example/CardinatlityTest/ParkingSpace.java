package com.example.CardinatlityTest;

import javax.persistence.*;

@Entity
@Table(name="PARKING_SPACE")
public class ParkingSpace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int lot;
	private String location;
	
	
	public ParkingSpace() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ParkingSpace(int lot, String location) {
		super();
		this.lot = lot;
		this.location = location;
	}
	public int getLot() {
		return lot;
	}
	public void setLot(int lot) {
		this.lot = lot;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "ParkingSpace [id=" + id + ", lot=" + lot + ", location=" + location + "]";
	}
	
	

}
