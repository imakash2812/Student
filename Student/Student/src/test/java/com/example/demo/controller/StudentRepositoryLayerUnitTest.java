package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.example.demo.model.Student;
import com.example.demo.repo.StudentRepository;
@Profile("test")
@SpringBootTest
//@DataJpaTest	//Spring Boot provides the @DataJpaTest annotation to enhance testing functionality for JPA repositories. It automatically configure JPA repositories by annotating the unit test class with @DataJpaTest.
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)

public class StudentRepositoryLayerUnitTest {
	@Autowired
	 private StudentRepository studentRepository;



	   @Test
	   @Order(1)
	   public void _should_add_student() {
	    Student student = new Student();
	    student.setName("Anoop");
	    student.setRollNumber("2");
	    student.setBranch("Btech");
	    Student persistedStudent=this.studentRepository.save(student);
	    assertNotNull(persistedStudent);
//	    assertEquals("",persistedStudent.get_id());
	    assertEquals("Anoop",persistedStudent.getName());
	    assertEquals("2",persistedStudent.getRollNumber());
	    assertEquals("Btech", persistedStudent.getBranch());
	 }
		    
	   @Test
	   @Order(7)
	   public void _should_return_student_list() 
	   {
		   //When
		   List<Student> students=this.studentRepository.findAll();
		   
		   //Then
		   assertEquals(21,students.size());
		   
	   }
	   
	   @Test
	   @Order(3)
	   public void _should_return_student_by_id()
	   {
		   //When
		   Optional<Student> student=this.studentRepository.findById("64899cd43920e16d3ae0ba82");
		   //Then
		   assertTrue(student.isPresent());
	   }
	   
	   @Test
	   @Order(4)
	   public void _should_return_student_by_name()
	   {
		   //When
		   Optional<Student> student=this.studentRepository.findByName("Anmol");
		   //Then
		   assertTrue(student.isPresent());
	   }
	   
	   @Test
	   @Order(5)
	   public void _should_return_student_by_RollNumber()
	   {
		   //When
		   Optional<Student> student=this.studentRepository.findByRollNumber("3");
		   //Then
		   assertTrue(student.isPresent());
	   }
	   
	   @Test
	   @Order(2)
	   public void _should_update_existing_student()
	   {

		   String _id="64899cd43920e16d3ae0ba82";
		   Optional<Student> optionalStudent = studentRepository.findById(_id);

		   if(optionalStudent.isPresent()) {
		   //Given
		   Student existingStudent=optionalStudent.get();
		   existingStudent.set_id("64899cd43920e16d3ae0ba82");
		   existingStudent.setName("Anmol");
		   existingStudent.setRollNumber("3");
		   existingStudent.setBranch("Electrical");
		   //When
		   Student updatedStudent=this.studentRepository.save(existingStudent);
		   //Then
		   assertNotNull(updatedStudent);
		   assertEquals("64899cd43920e16d3ae0ba82", updatedStudent.get_id());
		   assertEquals("Anmol", updatedStudent.getName());
		   assertEquals("3", updatedStudent.getRollNumber());
		   assertEquals("Electrical", updatedStudent.getBranch());
		   }
	   }
	   @Test
	   @Order(6)
	   public void _should_delete_student_by_Id() 
	   {
		   //When
		 this.studentRepository.deleteById("64899d623bee1d6b6bdbd044"); 
		 Optional<Student> student=this.studentRepository.findById("2");
		 //Then
		 assertFalse(student.isPresent());
	   }

}
