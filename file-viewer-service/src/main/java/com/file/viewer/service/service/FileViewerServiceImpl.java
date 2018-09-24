package com.file.viewer.service.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.file.viewer.service.entity.DropBoxConnection;
import com.file.viewer.service.entity.DropBoxRecord;
import com.file.viewer.service.providers.DropBoxDocumentProvider;
import com.google.gson.Gson;

@Service("fileViewerService")
public class FileViewerServiceImpl implements FileViewerService {

	@Override
	public String getFilesFromFolder(String accessKey, String clientId, String folder) {
		DropBoxConnection connection = new DropBoxConnection(accessKey, clientId);
		DropBoxDocumentProvider documentProvider = new DropBoxDocumentProvider(connection);
		List<DropBoxRecord> dropBoxRecords = documentProvider.getFilesFromFolder(folder);
		Gson gson = new Gson();
		return gson.toJson(dropBoxRecords, List.class);
	}

	@Override
	public String downloadFileFromServer(String accessKey, String clientId, String fileName, String localPath) {
		DropBoxConnection connection = new DropBoxConnection(accessKey, clientId);
		DropBoxDocumentProvider documentProvider = new DropBoxDocumentProvider(connection);
		return documentProvider.downloadFileFromAFolder(fileName, localPath);
	}

	@Override
	public String downloadFolderFromServer(String accessKey, String clientId, String folderName, String localPath) {
		DropBoxConnection connection = new DropBoxConnection(accessKey, clientId);
		DropBoxDocumentProvider documentProvider = new DropBoxDocumentProvider(connection);
		return documentProvider.downloadAllFilesFromFolder(folderName, localPath);
	}

	@Override
	public String uploadContent(String accessKey, String clientId, String serverFolderName, String fileName) {
		DropBoxConnection connection = new DropBoxConnection(accessKey, clientId);
		DropBoxDocumentProvider documentProvider = new DropBoxDocumentProvider(connection);
		boolean uploadStatus = documentProvider.uploadAFile(serverFolderName, fileName);
		JSONObject responseJson = new JSONObject();
		if(uploadStatus){
			responseJson.put("Status", "Uploaded successfully");
		}else{
			responseJson.put("status", "Upload failed");
		}
		return responseJson.toString();
	}
}
