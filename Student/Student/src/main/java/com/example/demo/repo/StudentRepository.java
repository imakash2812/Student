package com.example.demo.repo;

import java.util.List;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Student;
public interface StudentRepository extends MongoRepository<Student, String>{

	Optional<Student> findById(String id);

	Optional<Student> findByName(String name);

	Optional<Student> findByRollNumber(String rollNumber);

	Student getStudentDetailsBy_id(String id);

//	Optional<Student> findByFileId(String fileId);


    List<Student> findByQueryContainingIgnoreCase(String query);

    List<Student> findByNameContainingIgnoreCase(String name);

	List<Student> findByRollNumberContainingIgnoreCase(String rollNumber);

	List<Student> findByBranchContainingIgnoreCase(String branch);

//	void addStudent(Student johnDoe);

//	Object addStudentDetails(Student student);

//	List<Student> findByIdContainingIgnoreCase(String _id);

}
