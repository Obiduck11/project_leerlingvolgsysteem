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
	@DisplayName("DisplayName")
	void DisplayNameTest() {
		Student student = new Student("Henk", "van", "Dijk");
		String expectedStudentName = "Henk van Dijk";
		String studentName = student.getDisplayName();
		assertEquals(expectedStudentName, studentName);
	}
	@Test
	@DisplayName("DisplayNameWithoutPrefix")
	void DisplayNameTestWithoutPrefix() {
		Student student = new Student("Kees","Test");
		String expectedStudentName = "Kees Test";

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
