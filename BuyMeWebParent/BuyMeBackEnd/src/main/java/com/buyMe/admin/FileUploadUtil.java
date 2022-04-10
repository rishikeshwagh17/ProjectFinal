package com.buyMe.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	//utility class so using static method
	//method to get get photo upload directory and and saving the file using IO handling
	public static void saveFile(String uploadDir,String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try (InputStream input = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new IOException("could not save file: " + fileName, e);
		}
	}
	
	public static void cleanDir(String dir) {
		Path dirPath = Paths.get(dir);
		
		try {
			Files.list(dirPath).forEach(file -> {
				if (!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("could not delete file : " + file);
					}
				}
			});
		} catch (IOException e) {
			System.out.println("could not list directory: " + dirPath);
		}
	}
}
