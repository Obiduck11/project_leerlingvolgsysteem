package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assignment;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Course;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.SubmittedVersion;
import org.hibernate.internal.util.collections.IdentitySet;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest (classes = ProjectLeerlingvolgsysteemApplicationTests.class)
class ProjectLeerlingvolgsysteemApplicationTests {

	@Test
	@DisplayName("DisplayNameWithoutInfixName")
	void studentDisplayNameTest() {
		Student student = new Student();
		String expectedStudentName = "Robbin Drent";

		String studentName = student.getDisplayName();

		assertEquals(expectedStudentName, studentName);
	}

	@Test
	@Disabled
	@DisplayName("checkCoursesToString")
	void testCoursesToString() {
		Student testStudent = new Student("Wytse", "Boiten");
		Course rekenen = new Course();
		rekenen.setName("Rekenen");
		Course nederlands = new Course();
		nederlands.setName("Nederlands");

		Set<Course> testCourses = new HashSet<>();
		testCourses.add(rekenen);
		testCourses.add(nederlands);

		testStudent.setCourses(testCourses);

		String expectedCoursesList = ("Rekenen<br />Nederlands<br />");
		String coursesList = testStudent.getCoursesToString();

		assertEquals(expectedCoursesList, coursesList);
	}


	@Test
	@DisplayName("DisplayNameWithInfixName")
	void StudentDisplayNameWithInfixTest() {
		Student student = new Student();
		String expectedStudentName = "Henk van Dijk";

		String studentName = student.getDisplayName();

		assertEquals(expectedStudentName, studentName);
	}

	@Test
	@DisplayName("CheckforPlus")
	void countTestPlus(){
		Assignment assignment = new Assignment();
		String plus = "plus";
		int expected = 1;

		assertEquals(expected, assignment.count(plus));
	}

	@Test
	@DisplayName("checkForMinus")
	void coutnTestMinus(){
		Assignment assignment = new Assignment();
		String minus = "minus";
		int expected = -1;

		assertEquals(expected, assignment.count(minus));
	}

}
