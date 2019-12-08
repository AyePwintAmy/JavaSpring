package sg.edu.nus.catest2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import sg.edu.nus.catest2.model.Course;
import sg.edu.nus.catest2.model.CourseApplication;
import sg.edu.nus.catest2.model.Student;
import sg.edu.nus.catest2.mvcmodel.Session;
import sg.edu.nus.catest2.repo.CourseApplicationRepository;
import sg.edu.nus.catest2.repo.StudentRepository;
import sg.edu.nus.catest2.service.CourseApplicationService;
import sg.edu.nus.catest2.service.StudentService;

@Controller
@RequestMapping("/student2")
@SessionAttributes("usersession")
public class StudentController2 {

	@Autowired
	StudentService sserv;
	@Autowired
	StudentRepository stuRepo;
	@Autowired
	CourseApplicationService caserv;
	@Autowired
	CourseApplicationRepository carepo;
	
	public static Student student;
	
	
	public StudentController2() {
		student = new Student();
		
	}

	//Personal Information of Student
	@GetMapping("/info")
	public String studentInfo(Model model,@SessionAttribute("usersession") Session session){
		System.out.print("Student Info Method with "+session);
		
		student = stuRepo.getByStudentId(session.getSessionId());
		model.addAttribute("student", student);
		
		student=stuRepo.findById(student.getStudentId()).get();
		if(student!=null) {
			model.addAttribute("student",student);
		}	
		//return "studentlistAPP";
		return "redirect:/student2/home";
	}
	
	//Available Courses list
	@GetMapping("/home")
	public String studentHome(Model model,@SessionAttribute("usersession") Session session){
		
		if(session.getSessionId() == 0) {
			return "redirect:/login/welcome";
		}
				
		System.out.print("Student Info Method with "+session);
		student = stuRepo.getByStudentId(session.getSessionId());
		model.addAttribute("student", student);
		
		student=stuRepo.findById(student.getStudentId()).get();
		if(student!=null) {
			model.addAttribute("student",student);
		}	
				
		//model.addAttribute("courseLists", caserv.findCourseApplicationsByStudentId(student.getStudentId()));
		model.addAttribute("courseLists", caserv.findAll());
		return "studentCourseForm";
       		
	}
	// Retrieve all courses of login Student
	@RequestMapping("/courseapplicationlist/")
	public String courseApplicationlist(
			Model model) {
		
		model.addAttribute("student", student);
		student=stuRepo.findById(student.getStudentId()).get();
		System.out.println("All course Application list:Start!!!\n Session of Student:"+student);
		
		if(student!=null) {
			model.addAttribute("student",student);
		}	
		
		model.addAttribute("courseLists", caserv.findCourseApplicationsByStudentId(student.getStudentId()));
		
	    return "studentCourseForm";
		
	}
	@RequestMapping("/enrolledCourse")
	public String enrollCourses() {
		
		return "";
	}
		
}
