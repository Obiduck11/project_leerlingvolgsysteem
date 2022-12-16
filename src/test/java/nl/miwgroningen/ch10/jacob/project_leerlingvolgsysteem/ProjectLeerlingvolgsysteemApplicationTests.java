package nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem;

import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Assignment;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.Student;
import nl.miwgroningen.ch10.jacob.project_leerlingvolgsysteem.model.SubmittedVersion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest (classes = ProjectLeerlingvolgsysteemApplicationTests.class)
class ProjectLeerlingvolgsysteemApplicationTests {

	@Test
	@DisplayName("Display student name with infix")
	void displayStudentNameWithInfix() {

		Student student = new Student("Henk", "van", "Dijk");
		String expectedStudentName = "Henk van Dijk";
		String studentName = student.getDisplayName();
		assertEquals(expectedStudentName, studentName);
	}

	@Test
	@DisplayName("Display student name without infix")
	void displayStudentNameWithoutInfix() {

		Student student = new Student("Kees","Test");
		String expectedStudentName = "Kees Test";
		String studentName = student.getDisplayName();
		assertEquals(expectedStudentName, studentName);
	}

	@Test
	@DisplayName("versionPerAssignment")
	void versionPerAssignment() {
		Student student = new Student();
		Assignment assignment = new Assignment();

		SubmittedVersion attempt1 = new SubmittedVersion();
		attempt1.setStudent(student);
		SubmittedVersion attempt2 = new SubmittedVersion();
		attempt2.setStudent(student);

		Set<SubmittedVersion> attemptsForAssignment = getSubmittedVersions(assignment, attempt1, attempt2);

		student.setSubmittedVersions(attemptsForAssignment);
		student.versionsPerAssignment(assignment);

		int expectedSize = 2;

		assertEquals(expectedSize, student.versionsPerAssignment(assignment).size());
	}

	private static Set<SubmittedVersion> getSubmittedVersions(Assignment assignment, SubmittedVersion attempt1, SubmittedVersion attempt2) {
		Set<SubmittedVersion> attemptsForAssignment = new HashSet<>();
		attemptsForAssignment.add(attempt1);
		attemptsForAssignment.add(attempt2);
		attempt1.setAssignment(assignment);
		attempt2.setAssignment(assignment);
		return attemptsForAssignment;
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
