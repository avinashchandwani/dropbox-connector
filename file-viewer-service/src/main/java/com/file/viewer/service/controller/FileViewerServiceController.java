package com.file.viewer.service.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.file.viewer.service.entity.ServiceProperties;
import com.file.viewer.service.service.FileViewerService;
/**
 * File-Viewer-Service Controller
 * @author Avinash Chandwani
 *
 */
@RestController
@RequestMapping("fileviewerservice")
public class FileViewerServiceController {

	@Autowired
	private FileViewerService fileViewerService;

	@Autowired
	private ServiceProperties serviceProperties;

	@GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String testService() {
		JSONObject json = new JSONObject();
		json.put("Message", "Service is working correctly");
		return json.toString();
	}

	@PostMapping(value = "/getFilesFromFolder", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getFilesFromFolder(@RequestParam String accessKey, @RequestParam String clientId,
			@RequestParam String folderName) {
		return fileViewerService.getFilesFromFolder(accessKey, clientId, folderName);
	}

	@PostMapping(value = "/downloadFileFromFolder")
	public ResponseEntity<InputStreamResource> downloadFileFromFolder(@RequestParam String accessKey,
			@RequestParam String clientId, @RequestParam String fileName) {
		String localPath = serviceProperties.getPropertyValue("intermediate.folder");
		String localFilePath = fileViewerService.downloadFileFromServer(accessKey, clientId, fileName, localPath);
		File localFile = new File(localFilePath);
		InputStreamResource isr;
		try {
			isr = new InputStreamResource(new FileInputStream(localFile));
			HttpHeaders respHeaders = new HttpHeaders();
			MediaType mediaType = MediaType.parseMediaType("application/pdf");
			respHeaders.setContentType(mediaType);
			respHeaders.setContentLength(localFile.length());
			respHeaders.setContentDispositionFormData("attachment", localFile.getName());
			return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		} catch (FileNotFoundException e) {

		}
		return null;
	}

	@PostMapping(value = "/downloadFolder")
	public ResponseEntity<InputStreamResource> downloadFolder(@RequestParam String accessKey,
			@RequestParam String clientId, @RequestParam String folderName) {
		String localPath = serviceProperties.getPropertyValue("intermediate.folder");
		String localFilePath = fileViewerService.downloadFolderFromServer(accessKey, clientId, folderName, localPath);
		File localFile = new File(localFilePath);
		InputStreamResource isr;
		try {
			isr = new InputStreamResource(new FileInputStream(localFile));
			HttpHeaders respHeaders = new HttpHeaders();
			MediaType mediaType = MediaType.parseMediaType("application/zip");
			respHeaders.setContentType(mediaType);
			respHeaders.setContentLength(localFile.length());
			respHeaders.setContentDispositionFormData("attachment", localFile.getName());
			return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping(value = "/upload", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String uploadFile(@RequestParam String accessKey, @RequestParam String clientId,
			@RequestParam String serverFolderName, @RequestParam MultipartFile file) {
		String uploadDirectory = serviceProperties.getPropertyValue("intermediate.folder");
		File serverFile = new File(uploadDirectory + File.separator + file.getOriginalFilename());
		BufferedOutputStream stream =null;
		try {
			serverFile.createNewFile();
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(file.getBytes());
			stream.close();
			return fileViewerService.uploadContent(accessKey, clientId, serverFolderName,
					serverFile.getAbsoluteFile().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(stream!=null)
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
}