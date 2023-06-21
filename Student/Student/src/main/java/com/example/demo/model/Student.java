package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="Student")
public class Student {

	@Id
	private String _id;
	private String name;
	private String rollNumber;
    private String query;
    private String branch;
	private String contentType;
	private byte[] fileData;

	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public byte[] getFileData() {
		return fileData;
	}
	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRollNumber() {
		return rollNumber;
	}
	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	
	public Student(String _id, String name, String rollNumber, String branch) {
		super();
		this._id = _id;
		this.name = name;
		this.rollNumber = rollNumber;
		this.branch = branch;
	}
	
	public Student() {
		// TODO Auto-generated constructor stub
	}
	public Student(String string, String string2) {
		// TODO Auto-generated constructor stub
	}
	public Student(String string, String string2, String string3) {
		// TODO Auto-generated constructor stub
	}
	public Student(String string) {
		// TODO Auto-generated constructor stub
	}
	
	public void setFilename(String filename) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
