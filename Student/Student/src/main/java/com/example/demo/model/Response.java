//package com.example.demo.model;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//
//public class Response<T> {
//
//	private int status;
//	private String message;
//	private Student student;
//	
//	@JsonInclude(value=Include.NON_NULL)
//	private T data;	
//	public static final Response<?> SUCCESS = new Response<>(200, "Thanks, Student Details are added Successfully..");
//	public static final Response<?> ALREADY_EXIST = new Response<>(201, "Student details already exist");
//	public static final Response<?> FAILURE = new Response<>(205, "Failed to add Student Details");
//	public static final Response<?> NOT_FOUND = new Response<>(404, "Not found");
//	public static final Response<?> BAD_REQUEST = new Response<>(400, "Bad Request");
//	public static final Response<?> SERVER_ERROR = new Response<>(500, "Server Error");
//
//	
//	public Response(int status, String message, Student student) {
//		super();
//		this.status = status;
//		this.message = message;
//		this.student = student;
//	}
//	
//	public Response(int status, String message, T data) {
//		super();
//		this.status = status;
//		this.message = message;
//		this.data = data;
//	}
//	
//	public Response(int status, String message) {
//		super();
//		this.status = status;
//		this.message = message;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//	public void setStatus(int status) {
//		this.status = status;
//	}
//	public String getMessage() {
//		return message;
//	}
//	public void setMessage(String message) {
//		this.message = message;
//	}
//	public T getData() {
//		return data;
//	}
//	public void setData(T data) {
//		this.data = data;
//	}
//	
//
//	public Student getStudent() {
//		return student;
//	}
//
//	public void setStudent(Student student) {
//		this.student = student;
//	}
//	
//	
//}
