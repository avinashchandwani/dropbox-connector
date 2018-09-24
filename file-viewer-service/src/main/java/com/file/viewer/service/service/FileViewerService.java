package com.file.viewer.service.service;

public interface FileViewerService {

	public String getFilesFromFolder(String accessKey, String clientId, String folder);

	public String downloadFileFromServer(String accessKey, String clientId, String fileName, String localPath);

	public String downloadFolderFromServer(String accessKey, String clientId, String folderName, String localPath);
	
	public String uploadContent(String accessKey, String clientId, String serverFolderName, String fileName);

}
