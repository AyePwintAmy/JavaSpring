package com.example.CardinatlityTest;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CardinatlityTestApplication {

	public static final Logger Log=LoggerFactory.getLogger(CardinatlityTestApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(CardinatlityTestApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo(EmployeeRepository repository, ParkingRepository prepository) {
		return(arg)->{
		
			Log.info("Demo One to One Unidirectional Relationship");
			ParkingSpace ps1 = new ParkingSpace(100,"ISS 29 HMKT");
			ParkingSpace ps2 = new ParkingSpace(101,"SFAH");
			ParkingSpace ps3 = new ParkingSpace(102,"GuILD House");
			
			
			ParkingSpace ps4 = new ParkingSpace(100,"ISS 29 HMKT");
			ParkingSpace ps5 = new ParkingSpace(101,"SFAH");
			ParkingSpace ps6 = new ParkingSpace(102,"GuILD House");
			
			ArrayList<ParkingSpace> plist = new ArrayList<ParkingSpace>();
			plist.add(ps1);
			plist.add(ps2);
			plist.add(ps3);
			
			plist.add(ps4);
			plist.add(ps5);
			plist.add(ps6);
			prepository.saveAll(plist);
			Employee e1 = new Employee("Dilber", 100, ps1);
			Employee e2 = new Employee("Alice", 100, ps2);
			Employee e3 = new Employee("Pointy", 100, ps3);
			ArrayList<Employee> elist=new ArrayList<Employee>();
			elist.add(e1);
			elist.add(e2);
			elist.add(e3);
			repository.saveAll(elist);
			for(Employee e:repository.findAll()) {
				Log.info(e.toString());
			}
					
			};
		
		
	}

}
