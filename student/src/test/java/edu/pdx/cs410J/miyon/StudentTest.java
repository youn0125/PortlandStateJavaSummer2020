package edu.pdx.cs410J.miyon;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Unit tests for the Student class.  In addition to the JUnit annotations,
 * they also make use of the <a href="http://hamcrest.org/JavaHamcrest/">hamcrest</a>
 * matchers for more readable assertion statements.
 */
public class StudentTest
{
  private Student createStudentNamed(String name) {
    return new Student(name, new ArrayList<>(), 0.0, "Doesn't matter");
  }
  @Test
  public void studentNamedPatIsNamedPat() {
    String name = "Pat";
    var pat = createStudentNamed(name);
    assertThat(pat.getName(), equalTo(name));
  }
  @Test
  public void toStringContainsStudentName() {
    String name = "Pat";
    Student pat = createStudentNamed(name);
    assertThat(pat.toString(), containsString(name));
  }
  @Test
  public void toStringContainsGpa() {
    double gpa = 3.76;
    Student pat = new Student("Pat", new ArrayList<>(), gpa, "Doesn't matter");
    assertThat(pat.toString(), containsString("has a GPA of " + gpa));
  }

  @Ignore
  @Test
  public void toStringForExampleInAssignment() {
    Student dave = createDaveStudent();
    assertThat(dave.toString(), equalTo("Dave has a GPA of 3.64 and is taking 3 classes: Algorithms, Operating " +
            "Systems, and Java. He says \"This class is too much work\"."));
  }

  private Student createDaveStudent() {
    ArrayList<String> classes = new ArrayList<>();
    classes.add("Algorithm");
    classes.add("Operating Systems");
    classes.add("Java");
    return new Student("Dave", classes, 3.64, "male");
  }

  @Test
  public void daveTake3Classes() {
    Student dave = createDaveStudent();
    assertThat(dave.toString(), containsString("and is taking 3 classes"));
  }

  @Test
  public void studentTaking1ClassHasASingularWord() {
    ArrayList<String> classes = new ArrayList<>();
    classes.add("English");
    Student student = new Student("Name", classes, 3.64, "doesn't matter");

    assertThat(student.toString(), containsString("and is taking 1 class:"));
  }

  @Test
  public void studentTaking0ClassHasNoColonInSentence() {
    ArrayList<String> classes = new ArrayList<>();
    Student student = new Student("Name", classes, 3.64, "doesn't matter");

    assertThat(student.toString(), containsString("and is taking 0 classes."));
  }

  @Ignore
  @Test
  public void daveToStringHasAllClassNames() {
    Student dave = createDaveStudent();
    assertThat(dave.toString(), containsString("classes: Algorithms, Operating Systems, and Java."));
  }
}
