package com.file.viewer.ui.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.file.viewer.ui.entity.DropBoxConnection;
import com.file.viewer.ui.entity.DropBoxRecord;
import com.file.viewer.ui.entity.ServiceProperties;
import com.file.viewer.ui.service.FileViewerService;

@Controller
public class FileViewerUIController {

	@Autowired
	private ServiceProperties serviceProperties;

	@Autowired
	private FileViewerService fileViewerService;

	@Autowired
	private DropBoxConnection dropBoxConnection;

	@PostMapping(value = "/login")
	public String login(@RequestParam String dropBoxAccessKey, @RequestParam String clientId, ModelMap map,
			HttpServletRequest request) {
		System.out.println();
		dropBoxConnection.setAccessKey(dropBoxAccessKey);
		dropBoxConnection.setClientId(clientId);

		List<DropBoxRecord> files = null;
		map.addAttribute("currentPath", "");
		files = fileViewerService.getContentsOfFolder(
				"PhW0abwecvAAAAAAAAACZabskebC8pTlsdZfzXIXvILwQVGCln9Kt76y3QdNKGIe", "api-dropbox-testing", "");
		map.addAttribute("dropBoxRecord", files);
		return "files";
	}

	@GetMapping(value = "/")
	public String launchHomePage(ModelMap map) {
		System.out.println("Home Hit");
		return "home";
	}

	@GetMapping(value = "/viewPage")
	public String viewHomeFolder(ModelMap map) {
		List<DropBoxRecord> files = null;
		map.addAttribute("currentPath", "");
		files = fileViewerService.getContentsOfFolder(dropBoxConnection.getAccessKey(), dropBoxConnection.getClientId(),
				"");
		map.addAttribute("dropBoxRecord", files);
		return "files";
	}

	@GetMapping(value = "/downloadFile/")
	public ResponseEntity<InputStreamResource> downloadFile(@RequestParam(value = "pathValue") String pathValue,
			@RequestParam String fileType, ModelMap map) {
		String localPath = serviceProperties.getPropertyValue("intermediate.folder");
		if (fileType.equals("file")) {

			String localFilePath = fileViewerService.downloadFileFromServer(dropBoxConnection.getAccessKey(),
					dropBoxConnection.getClientId(), pathValue, localPath);
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
				// This will probably not come at any cost, a file available but
				// not
				// found when downloading is the use-case
			}
		} else if (fileType.equals("folder")) {
			InputStreamResource isr;
			try {
				String localFilePath = fileViewerService.downloadFolderContents(dropBoxConnection.getAccessKey(),
						dropBoxConnection.getClientId(), pathValue, localPath);
				File localFile = new File(localFilePath);

				isr = new InputStreamResource(new FileInputStream(localFile));
				HttpHeaders respHeaders = new HttpHeaders();
				MediaType mediaType = MediaType.parseMediaType("application/zip");
				respHeaders.setContentType(mediaType);
				respHeaders.setContentLength(localFile.length());
				respHeaders.setContentDispositionFormData("attachment", localFile.getName());
				return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
			} catch (FileNotFoundException e) {
				// This cannot occur, if folder is found and clicked, it should
				// be available at download
			}
		}

		return null;
	}

	@PostMapping(value = "/uploadFileAction")
	public String uploadFileAction(@RequestParam String path, @RequestParam MultipartFile file, ModelMap model) {
		if (file.isEmpty()) {
			model.addAttribute("failuremessage", "File is empty");
		} else {

			String message = fileViewerService.uploadFile(dropBoxConnection.getAccessKey(),
					dropBoxConnection.getClientId(), path, file);
			model.addAttribute("successmessage", message);
		}
		List<DropBoxRecord> files = null;
		model.addAttribute("currentPath", path);
		files = fileViewerService.getContentsOfFolder(dropBoxConnection.getAccessKey(), dropBoxConnection.getClientId(),
				path);
		model.addAttribute("dropBoxRecord", files);
		return "files";
	}

	@GetMapping(value = "/getContentsOfFolder")
	public String getContentsOfFolder(@RequestParam(value = "pathValue") String pathValue,
			@RequestParam String currentFolder, @RequestParam String fileType, ModelMap model) {
		if (fileType.equals("folder")) {
			List<DropBoxRecord> files = null;
			files = fileViewerService.getContentsOfFolder(dropBoxConnection.getAccessKey(),
					dropBoxConnection.getClientId(), pathValue + "/" + currentFolder);
			model.addAttribute("dropBoxRecord", files);
			model.addAttribute("currentPath", pathValue + currentFolder);
		} else if (fileType.equals("file")) {
			// This code is for viewing the file
		}
		return "files";
	}
}