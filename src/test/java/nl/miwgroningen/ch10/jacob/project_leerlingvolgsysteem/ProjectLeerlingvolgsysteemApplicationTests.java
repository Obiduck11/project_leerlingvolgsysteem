package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
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

}
