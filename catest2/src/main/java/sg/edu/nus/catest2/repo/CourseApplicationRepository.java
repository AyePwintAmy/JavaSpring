package sg.edu.nus.catest2.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.edu.nus.catest2.model.*;

@Repository
public interface CourseApplicationRepository extends JpaRepository<CourseApplication, Integer> {

	//@Query("Select * from cours stu where stu.")
	//List<CourseApplication> getPendingCourseByStudentId(int id);
	List<CourseApplication> findCourseApplicationsByStudentId(int studentId);
	
}
