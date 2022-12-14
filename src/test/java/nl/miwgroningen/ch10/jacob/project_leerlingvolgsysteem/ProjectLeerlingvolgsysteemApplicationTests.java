package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assignment;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest (classes = ProjectLeerlingvolgsysteemApplicationTests.class)
class ProjectLeerlingvolgsysteemApplicationTests {

	@Test
	@DisplayName("DisplayNameWithoutInfixName")
	void StudentDisplayNameTest() {
		Student student = new Student();
		String expectedStudentName = "Robbin Drent";

		String studentName = student.getDisplayName();

		assertEquals(expectedStudentName, studentName);
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
	@DisplayName("DisplayName")
	void DisplayNameTest() {
		Student student = new Student();
		String expectedStudentName = "Henk van Dijk";
		student.setFirstName("Henk");
		student.setInFixName("van");
		student.setLastName("Dijk");
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
