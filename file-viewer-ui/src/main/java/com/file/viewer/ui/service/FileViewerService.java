package com.file.viewer.ui.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.file.viewer.ui.entity.DropBoxRecord;

public interface FileViewerService {

	public String downloadFileFromServer(String accessKey, String clientId, String pathValue, String localPath);

	public String uploadFile(String accessKey, String clientId, String serverPath, MultipartFile file);

	public List<DropBoxRecord> getContentsOfFolder(String accessKey, String clientId, String folderName);
	
	public String downloadFolderContents(String accessKey, String clientId, String pathValue, String localPath);
}
