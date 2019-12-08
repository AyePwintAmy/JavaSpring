package sg.edu.nus.catest2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/student")
@SessionAttributes("usersession")
public class StudentController {

	@Autowired
	StudentService stuservice;
	@Autowired
	StudentRepository stuRepo;
	@Autowired
	CourseApplicationService caservice;
	@Autowired
	CourseApplicationRepository carepo;
	
	public static Student student;
	
	
	public StudentController() {
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
		return "redirect:/student/home";
	}
	
	// Retrieve all applied courses of login Student
	@GetMapping("/home")
	public String studentHome(Model model,@SessionAttribute("usersession") Session session,
								@RequestParam(name = "page") Optional<Integer> page, 
								@RequestParam(name = "size") Optional<Integer> size,
								@RequestParam(name = "sort") Optional<String> sort){
		
		if(session.getSessionId() == 0) {
			return "redirect:/login/welcome";
		}
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String sorting = sort.orElse("all");
		
		System.out.print("Student Info Method with "+session);
		student = stuRepo.getByStudentId(session.getSessionId());
		model.addAttribute("student", student);
		
		student=stuRepo.findById(student.getStudentId()).get();
		if(student!=null) {
			model.addAttribute("student",student);
		}	
		
		
		model.addAttribute("sort", sorting);
		List<CourseApplication> courseLists=caservice.findCourseApplicationsByStudentId(student.getStudentId());
		model.addAttribute("courseLists", courseLists);

		Page<CourseApplication> courseApplicationPage = caservice.findPaginatedCourseApplication
				(PageRequest.of(currentpage - 1, pagesize),courseLists);
        model.addAttribute("courseApplicationPage", courseApplicationPage);
 
        int totalPages = courseApplicationPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "courseapplicationlist_Stu";
       		
	}
	//All course Application list
	@RequestMapping("/courseapplicationlist")
	public String courseApplicationlist(
			Model model,
			@RequestParam(name = "page") Optional<Integer> page, 
			@RequestParam(name = "size") Optional<Integer> size,
			@RequestParam(name = "sort") Optional<String> sort) {
		
		model.addAttribute("student", student);
		student=stuRepo.findById(student.getStudentId()).get();
		System.out.println("All course Application list:Start!!!\n Session of Student:"+student);
		
		if(student!=null) {
			model.addAttribute("student",student);
		}	
		
		
		int currentpage = page.orElse(1);
		int pagesize = size.orElse(5);
		String sorting = sort.orElse("all");
		
		if(sorting.equals("approved")) {
			System.out.println("Sorting value:"+sorting);
			model.addAttribute("sort", sorting);
			List<CourseApplication> courseApplications = caservice.getApprovedCourseByStudentId(student);
			model.addAttribute("courseApplications", courseApplications);
			System.out.println("Sorting value:"+courseApplications);
			
			Page<CourseApplication> courseApplicationPage = caservice.findPaginatedCourseApplication
					(PageRequest.of(currentpage - 1, pagesize),courseApplications);
	        model.addAttribute("courseApplicationPage", courseApplicationPage);
	 
	        int totalPages = courseApplicationPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "courseapplicationlist_Stu";
		}
		else if(sorting.equals("pending")) {
			model.addAttribute("sort", sorting);
			List<CourseApplication> courseApplications = caservice.getPendingCourseByStudentId(student);
			model.addAttribute("courseApplications", courseApplications);
			
			Page<CourseApplication> courseApplicationPage = caservice.findPaginatedCourseApplication
					(PageRequest.of(currentpage - 1, pagesize),courseApplications);
	        model.addAttribute("courseApplicationPage", courseApplicationPage);
	 
	        int totalPages = courseApplicationPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "courseapplicationlist_Stu";
		}
		else {
			model.addAttribute("sort", sorting);
			List<CourseApplication> courseApplications = carepo.findAll();
			model.addAttribute("courseApplications", courseApplications);
			Page<CourseApplication> courseApplicationPage = caservice.findPaginatedCourseApplication
					(PageRequest.of(currentpage - 1, pagesize),courseApplications);
	        model.addAttribute("courseApplicationPage", courseApplicationPage);
	 
	        int totalPages = courseApplicationPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        return "redirect:/student/home";
		}
	}
		@GetMapping("/allCourses")
		public String courselist(
				Model model,@RequestParam(name = "page") Optional<Integer> page, 
				@RequestParam(name = "size") Optional<Integer> size,
				@RequestParam(name = "sort") Optional<String> sort) {

			int currentpage = page.orElse(1);
			int pagesize = size.orElse(5);
			String sorting = sort.orElse("all");
			
			model.addAttribute("student", student);
			List<CourseApplication> courseApplications = (List<CourseApplication>) caservice.findAll();
			
			
			model.addAttribute("courseApplications", courseApplications);
			Page<CourseApplication> courseApplicationPage = caservice.findPaginatedCourseApplication
					(PageRequest.of(currentpage - 1, pagesize),courseApplications);
	        model.addAttribute("courseApplicationPage", courseApplicationPage);
	 
	        int totalPages = courseApplicationPage.getTotalPages();
	        if (totalPages > 0) {
	            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
	                .boxed()
	                .collect(Collectors.toList());
	            model.addAttribute("pageNumbers", pageNumbers);
	        }
	        
	        return "courseapplicationlist_Stu";
		}
		
		@GetMapping("/edit/{id}")
		public String EditForm(Model model,@PathVariable("id") Integer id) {
			Student stu =stuRepo.findById(id).get();
			model.addAttribute("student",stu);
			return "studentEditForm";
		
		}
		@PostMapping("/save")
		public String saveProduct(@Valid Student student, BindingResult bindingResult) {
			if (bindingResult.hasErrors()) {
				return "studentEditForm";
			}
			System.out.println(student);
			stuservice.save(student);			
			return "redirect:/student/home";
		}
}
