package com.file.viewer.ui.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.file.viewer.ui.entity.DropBoxRecord;
import com.file.viewer.ui.entity.ServiceProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service("fileViewerService")
public class FileViewerServiceImpl implements FileViewerService {

	@Autowired
	private ServiceProperties serviceProperties;

	@Autowired
	private RestTemplateBuilder restTemplate;

	@Override
	public String downloadFileFromServer(String accessKey, String clientId, String pathValue, String localPath) {
		String fileLocalPath = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_PDF));
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("accessKey", accessKey);
			map.add("clientId", clientId);
			map.add("fileName", pathValue);
			String downloadFileName = pathValue.substring(pathValue.lastIndexOf("/") + 1);
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);
			String protocol = serviceProperties.getPropertyValue("service.protocol");
			String hostName = serviceProperties.getPropertyValue("service.host");
			String portNumber = serviceProperties.getPropertyValue("service.port");
			String serviceBaseName = serviceProperties.getPropertyValue("service.root");
			ResponseEntity<byte[]> response = restTemplate.build().exchange(
					protocol + "://" + hostName + ":" + portNumber + "/" + serviceBaseName + "/downloadFileFromFolder",
					HttpMethod.POST, entity, byte[].class);
			Files.write(Paths.get(localPath + File.separator + downloadFileName), response.getBody());
			fileLocalPath = localPath + File.separator + downloadFileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileLocalPath;
	}
	
	@Override
	public String downloadFolderContents(String accessKey, String clientId, String pathValue, String localPath) {
		String fileLocalPath = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("accessKey", accessKey);
			map.add("clientId", clientId);
			map.add("fileName", pathValue);
			String downloadFileName = pathValue.substring(pathValue.lastIndexOf("/") + 1);
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);
			String protocol = serviceProperties.getPropertyValue("service.protocol");
			String hostName = serviceProperties.getPropertyValue("service.host");
			String portNumber = serviceProperties.getPropertyValue("service.port");
			String serviceBaseName = serviceProperties.getPropertyValue("service.root");
			ResponseEntity<byte[]> response = restTemplate.build().exchange(
					protocol + "://" + hostName + ":" + portNumber + "/" + serviceBaseName + "/downloadFolder",
					HttpMethod.POST, entity, byte[].class);
			Files.write(Paths.get(localPath + File.separator + downloadFileName), response.getBody());
			fileLocalPath = localPath + File.separator + downloadFileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileLocalPath;
	}

	@Override
	public String uploadFile(String accessKey, String clientId, String serverPath, MultipartFile file) {
		String responseMessage = null;
		String uploadDirectory = serviceProperties.getPropertyValue("intermediate.folder");
		File serverFile = new File(uploadDirectory + File.separator + file.getOriginalFilename());
		BufferedOutputStream stream = null;
		try {
			serverFile.createNewFile();
			stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(file.getBytes());
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null)
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("accessKey", accessKey);
			map.add("clientId", clientId);
			map.add("serverFolderName", serverPath);
			map.add("file", new FileSystemResource(serverFile.getAbsolutePath().toString()));
			HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map,
					headers);
			String protocol = serviceProperties.getPropertyValue("service.protocol");
			String hostName = serviceProperties.getPropertyValue("service.host");
			String portNumber = serviceProperties.getPropertyValue("service.port");
			String serviceBaseName = serviceProperties.getPropertyValue("service.root");
			ResponseEntity<String> response = restTemplate.build().exchange(
					protocol + "://" + hostName + ":" + portNumber + "/" + serviceBaseName + "/upload", HttpMethod.POST,
					entity, String.class);
			responseMessage = response.getBody();
			System.out.println(responseMessage);
			JSONObject responseObj = new JSONObject(responseMessage);
			responseMessage = responseObj.getString("Status");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseMessage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DropBoxRecord> getContentsOfFolder(String accessKey, String clientId, String folderName) {
		List<DropBoxRecord> records = null;
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("accessKey", accessKey);
		map.add("clientId", clientId);
		map.add("folderName", folderName);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(map,
				null);
		String protocol = serviceProperties.getPropertyValue("service.protocol");
		String hostName = serviceProperties.getPropertyValue("service.host");
		String portNumber = serviceProperties.getPropertyValue("service.port");
		String serviceBaseName = serviceProperties.getPropertyValue("service.root");
		
		ResponseEntity<String> response = restTemplate.build().exchange(
				protocol + "://" + hostName + ":" + portNumber + "/" + serviceBaseName + "/getFilesFromFolder",
				HttpMethod.POST, requestEntity, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			String responseBody = response.getBody();
			Gson gson = new GsonBuilder().create();
			records = gson.fromJson(responseBody, List.class);
			System.out.println(records);
		} else {

		}
		return records;
	}
}