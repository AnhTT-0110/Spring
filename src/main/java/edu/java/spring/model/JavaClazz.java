package edu.java.spring.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "clazz")
public class JavaClazz {

    private String message;
    private List<Student> students;

    public JavaClazz(List<Student> students) {
        this.students = students;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println("Your Message : " + message);
    }

    @XmlElements(@XmlElement(name = "student", type = Student.class))
    public List<Student> getStudents() {
        return students;
    }

    public JavaClazz() {
    }
}
