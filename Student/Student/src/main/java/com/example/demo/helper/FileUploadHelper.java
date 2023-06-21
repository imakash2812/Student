package com.example.demo.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.repo.StudentRepository;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

@Component
public class FileUploadHelper {
	
//	
//	    @Autowired
//	    private StudentRepository mongoDbFactory;
		//Our files are stored in this folder
		//static folder
		public final String UPLOAD_DIR="C:\\Users\\AkashMaurya\\Desktop\\Student\\Student\\src\\main\\resources\\static";


//	    public boolean uploadFileDB(MultipartFile file) {
//	        try {
//	            InputStream inputStream = file.getInputStream();
//
//	            // Set the GridFS upload options
//	            GridFSUploadOptions options = new GridFSUploadOptions()
//	                    .chunkSizeBytes(1024)
//	                    .metadata(new Document("contentType", file.getContentType()));
//
//	            // Get the GridFS bucket from the MongoDbFactory
//	            GridFSBucket gridFSBucket = GridFSBuckets.create(mongoDbFactory.getDb());
//
//	            // Create a new GridFS upload stream
//	            GridFSUploadStream uploadStream = gridFSBucket.openUploadStream(file.getOriginalFilename(), options);
//
//	            // Upload the file to GridFS in chunks
//	            byte[] buffer = new byte[1024];
//	            int bytesRead;
//	            while ((bytesRead = inputStream.read(buffer)) != -1) {
//	                uploadStream.write(buffer, 0, bytesRead);
//	            }
//	            uploadStream.close();
//
//	            return true;
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	        return false;
//	    }
//	
//
//	

	public boolean uploadFile(MultipartFile file) {
		boolean f=false;	//false means file is not uploaded yet
		try {
//			
//			InputStream is=file.getInputStream();	//read data with inputstream ands write on to the directory path
//			byte data[]=new byte[is.available()];	//we are creating an bytes array which is equals to data that we are getting from input
//			is.read(data);	//we are reading data using this method
//			
//			//Write data after reading
//			FileOutputStream fos=new FileOutputStream(UPLOAD_DIR+File.separator+file.getOriginalFilename());
//			fos.write(data);
//			
//			fos.flush();
//			fos.close();
//===============================Alternative method to perform the same operation with less code======================
			
			Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING );
			
			f=true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	
	
	
}
