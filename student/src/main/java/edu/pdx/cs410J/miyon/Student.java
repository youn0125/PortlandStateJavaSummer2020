package edu.pdx.cs410J.miyon;

import edu.pdx.cs410J.lang.Human;
import groovy.json.JsonOutput;

import java.util.ArrayList;
                                                                                    
/**                                                                                 
 * This class is represents a <code>Student</code>.                                 
 */                                                                                 
public class Student extends Human {

  private final double gpa;
  private final ArrayList<String> classes;

  /**                                                                               
   * Creates a new <code>Student</code>                                             
   *                                                                                
   * @param name                                                                    
   *        The student's name                                                      
   * @param classes                                                                 
   *        The names of the classes the student is taking.  A student              
   *        may take zero or more classes.                                          
   * @param gpa                                                                     
   *        The student's grade point average                                       
   * @param gender                                                                  
   *        The student's gender ("male" or "female", or "other", case insensitive)
   */                                                                               
  public Student(String name, ArrayList<String> classes, double gpa, String gender) {
    super(name);
    this.gpa = gpa;
    this.classes = classes;
  }

  /**                                                                               
   * All students say "This class is too much work"
   */
  @Override
  public String says() {                                                            
    throw new UnsupportedOperationException("Not implemented yet");
  }
                                                                                    
  /**                                                                               
   * Returns a <code>String</code> that describes this                              
   * <code>Student</code>.                                                          
   */                                                                               

  public String toString() {
    int numClasses = this.classes.size();
    return this.getName() + " has a GPA of " + this.gpa
            + " and is taking " + numClasses + " class"
            + (numClasses != 1 ? "es" : "")
            + (numClasses == 0 ? '.' : ": " + listOfClasses() + ".")
            + "  ";
  }

  private String listOfClasses(){
    StringBuilder sb = new StringBuilder();
    int numClasses = this.classes.size();
    sb.append(String.join(", ", this.classes.subList(0, numClasses - 1)));
    if (numClasses > 1) {
      if (numClasses > 2) {
        sb.append(",");
      }
      sb.append(" and ");
    }
    sb.append(this.classes.get(numClasses - 1));
    return sb.toString();
  }

  /**
   * Main program that parses the command line, creates a
   * <code>Student</code>, and prints a description of the student to
   * standard out by invoking its <code>toString</code> method.
   */
  public static void main(String[] args) {
    System.err.println("Missing command line arguments");
    System.exit(1);
  }
}