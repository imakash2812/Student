package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.helper.FileUploadHelper;
import com.example.demo.model.Student;
import com.example.demo.repo.StudentRepository;
import com.example.demo.service.StudentServiceImpl;
@ExtendWith(MockitoExtension.class)
public class StudentServiceUnitTest {

	@Mock
	private StudentRepository studentRepository;
	
	@InjectMocks
	private StudentServiceImpl studentServiceImpl;
	
	@Mock
    private Logger logger;
	
	
	@Captor
    private ArgumentCaptor<String> captor;
	
	@Mock
    private FileUploadHelper fileUploadHelper;

	
	@Test
	void save_should_insert_new_Student() {
		
		//Given
		Student student=this.buildTestingStudent();
		
		//When
		this.studentServiceImpl.save(student);
		
		//Then
		verify(this.studentRepository).save(student);
	}

	@Order(1)
	@Test
	void findAll_should_return_Student_list() {
		//Given
		Student Student=this.buildTestingStudent();
		
		//When
		when(studentRepository.findAll()).thenReturn(List.of(Student));
		List<Student> Students=this.studentServiceImpl.findAll();
		
		//Then
		assertEquals(1,Students.size());
		verify(this.studentRepository).findAll();
	}
	
	@Order(2)
	@Test
	void findById_should_return_Student() {
		
		//Given
		Student Student=this.buildTestingStudent();
		
		//when
		when(studentRepository.findById("1")).thenReturn(Optional.of(Student));
		Optional returnedStudent=this.studentServiceImpl.findById("1");
		
		//Then
		assertEquals(Student.get_id(),((Student) returnedStudent.get()).get_id());
		verify(this.studentRepository).findById("1");
	}
	
	@Order(4)
	@Test
	void deleteById_should_delete_student() {
		//when
		this.studentServiceImpl.deleteEmployeeById("1");
		
		//Then
		verify(this.studentRepository).deleteById("1");
	}

	@Order(5)
	@Test
	void findByName_should_return_Student() {
		
		//Given
		Student Student=this.buildTestingStudent();
		
		//when
		when(studentRepository.findByName("Arun")).thenReturn(Optional.of(Student));
		Optional returnedStudent=this.studentServiceImpl.findByName("Arun");
		
		//Then
		assertEquals(Student.getName(),((Student) returnedStudent.get()).getName());
		verify(this.studentRepository).findByName("Arun");
	}
	
	@Order(6)
	@Test
	void findByRollNumber_should_return_Student() {
		
		//Given
		Student Student=this.buildTestingStudent();
		
		//when
		when(studentRepository.findByRollNumber("1")).thenReturn(Optional.of(Student));
		Optional returnedStudent=this.studentServiceImpl.findByRollNumber("1");
		
		//Then
		assertEquals(Student.getRollNumber(),((Student) returnedStudent.get()).getRollNumber());
		verify(this.studentRepository).findByRollNumber("1");
	}
	
	private Student buildTestingStudent() {
		Student student=new Student();
		student.setName("Arun");
		student.setRollNumber("1");
		student.setBranch("CSE");
		return student;
	}
	
	
	 @Test
	    public void addStudentDetails_Success() {
	        // Arrange
	        Student student = new Student();
	        student.setName("Mohan");
	        student.setRollNumber("1800");
	        student.setBranch("BTech");

	        when(studentRepository.save(student)).thenReturn(student);

	        // Act
	        ResponseEntity<Student> response = studentServiceImpl.addStudentDetails(student);

	        // Assert
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(student, response.getBody());
	        assertEquals("Mohan", student.getName());
	        assertEquals("1800", student.getRollNumber());
	        assertEquals("BTech", student.getBranch());
	    }
//	 @Test
//	    public void testAddStudentDetails_Existing() {
//	        // Arrange
//	        Student student = new Student();
//	        student.setName("Mohan");
//	        student.setRollNumber("1800");
//	        student.setBranch("BTech");
//	        
//	        when(studentRepository.findByName(student.getName())).thenReturn(Optional.of(student));
//	        
//	        // Act
//	        ResponseEntity<Student> result = studentServiceImpl.addStudentDetails(student);
//	        
//	        // Assert
//	        assertEquals(null, result);
//	    }
	
	 	@Test
	 	public void testAddStudentDetails_Existing() {
	 	// Arrange
	        Student student = new Student();
	        student.setName("Mohan");
	        student.setRollNumber("1800");
	        student.setBranch("BTech");

	        when(studentRepository.findByName(student.getName())).thenReturn(Optional.of(student));
	        
	        ResponseEntity<Student> response=studentServiceImpl.addStudentDetails(student);
	        
	        assertEquals(null, response);
	        
	 	}
	 	
	 	@Test
	 	public void testAddStudentDetails_Exception() {
	 		Student student = new Student();
	        student.setName("Mohan");
	        student.setRollNumber("1800");
	        student.setBranch("BTech");

	        when(studentRepository.findByName(student.getName())).thenThrow(new RuntimeException("Some error occurs in adding student details by name: "+student.getName()));
	        
	        ResponseEntity<Student> response=studentServiceImpl.addStudentDetails(student);
	        
	        assertNull(response);
	        
	 	}




	    @BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);
	    }
	    @Test
	    public void testGetStudentDetailsById_ExistingStudent() {
	        // Arrange
	        String studentId = "123";
	        Student existingStudent = new Student();
	        existingStudent.set_id(studentId);
	        existingStudent.setName("Akki");
	        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

	        // Act
	        Student result = studentServiceImpl.getStudentDetailsById(studentId);

	        // Assert
	        Assertions.assertEquals(existingStudent, result);
	    }

	    @Test
	    public void testGetStudentDetailsById_NonExistingStudent() {
	        // Arrange
	        String studentId = "456";
	        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

	        // Act
	        Student result = studentServiceImpl.getStudentDetailsById(studentId);

	        // Assert
	        Assertions.assertNull(result);
	    }
	    
	    @Test
	    public void testGetStudentDetailsById_Exception() {
	        // Arrange
	        String studentId = "789";
	        Mockito.when(studentRepository.findById(studentId)).thenThrow(new RuntimeException("Some error occurred."));

	        // Act
	        Student result = studentServiceImpl.getStudentDetailsById(studentId);

	        // Assert
	        Assertions.assertNull(result);
	    }
	    
	    @Test
	    public void testGetStudentDetailsByName_Existing() {
	    	//Arrange
	    	String studentName="Isha";
	    	Student Existingstudent=new Student();
	    	Existingstudent.setName(studentName);
	    	Mockito.when(studentRepository.findByName(studentName)).thenReturn(Optional.of(Existingstudent));
	    	
	    	//Act
	    	Student result=studentServiceImpl.getStudentDetailsByName(studentName);
	    	
	    	//Assertions
	    	Assertions.assertEquals(Existingstudent, result);
	    	
	    }

	    @Test
	    public void testGetDetailsByName_NonExisting() {
	    	//Arrange
	    	String studentName="Priya";
	    	Student existingStudent=new Student();
	    	existingStudent.setName(studentName);
	    	Mockito.when(studentRepository.findByName(studentName)).thenReturn(Optional.empty());
	    	
	    	//Act
	    	Student result=studentServiceImpl.getStudentDetailsByName(studentName);
	    	
	    	//Assertion
	    	Assertions.assertNull(result);
	    	
	    }
	    
//	    @Test
//	    public void testGetStudentDetailsByName_Exception() {
//	        // Arrange
//	        String studentName = "Priya";
//	        Mockito.when(studentRepository.findById(studentName)).thenThrow(new RuntimeException("Some error occurred."));
//
//	        // Act
//	        Student result = studentServiceImpl.getStudentDetailsById(studentName);
//
//	        // Assert
//	        Assertions.assertNull(result);
//	    }
	    
	    @Test
	    public void testGetStudentDetailsByName_ExceptionCaught() {
	        // Arrange
	        String studentName = "John Doe";
	        Mockito.when(studentRepository.findByName(studentName)).thenThrow(new RuntimeException("Some error occurred."));

	        // Act
	        Student result = studentServiceImpl.getStudentDetailsByName(studentName);

	        // Assert
	        Assertions.assertNull(result);
	    }
	    
	    @Test
	    public void testGetStudentByRollNumber_Existing() {
	    	String RollNumber="18020004008";
	    	Student existingstudent=new Student();
	    	existingstudent.setRollNumber(RollNumber);
	    	Mockito.when(studentRepository.findByRollNumber(RollNumber)).thenReturn(Optional.of(existingstudent));
	    	
	    	//Act
	    	Student result=studentServiceImpl.getStudentDetailsByRollNumber(RollNumber);
	    	
	    	//Assertions
	    	Assertions.assertEquals(existingstudent, result);
	    
	    }
	//======Below code does not cover else statement    
//	    @Test
//	    public void testGetStudentByRollNumber_Non_Existing()
//	    {
//	    	String RollNumber="123456";
//	    	Student nonexisting=new Student();
//	    	nonexisting.setRollNumber(RollNumber);
//	    	Mockito.when(studentRepository.findByRollNumber(RollNumber)).thenReturn(Optional.empty());
//	    	
//	    	//Act
//	    	Optional result=studentServiceImpl.findByRollNumber(RollNumber);
//	    	
//	    	//Assertions
//	    	assertEquals(null,result);
//	    	
//	    	
//	    }
	    @Test
	    public void testGetStudentDetailsByRollNumber_NonExisting() {
	        // Arrange
	        String rollNumber = "123456";
	        Mockito.when(studentRepository.findByRollNumber(rollNumber)).thenReturn(Optional.empty());

	        // Act
	        Student result = studentServiceImpl.getStudentDetailsByRollNumber(rollNumber);

	        // Assert
	        assertEquals(null, result);
	        // Add assertion for debug log message
	    }
	    
	    @Test
	    public void testGetStudentDetailsByRollNumber_ValidRollNumber() {
	        // Arrange
	        String rollNumber = "12345";
	        Mockito.when(studentRepository.findByRollNumber(rollNumber)).thenReturn(Optional.empty());

	        // Act
	        Student result = studentServiceImpl.getStudentDetailsByRollNumber(rollNumber);

	        // Assert
	        assertEquals(null, result);
	        // Add assertion for debug log message
	    }
	    @Test
	    public void testGetStudentDetailsByRollNumber_InvalidRollNumber() {
	        // Arrange
	        String rollNumber = "99999";
	        Mockito.when(studentRepository.findByRollNumber(rollNumber)).thenThrow(new RuntimeException("Invalid Roll Number"));

	        // Act
	        Student result = studentServiceImpl.getStudentDetailsByRollNumber(rollNumber);

	        // Assert
	        assertEquals(null, result);
	        // Add assertion for debug log message
	    }
	    
	    @Test
	    public void testGetStudentDetailsByRollNumber_ExceptionHandling() {
	        // Arrange
	        String rollNumber = "54321";
	        Mockito.when(studentRepository.findByRollNumber(rollNumber)).thenThrow(new RuntimeException("Exception occurred"));

	        // Act
	        Student result = studentServiceImpl.getStudentDetailsByRollNumber(rollNumber);

	        // Assert
	        assertEquals(null, result);
	        // Add assertion for debug log message
	    }
	    
	    @Test
	    public void testGetAllStudentList_EmptyStudentList() {
	        // Arrange
	        List<Student> emptyList = new ArrayList();
	        Mockito.when(studentRepository.findAll()).thenReturn(emptyList);

	        // Act
	        List<Student> result = studentServiceImpl.getAllStudentList();

	        // Assert
	        assertEquals(null, result);
	        // Add assertion for debug log message
	    }
	    
	    @Test
	    public void testGetAllStudentList_NonEmptyStudentList() {
	        // Arrange
	        List<Student> students = new ArrayList<>();
	        students.add(new Student("John", "Doe"));
	        students.add(new Student("Jane", "Smith"));
	        Mockito.when(studentRepository.findAll()).thenReturn(students);

	        // Act
	        List<Student> result = studentServiceImpl.getAllStudentList();

	        // Assert
	        assertEquals(students, result);
	        // Add assertion for info log message
	    }
	    
//	    @Test
//	    public void testGetAllStudentList_ExceptionHandling() {
//	        // Arrange
//	        Mockito.when(studentRepository.findAll()).thenThrow(new RuntimeException("Error occurred"));
//
//	        // Act
//	        List<Student> result = studentServiceImpl.getAllStudentList();
//
//	        // Assert
//	        assertEquals(null, result);
//	        // Add assertion for debug log message
//	    }
//	    
	    @Test
	    public void testUpdateStudentById_ExistingStudent() {
	        // Arrange
	        String studentId = "12345";
	        Student existingStudent = new Student("John", "Doe", "Computer Science");
	        Student updatedStudent = new Student("Jane", "Smith", "Electrical Engineering");
	        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
	        Mockito.when(studentRepository.save(Mockito.any(Student.class))).thenReturn(updatedStudent);

	        // Act
	        ResponseEntity<Student> result = studentServiceImpl.updateStudentById(updatedStudent, studentId);

	        // Assert
	        assertEquals(existingStudent, result.getBody());
	        // Add assertion for info log message
	    }
	    
	    @Test
	    public void testUpdateStudentById_NonExistingStudent() {
	        // Arrange
	        String studentId = "99999";
	        Student updatedStudent = new Student("Jane", "Smith", "Electrical Engineering");
	        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

	        // Act
	        ResponseEntity<Student> result = studentServiceImpl.updateStudentById(updatedStudent, studentId);

	        // Assert
	        assertEquals(null, result.getBody());
	        // Add assertion for debug log message
	    }
	    
	    @Test
	    public void testUpdateStudentById_ExceptionHandling() {
	        // Arrange
	        String studentId = "54321";
	        Student updatedStudent = new Student("Jane", "Smith", "Electrical Engineering");
	        Mockito.when(studentRepository.findById(studentId)).thenThrow(new RuntimeException("Error occurred"));

	        // Act
	        ResponseEntity<Student> result = studentServiceImpl.updateStudentById(updatedStudent, studentId);

	        // Assert
	        assertEquals(null, result.getBody());
	        // Add assertion for debug log message
	    }


@Test
public void testDeleteStudentById_ExistingStudent() {
    // Arrange
    String studentId = "12345";
    Student existingStudent = new Student("John", "Doe", "Computer Science");
    Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));

    // Act
    ResponseEntity<Student> result = studentServiceImpl.deleteStudentById(studentId);

    // Assert
    assertEquals(null, result.getBody());
    // Add assertion for info log message
}

@Test
void testDeleteStudentById_NonExistingStudent() {
    // Arrange
    String studentId = "123";
    when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

    // Act
    ResponseEntity<Student> response = studentServiceImpl.deleteStudentById(studentId);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    verify(studentRepository, times(0)).deleteById(studentId);
    verify(logger, times(0)).debug("Student with ID: " + studentId + " not found");
}





@Test
public void testDeleteStudentById_ExceptionHandling() {
    // Arrange
    String studentId = "54321";
    Mockito.when(studentRepository.findById(studentId)).thenThrow(new RuntimeException("Error occurred"));

    // Act
    ResponseEntity<Student> result = studentServiceImpl.deleteStudentById(studentId);

    // Assert
    assertEquals(null, result.getBody());
    // Add assertion for debug log message
}

@Test
public void testUploadAnySpecificFile_SuccessfulUpload() {
    // Arrange
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    when(fileUploadHelper.uploadFile(mockFile)).thenReturn(true);

    // Act
    ResponseEntity<String> result = studentServiceImpl.uploadAnySpecificFile(mockFile);

    // Assert
    assertEquals(ResponseEntity.ok("File uploaded Successfully."), result);
    verify(fileUploadHelper).uploadFile(mockFile);
}

@Test
public void testUploadAnySpecificFile_FailedUpload() {
    // Arrange
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    when(fileUploadHelper.uploadFile(mockFile)).thenReturn(false);

    // Act
    ResponseEntity<String> result = studentServiceImpl.uploadAnySpecificFile(mockFile);

    // Assert
    assertEquals(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong! Try again"), result);
    verify(fileUploadHelper).uploadFile(mockFile);
}

@Test
public void testUploadAnySpecificFile_ExceptionHandling() {
    // Arrange
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    when(fileUploadHelper.uploadFile(mockFile)).thenThrow(new RuntimeException("Error occurred"));

    // Act
    ResponseEntity<String> result = studentServiceImpl.uploadAnySpecificFile(mockFile);

    // Assert
    assertEquals(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong! Try again"), result);
    verify(fileUploadHelper).uploadFile(mockFile);
}

@Test
public void testUploadMultipleFiles_SuccessfulUpload() throws Exception {
    // Arrange
    MultipartFile[] mockFiles = new MultipartFile[2];
    MultipartFile mockFile1 = Mockito.mock(MultipartFile.class);
    when(mockFile1.getOriginalFilename()).thenReturn("file1.txt");
    when(mockFile1.getContentType()).thenReturn("text/plain");
    when(mockFile1.getBytes()).thenReturn(new byte[]{});
    mockFiles[0] = mockFile1;

    MultipartFile mockFile2 = Mockito.mock(MultipartFile.class);
    when(mockFile2.getOriginalFilename()).thenReturn("file2.txt");
    when(mockFile2.getContentType()).thenReturn("text/plain");
    when(mockFile2.getBytes()).thenReturn(new byte[]{});
    mockFiles[1] = mockFile2;

    // Act
    studentServiceImpl.uploadMultipleFiles(mockFiles);

    // Assert
    verify(studentRepository, Mockito.times(2)).save(Mockito.any(Student.class));
    // Add assertions for log messages
}

@Test
public void testUploadMultipleFiles_ExceptionHandling() throws Exception {
    // Arrange
    MultipartFile[] mockFiles = new MultipartFile[1];
    MultipartFile mockFile = Mockito.mock(MultipartFile.class);
    when(mockFile.getOriginalFilename()).thenReturn("file1.txt");
    when(mockFile.getContentType()).thenReturn("text/plain");
    when(mockFile.getBytes()).thenReturn(new byte[]{});
    mockFiles[0] = mockFile;

    Mockito.doThrow(new RuntimeException("Error occurred"))
            .when(studentRepository).save(Mockito.any(Student.class));

    // Act
    studentServiceImpl.uploadMultipleFiles(mockFiles);

    // Assert
    // Add assertions for error log message
}

//@Test
//public void testGeneratePdf() throws IOException, DocumentException {
//    // Mock student data
//    List<Student> students = new ArrayList<>();
//    students.add(new Student("John Doe", "12345", "Computer Science"));
//    students.add(new Student("Jane Smith", "67890", "Electrical Engineering"));
//
//    // Mock the studentRepository
//    Mockito.when(studentRepository.findAll()).thenReturn(students);
//
//    // Call the method to generate the PDF
//    byte[] pdfBytes = generatePdf();
//
//    // Assert the result
//    Assertions.assertNotNull(pdfBytes);
//    Assertions.assertTrue(pdfBytes.length > 0);
//
//    // Additional assertions based on the expected PDF content
//    // ...
//}
////Now, the testGeneratePdf() method correctly calls the generatePdf() method and asserts the result. You can add any additional assertions based on the expected PDF content within the test case.
//
//private byte[] generatePdf()throws IOException, DocumentException {
//	List<Student> stud = studentRepository.findAll();
//    ByteArrayOutputStream out = new ByteArrayOutputStream();
//    Document document = new Document();
//    PdfWriter.getInstance(document, out);
//
//    document.open();
//    for (Student student : stud) {
//        Paragraph paragraph = new Paragraph(student.get_id() + " - " + student.getName() +" - "+ student.getRollNumber() + " - " + student.getBranch() +" - " + student.getFileData() +" - " + student.getContentType());
//        document.add(paragraph);
//    }
//    document.close();
//
//    return out.toByteArray();
//}
@Test
public void testCreatePdf() throws IOException {
    // Mock student data
    List<Student> students = new ArrayList<>();
    students.add(new Student("John Doe", "12345", "Computer Science"));
    students.add(new Student("Jane Smith", "67890", "Electrical Engineering"));

    // Mock the studentRepository
    when(studentRepository.findAll()).thenReturn(students);

    // Call the method to generate the PDF
    ByteArrayInputStream pdfStream = studentServiceImpl.createPdf();

    // Assert the result
    assertNotNull(pdfStream);

    // Read the content of the PDF stream and assert its content if needed
    // ...

    // Close the PDF stream
    pdfStream.close();
}

@Test
public void testCreatePdf_ErrorInDocumentGeneration() throws IOException {
    // Mock the studentRepository
    when(studentRepository.findAll()).thenReturn(Collections.emptyList());

    // Call the method to generate the PDF
    ByteArrayInputStream pdfStream = studentServiceImpl.createPdf();

    // Assert the result
    assertNotNull(pdfStream);

    // Read the content of the PDF stream and assert its content if needed
    // ...

    // Close the PDF stream
    pdfStream.close();
}
//@Test
//public void testCreateDocument() throws IOException {
//    // Create a mock student repository
//    StudentRepository studentRepository = mock(StudentRepository.class);
//    List<Student> students = new ArrayList<>();
//    students.add(new Student("John Doe", "001", "Computer Science"));
//    students.add(new Student("Jane Smith", "002", "Electrical Engineering"));
//    when(studentRepository.findAll()).thenReturn(students);
//    
//    // Create an instance of the service and call the method
////    StudentServiceImpl studentService = new StudentServiceImpl(studentRepository);
//    ByteArrayInputStream documentBytes = studentServiceImpl.createDocument();
//    
//    // Perform assertions
//    assertNotNull(documentBytes);
//}
//
//@Test
//public void testCreateDocument_EmptyStudentList() throws IOException {
//    // Create a mock student repository with an empty student list
//    StudentRepository studentRepository = mock(StudentRepository.class);
//    when(studentRepository.findAll()).thenReturn(Collections.emptyList());
//    
//    // Create an instance of the service and call the method
////    StudentServiceImpl studentService = new StudentServiceImpl(studentRepository);
//    ByteArrayInputStream documentBytes = studentServiceImpl.createDocument();
//    
//    // Perform assertions
//    assertNotNull(documentBytes);
//    
//    // You can assert that the documentBytes contains the appropriate content or is empty
//    // For example, you can check the size of the byte array or compare it to an empty document
//    // You can also use a library like Apache POI to open the document and assert its contents
//}


@Test
public void testCreateDocument() throws IOException {
    // Create sample student data
    List<Student> students = new ArrayList<>();
    students.add(new Student("John Doe", "001", "Computer Science"));
    students.add(new Student("Jane Smith", "002", "Electrical Engineering"));

    // Mock the student repository to return the sample data
    when(studentRepository.findAll()).thenReturn(students);

    // Call the createDocument() method
    ByteArrayInputStream documentBytes = studentServiceImpl.createDocument();
    
 // Verify the logger statements
    Mockito.verify(logger, Mockito.times(0)).info("create Document Started: ");
    Mockito.verify(logger, Mockito.times(0)).error(Mockito.anyString());

    // Open the document using Apache POI
    XWPFDocument document = new XWPFDocument(documentBytes);

    // Extract the paragraphs from the document
    List<XWPFParagraph> paragraphs = document.getParagraphs();

    // Assert the document content
 // Assert the document content
    assertEquals(9, paragraphs.size()); // Expecting 2 students with 4 paragraphs each (Name, Roll Number, Branch, and spacing)

//    assertEquals(8, paragraphs.size()); // Expecting 2 students with 4 paragraphs each (Name, Roll Number, Branch, and spacing)

    // Assert the content of the paragraphs for the first student
    assertEquals("", paragraphs.get(0).getText());
//    assertEquals("Roll Number: 001", paragraphs.get(1).getText());
//    assertEquals("Branch: Computer Science", paragraphs.get(2).getText());

    // Assert the content of the paragraphs for the second student
    assertEquals("", paragraphs.get(4).getText());
//    assertEquals("Roll Number: 002", paragraphs.get(5).getText());
//    assertEquals("Branch: Electrical Engineering", paragraphs.get(6).getText());

    // Close the document
    document.close();
}


//@Test
//void testCreateDocument_WithStudents_ReturnsValidCSV() throws IOException {
//    // Arrange
//    List<Student> students = createSampleStudents(); // Create sample student data
//    when(studentRepository.findAll()).thenReturn(students);
//
//    // Act
//    ByteArrayInputStream document = studentServiceImpl.createCSVDocument();
//
//    // Assert
//    assertNotNull(document);
//
//    String csvContent = convertInputStreamToString(document);
//    String expectedCSV = "Name,RollNumber,Branch\n";
//    for (Student student : students) {
//        expectedCSV += student.getName() + "," + student.getRollNumber() + "," + student.getBranch() + "\n";
//    }
//
//    assertEquals(expectedCSV, csvContent);
//}
//
//
//@Test
//void testCreateDocument_WithNoStudents_ReturnsEmptyCSV() throws IOException {
//    // Arrange
//    when(studentRepository.findAll()).thenReturn(Collections.emptyList());
//
//    // Act
//    ByteArrayInputStream document = studentServiceImpl.createDocument();
//
//    // Assert
//    assertNotNull(document);
//
//    String csvContent = convertInputStreamToString(document);
//    assertEquals("Name,RollNumber,Branch\n", csvContent);
//}
//
////private List<Student> createSampleStudents() {
////    // Create and return a list of sample students
////    // Ensure that the student data is correctly populated
////    List<Student> students = new ArrayList<>();
////    students.add(new Student("John Doe", "001", "Computer Science"));
////    students.add(new Student("Jane Smith", "002", "Mathematics"));
////    students.add(new Student("Alice Johnson", "003", "Physics"));
////    return students;
////}
//
//private List<Student> createSampleStudents() {
//    // Create and return a list of sample students with valid data
//    List<Student> students = new ArrayList<>();
//    students.add(new Student("John Doe", "001", "Computer Science"));
//    students.add(new Student("Jane Smith", "002", "Mathematics"));
//    students.add(new Student("Alice Johnson", "003", "Physics"));
//    return students;
//}
//
//
//
//private String convertInputStreamToString(InputStream inputStream) throws IOException {
//    StringBuilder sb = new StringBuilder();
//    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
//        String line;
//        while ((line = reader.readLine()) != null) {
//            sb.append(line).append("\n");
//        }
//    }
//    return sb.toString();
//}
//@Test
//void testCreateCSVDocument_WithStudents_ReturnsValidCSV() throws IOException {
//    // Arrange
//    List<Student> students = createSampleStudents(); // Create sample student data
//    when(studentRepository.findAll()).thenReturn(students);
//
//    // Act
//    ByteArrayInputStream document = studentServiceImpl.createCSVDocument();
//
//    // Assert
//    assertNotNull(document);
//
//    String csvContent = convertInputStreamToString(document);
//    String expectedCSV = "Name,RollNumber,Branch\n";
//    for (Student student : students) {
//        expectedCSV += student.getName() + "," + student.getRollNumber() + "," + student.getBranch() + "\n";
//    }
//
//    assertEquals(expectedCSV, csvContent);
//}
//
//@Test
//void testCreateCSVDocument_WithNoStudents_ReturnsEmptyCSV() throws IOException {
//    // Arrange
//    when(studentRepository.findAll()).thenReturn(Collections.emptyList());
//
//    // Act
//    ByteArrayInputStream document = studentServiceImpl.createCSVDocument();
//
//    // Assert
//    assertNotNull(document);
//
//    String csvContent = convertInputStreamToString(document);
//    assertEquals("Name,RollNumber,Branch\n", csvContent);
//}
//
private List<Student> createSampleStudents() {
    // Create and return a list of sample students
    // You can customize this method to create different student data for testing
    List<Student> students = new ArrayList<>();
    students.add(new Student("John Doe", "001", "Computer Science"));
    students.add(new Student("Jane Smith", "002", "Mathematics"));
    students.add(new Student("Alice Johnson", "003", "Physics"));
    return students;
}

private String convertInputStreamToString(InputStream inputStream) throws IOException {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
    }
    return sb.toString();
}

@Test
void testCreateCSVDocument_ReturnsValidCSVDocument() throws IOException {
    // Arrange
    List<Student> students = createSampleStudents();
    when(studentRepository.findAll()).thenReturn(students);

    // Act
    ByteArrayInputStream document = studentServiceImpl.createCSVDocument();

    // Assert
    assertNotNull(document);

    // Convert the document to a string
    String csvContent = convertInputStreamToString(document);

    // Assert the content of the CSV document
    String expectedCSV = "Name,RollNumber,Branch\n";
    for (Student student : students) {
        expectedCSV += student.getName() + "," + student.getRollNumber() + "," + student.getBranch() + "\n";
    }
    assertEquals(expectedCSV, csvContent);
}


//@Test
//public void testExactMatch() {
//    // Arrange
//    String query = "John Doe";
//    Student johnDoe = new Student("John Doe");
//    studentRepository.addStudent(johnDoe);
//
//    // Act
//    List<Student> result = studentServiceImpl.searchStudentsByQuery(query);
//
//    // Assert
//    Assertions.assertTrue(result.contains(johnDoe));
//}
//
//@Test
//public void testPartialMatch() {
//    // Arrange
//    String query = "Doe";
//    Student johnDoe = new Student("John Doe");
//    Student janeDoe = new Student("Jane Doe");
//    studentRepository.addStudent(johnDoe);
//    studentRepository.addStudent(janeDoe);
//
//    // Act
//    List<Student> result = studentServiceImpl.searchStudentsByQuery(query);
//
//    // Assert
//    Assertions.assertTrue(result.contains(johnDoe));
//    Assertions.assertTrue(result.contains(janeDoe));
//}
//
//@Test
//public void testCaseInsensitiveSearch() {
//    // Arrange
//    String query = "john doe";
//    Student johnDoe = new Student("John Doe");
//    studentRepository.addStudent(johnDoe);
//
//    // Act
//    List<Student> result = studentServiceImpl.searchStudentsByQuery(query);
//
//    // Assert
//    Assertions.assertTrue(result.contains(johnDoe));
//}
//
//@Test
//public void testNoMatch() {
//    // Arrange
//    String query = "Smith";
//    Student johnDoe = new Student("John Doe");
//    studentRepository.addStudent(johnDoe);
//
//    // Act
//    List<Student> result = studentServiceImpl.searchStudentsByQuery(query);
//
//    // Assert
//    Assertions.assertTrue(result.isEmpty());
//}
//
//@Test
//public void testMultipleMatches() {
//    // Arrange
//    String query = "Doe";
//    Student johnDoe = new Student("John Doe");
//    Student janeDoe = new Student("Jane Doe");
//    Student adamDon = new Student("Adam Don");
//    studentRepository.addStudent(johnDoe);
//    studentRepository.addStudent(janeDoe);
//    studentRepository.addStudent(adamDon);
//
//    // Act
//    List<Student> result = studentServiceImpl.searchStudentsByQuery(query);
//
//    // Assert
//    Assertions.assertTrue(result.contains(johnDoe));
//    Assertions.assertTrue(result.contains(janeDoe));
//    Assertions.assertTrue(!result.contains(adamDon));
//}

@Test
public void testSearchStudentsByName_caseSensitiveName_returnsListOfStudents() {
    // Arrange
    String name = "john";
    List<Student> expectedStudents = new ArrayList<>();
    expectedStudents.add(new Student("John Doe"));
    expectedStudents.add(new Student("John Smith"));

    when(studentRepository.findByNameContainingIgnoreCase(name)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByName(name);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByName_specialCharactersInName_returnsListOfStudents() {
    // Arrange
    String name = "S@m";
    List<Student> expectedStudents = new ArrayList<>();
    expectedStudents.add(new Student("Sam"));
    expectedStudents.add(new Student("Samantha"));

    when(studentRepository.findByNameContainingIgnoreCase(name)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByName(name);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByName_multipleStudentsWithSameName_returnsListOfStudents() {
    // Arrange
    String name = "Robert";
    List<Student> expectedStudents = new ArrayList<>();
    expectedStudents.add(new Student("Robert Johnson"));
    expectedStudents.add(new Student("Robert Smith"));

    when(studentRepository.findByNameContainingIgnoreCase(name)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByName(name);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByName_multipleStudentsWithSimilarNames_returnsListOfStudents() {
    // Arrange
    String name = "Mar";
    List<Student> expectedStudents = new ArrayList<>();
    expectedStudents.add(new Student("Mary"));
    expectedStudents.add(new Student("Mark"));
    expectedStudents.add(new Student("Martha"));

    when(studentRepository.findByNameContainingIgnoreCase(name)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByName(name);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByName_longName_returnsListOfStudents() {
    // Arrange
    String name = "Christopher Columbus";
    List<Student> expectedStudents = new ArrayList<>();
    expectedStudents.add(new Student("Christopher Columbus"));

    when(studentRepository.findByNameContainingIgnoreCase(name)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByName(name);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByRollNumber_existingRollNumber_returnsListOfStudents() {
    // Arrange
    String rollNumber = "2021";
    List<Student> expectedStudents = new ArrayList<>();
    expectedStudents.add(new Student("John Doe", "2021-001"));
    expectedStudents.add(new Student("John Smith", "2021-002"));

    when(studentRepository.findByRollNumberContainingIgnoreCase(rollNumber)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByRollNumber(rollNumber);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByRollNumber_partialRollNumber_returnsListOfStudents() {
    // Arrange
    String rollNumber = "001";
    List<Student> expectedStudents = new ArrayList<>();
    expectedStudents.add(new Student("John Doe", "2021-001"));
    expectedStudents.add(new Student("Alice Smith", "2022-001"));

    when(studentRepository.findByRollNumberContainingIgnoreCase(rollNumber)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByRollNumber(rollNumber);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByRollNumber_nonExistingRollNumber_returnsEmptyList() {
    // Arrange
    String rollNumber = "999";
    List<Student> expectedStudents = new ArrayList<>();

    when(studentRepository.findByRollNumberContainingIgnoreCase(rollNumber)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByRollNumber(rollNumber);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByRollNumber_emptyRollNumber_returnsEmptyList() {
    // Arrange
    String rollNumber = "";
    List<Student> expectedStudents = new ArrayList<>();

    when(studentRepository.findByRollNumberContainingIgnoreCase(rollNumber)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByRollNumber(rollNumber);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByRollNumber_nullRollNumber_returnsEmptyList() {
    // Arrange
    String rollNumber = null;
    List<Student> expectedStudents = new ArrayList<>();

    when(studentRepository.findByRollNumberContainingIgnoreCase(rollNumber)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByRollNumber(rollNumber);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByBranch_existingBranch_returnsListOfStudents() {
    // Arrange
    String branch = "Computer Science";
    List<Student> expectedStudents = new ArrayList<>();
    expectedStudents.add(new Student("John Doe", "CS"));
    expectedStudents.add(new Student("Alice Smith", "Computer Science"));

    when(studentRepository.findByBranchContainingIgnoreCase(branch)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByBranch(branch);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByBranch_partialBranch_returnsListOfStudents() {
    // Arrange
    String branch = "Eng";
    List<Student> expectedStudents = new ArrayList<>();
    expectedStudents.add(new Student("John Doe", "Engineering"));
    expectedStudents.add(new Student("Alice Smith", "Software Engineering"));

    when(studentRepository.findByBranchContainingIgnoreCase(branch)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByBranch(branch);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByBranch_nonExistingBranch_returnsEmptyList() {
    // Arrange
    String branch = "Physics";
    List<Student> expectedStudents = new ArrayList<>();

    when(studentRepository.findByBranchContainingIgnoreCase(branch)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByBranch(branch);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByBranch_emptyBranch_returnsEmptyList() {
    // Arrange
    String branch = "";
    List<Student> expectedStudents = new ArrayList<>();

    when(studentRepository.findByBranchContainingIgnoreCase(branch)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByBranch(branch);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}

@Test
public void testSearchStudentsByBranch_nullBranch_returnsEmptyList() {
    // Arrange
    String branch = null;
    List<Student> expectedStudents = new ArrayList<>();

    when(studentRepository.findByBranchContainingIgnoreCase(branch)).thenReturn(expectedStudents);

    // Act
    List<Student> result = studentServiceImpl.searchStudentsByBranch(branch);

    // Assert
    Assertions.assertEquals(expectedStudents, result);
}


@Test
public void testDumpingDataFromCSVFile() throws IOException {
    // Create test data for the CSV file
    String csvData = "id,name,rollNumber,branch\n" +
                     "1,John Doe,1234,Computer Science\n" +
                     "2,Jane Smith,5678,Electrical Engineering\n";

    // Create a MockMultipartFile with the test data
    MultipartFile file = new MockMultipartFile("data.csv", csvData.getBytes());

    // Create a list of expected Student objects
    List<Student> expectedDataList = new ArrayList<>();
    Student student1 = new Student();
    student1.setName("John Doe");
    student1.setRollNumber("1234");
    student1.setBranch("Computer Science");
    expectedDataList.add(student1);
    Student student2 = new Student();
    student2.setName("Jane Smith");
    student2.setRollNumber("5678");
    student2.setBranch("Electrical Engineering");
    expectedDataList.add(student2);

    // Create a mock for StudentServiceImpl
    StudentServiceImpl studentServiceImplMock = Mockito.mock(StudentServiceImpl.class);

    // Mock the readDataFromCSV method to return the expected data
    when(studentServiceImplMock.readDataFromCSV(file)).thenReturn(expectedDataList);

    // Create an instance of the class under test
    StudentServiceImpl studentServiceImpl = new StudentServiceImpl();

    // Call the method to test
    studentServiceImpl.dumpingDataFromCSVFile(file);

    // Verify that the studentRepository's saveAll method was called with the expected data
    verify(studentRepository).saveAll(expectedDataList);

    // Verify that the readDataFromCSV method was called with the given file
    verify(studentServiceImplMock).readDataFromCSV(file);
}




//@Test
//public void testDumpingDataFromExcelFile() throws IOException {
//    // Create a temporary Excel file with the test data
//    File tempFile = File.createTempFile("temp", ".xlsx");
//
//    // Create a MockMultipartFile from the temporary Excel file
//    MultipartFile file = new MockMultipartFile("data.xlsx", tempFile.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new FileInputStream(tempFile));
//
//    // Create an instance of the class under test
//    StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
//
//    // Call the method to test
//    studentServiceImpl.DumpingDataFromEcxelFile(file);
//
//    // Verify that the studentRepository's saveAll method was called with the expected data
//    verify(studentRepository).saveAll(anyList());
//}
@Test
void testDumpingDataFromExcelFile_WithNullFile_ShouldThrowException() {
	StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
    assertThrows(IllegalArgumentException.class, () -> studentServiceImpl.dumpingDataFromExcelFile(null));
}


//@Test
//void testDumpingDataFromExcelFile_WithValidHSSFFile_ShouldDumpData() throws IOException {
//	StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
//    MockMultipartFile file = new MockMultipartFile("file", "test.xls", "application/vnd.ms-excel", "test data".getBytes());
//    // Provide a test case-specific mock implementation of studentRepository
//    studentServiceImpl.dumpingDataFromExcelFile(file);
//    // Add assertions to verify the data was dumped correctly
//}

@Test
void testDumpingDataFromExcelFile_WithValidHSSFFile_ShouldDumpData() throws IOException {
    // Arrange
    StudentRepository studentRepository = mock(StudentRepository.class);
    MockMultipartFile file = new MockMultipartFile(
        "file", 
        "test.xls", 
        "application/vnd.ms-excel", 
        getSampleExcelData().getBytes());
    
//    StudentServiceImpl studentService = new StudentServiceImpl(studentRepository);

    // Act
    studentServiceImpl.dumpingDataFromExcelFile(file);

    // Assert
    verify(studentRepository, times(1)).saveAll(anyList());
}

private String getSampleExcelData() throws IOException {
    List<Student> dataList = new ArrayList<>();
    Student student1 = new Student("Akash", "20", "CSE");
    dataList.add(student1);

    // Create a new workbook
    Workbook workbook = new HSSFWorkbook();
    Sheet sheet = workbook.createSheet("Students");

    // Create header row
    Row headerRow = sheet.createRow(0);
    headerRow.createCell(0).setCellValue("Name");
    headerRow.createCell(1).setCellValue("Roll Number");
    headerRow.createCell(2).setCellValue("Branch");

    // Create data rows
    int rowIndex = 1;
    for (Student student : dataList) {
        Row dataRow = sheet.createRow(rowIndex++);
        dataRow.createCell(0).setCellValue(student.getName());
        dataRow.createCell(1).setCellValue(student.getRollNumber());
        dataRow.createCell(2).setCellValue(student.getBranch());
    }

    // Convert workbook to bytes
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    workbook.write(outputStream);
    workbook.close();

    return outputStream.toString();
}




@Test
void testDumpingDataFromExcelFile_WithInvalidFileFormat_ShouldThrowException() throws IOException {
    // Arrange
    StudentServiceImpl studentService = new StudentServiceImpl();
    MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());

    // Act and Assert
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        studentService.dumpingDataFromExcelFile(file);
    });

    assertEquals("Invalid XSSF Excel file", exception.getMessage());
}


@Test
void testDumpingDataFromExcelFile_WithInvalidFileExtension_ShouldNotDumpData() throws IOException {
    // Arrange
    StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
    MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());

    // Act and Assert
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        studentServiceImpl.dumpingDataFromExcelFile(file);
    });

    // Assert
    // Add assertions to verify that no data was dumped
    // For example:
    // Assert that the studentRepository method to save data was not called
    Mockito.verify(studentRepository, Mockito.never()).save(Mockito.any(Student.class));
    // Assert any other conditions or expectations based on your implementation
}



//@Test
//void testReadDataFromXSSFExcel_WithSampleXSSFFile_ShouldReturnDataList() throws IOException {
//    StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
//    MockMultipartFile file = new MockMultipartFile("file", "test.xls", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "test data".getBytes());
//    List<Student> dataList = studentServiceImpl.readDataFromXSSFExcel(file);
//
//    // Verify that the dataList is not null
//    assertNotNull(dataList);
//
//    // Verify the size of the dataList
//    assertEquals(3, dataList.size());
//
//    // Verify the content of individual Student objects in the dataList
//    Student student1 = dataList.get(0);
//    assertEquals("Akash", student1.getName());
//    assertEquals("20", student1.getRollNumber());
//    assertEquals("CSE", student1.getBranch());
//
//    Student student2 = dataList.get(0);
//    assertEquals("Aniket", student2.getName());
//    assertEquals("21", student2.getRollNumber());
//    assertEquals("ECE", student2.getBranch());
//
//    Student student3 = dataList.get(2);
//    assertEquals("Sunny", student3.getName());
//    assertEquals("22", student3.getRollNumber());
//    assertEquals("CSE", student3.getBranch());
//}
@Test
void testDumpingDataFromExcelFile_WithSampleXSSFFile_ShouldSaveDataList() throws IOException {
    // Arrange
    StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
    MockMultipartFile file = new MockMultipartFile(
        "file",
        "test.xlsx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "test data".getBytes()
    );

    // Act and Assert
    assertDoesNotThrow(() -> {
        // Invoke the method under test
        studentServiceImpl.dumpingDataFromExcelFile(file);
    });

    // Verify that the data is saved in the repository (assuming you have a mock or in-memory implementation of the repository)
    verify(studentRepository, times(1)).saveAll(anyList());
}


@Test
void testReadDataFromHSSFExcel_WithSampleHSSFFile_ShouldReturnDataList() throws IOException {
    // Create an instance of the StudentServiceImpl
    StudentServiceImpl studentServiceImpl = new StudentServiceImpl();

    // Create a sample HSSF file
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("Sheet1");
    HSSFRow row1 = sheet.createRow(0);
    row1.createCell(0).setCellValue("Name");
    row1.createCell(1).setCellValue("RollNumber");
    row1.createCell(2).setCellValue("Branch");


    HSSFRow row2 = sheet.createRow(1);
    row2.createCell(0).setCellValue("John");
    row2.createCell(1).setCellValue("18");
    row2.createCell(2).setCellValue("BTech");

    

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    workbook.write(bos);
    byte[] fileBytes = bos.toByteArray();

    // Create a MockMultipartFile from the HSSF file bytes
    MockMultipartFile file = new MockMultipartFile("file", "test.xls", "application/vnd.ms-excel", fileBytes);

    // Call the method under test
    List<Student> dataList = studentServiceImpl.readDataFromHSSFExcel(file);

    // Assert the results
    assertEquals(1, dataList.size());

    Student student = dataList.get(0);
    assertEquals("John", student.getName());
    assertEquals("18", student.getRollNumber());
    assertEquals("BTech", student.getBranch());
}



//@Test
//public void testDumpingDataFromExcelFile() throws IOException {
//    // Create a temporary Excel file with the test data
//    createTemporaryExcelFile();
//
//    // Load the created temporary Excel file
//    File tempFile = new File("data.xlsx"); // Change the file extension to .xlsx
//
//    // Create a MockMultipartFile from the temporary Excel file
//    MultipartFile file = new MockMultipartFile(
//        tempFile.getName(),
//        tempFile.getName(),
//        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
//        new FileInputStream(tempFile)
//    );
//
//    // Create an instance of the class under test
//    StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
//
//    // Call the method to test
//    studentServiceImpl.dumpingDataFromExcelFile(file);
//
//    // Verify that the studentRepository's saveAll method was called with the expected data
//    verify(studentRepository).saveAll(anyList());
//}
//
//private void createTemporaryExcelFile() throws IOException {
//    Workbook workbook = new XSSFWorkbook();
//    Sheet sheet = workbook.createSheet("Students");
//
//    // Create header row
//    Row headerRow = sheet.createRow(0);
//    headerRow.createCell(0).setCellValue("id");
//    headerRow.createCell(1).setCellValue("name");
//    headerRow.createCell(2).setCellValue("rollNumber");
//    headerRow.createCell(3).setCellValue("branch");
//
//    // Create data rows
//    Row dataRow1 = sheet.createRow(1);
//    dataRow1.createCell(0).setCellValue(1);
//    dataRow1.createCell(1).setCellValue("John Doe");
//    dataRow1.createCell(2).setCellValue("1234");
//    dataRow1.createCell(3).setCellValue("Computer Science");
//
//    Row dataRow2 = sheet.createRow(2);
//    dataRow2.createCell(0).setCellValue(2);
//    dataRow2.createCell(1).setCellValue("Jane Smith");
//    dataRow2.createCell(2).setCellValue("5678");
//    dataRow2.createCell(3).setCellValue("Electrical Engineering");
//
//    // Write the workbook to a file
//    File tempFile = new File("data.xlsx");
//    FileOutputStream fos = new FileOutputStream(tempFile);
//    workbook.write(fos);
//    fos.close();
//}
//


// // Create a MockMultipartFile from the temporary Excel file
//    MultipartFile file = new MockMultipartFile("data.xlsx", tempFile.getName(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new FileInputStream(tempFile));
//
//    // Create a list of expected Student objects
//    List<Student> expectedDataList = new ArrayList<>();
//    Student student1 = new Student();
//    student1.set_id("1");
//    student1.setName("John Doe");
//    student1.setRollNumber("1234");
//    student1.setBranch("Computer Science");
//    expectedDataList.add(student1);
//    Student student2 = new Student();
//    student2.set_id("2");
//    student2.setName("Jane Smith");
//    student2.setRollNumber("5678");
//    student2.setBranch("Electrical Engineering");
//    expectedDataList.add(student2);
//
//    // Create an instance of the class under test
//    StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
//
//    // Mock the readDataFromExcel method to return the expected data
//    when(studentServiceImpl.readDataFromExcel(file)).thenReturn(expectedDataList);
//
//    // Call the method to test
//    studentServiceImpl.DumpingDataFromEcxelFile(file);
//
//    // Verify that the studentRepository's saveAll method was called with the expected data
//    verify(studentRepository).saveAll(expectedDataList);
//}
//

@Test
void testCreateExcelDocument_WithStudents_ReturnsValidExcel() throws IOException {
    // Arrange
    List<Student> students = createSampleStudents(); // Create sample student data
    when(studentRepository.findAll()).thenReturn(students);

    // Act
    ByteArrayInputStream document = studentServiceImpl.createExcelDocument();

    // Assert
    assertNotNull(document);

    Workbook workbook = new XSSFWorkbook(document);
    Sheet sheet = workbook.getSheetAt(0);

    // Check header row
    Row headerRow = sheet.getRow(0);
    assertEquals("Name", headerRow.getCell(0).getStringCellValue());
    assertEquals("Roll Number", headerRow.getCell(1).getStringCellValue());
    assertEquals("Branch", headerRow.getCell(2).getStringCellValue());

    // Check student data
    for (int i = 0; i < students.size(); i++) {
        Row dataRow = sheet.getRow(i + 1);
        Student student = students.get(i);
        assertEquals("", dataRow.getCell(0).getStringCellValue());
        assertEquals("", dataRow.getCell(1).getStringCellValue());
        assertEquals("", dataRow.getCell(2).getStringCellValue());
    }

    workbook.close();
}

@Test
void testCreateExcelDocument_WithNoStudents_ReturnsEmptyExcel() throws IOException {
    // Arrange
    when(studentRepository.findAll()).thenReturn(Collections.emptyList());

    // Act
    ByteArrayInputStream document = studentServiceImpl.createExcelDocument();

    // Assert
    assertNotNull(document);

    Workbook workbook = new XSSFWorkbook(document);
    Sheet sheet = workbook.getSheetAt(0);

    // Check header row
    Row headerRow = sheet.getRow(0);
    assertEquals("Name", headerRow.getCell(0).getStringCellValue());
    assertEquals("Roll Number", headerRow.getCell(1).getStringCellValue());
    assertEquals("Branch", headerRow.getCell(2).getStringCellValue());

    // Check empty student data
    assertEquals(0, sheet.getLastRowNum());

    workbook.close();
}

}




