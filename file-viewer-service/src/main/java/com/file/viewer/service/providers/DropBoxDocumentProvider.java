package com.file.viewer.service.providers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DownloadZipResult;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;
import com.file.viewer.service.entity.DropBoxConnection;
import com.file.viewer.service.entity.DropBoxRecord;

public class DropBoxDocumentProvider {
	private DropBoxConnection connection;

	public DropBoxDocumentProvider(DropBoxConnection connection) {
		this.connection = connection;
	}

	private DbxClientV2 getClient() {
		DbxRequestConfig config = DbxRequestConfig.newBuilder(this.connection.getClientId()).build();
		DbxClientV2 client = new DbxClientV2(config, this.connection.getAccessKey());
		return client;
	}

	public void testConnection() {
	}

	@SuppressWarnings("unused")
	/**
	 * Created for testing purpose
	 * 
	 * @throws DbxApiException
	 * @throws DbxException
	 */
	private static void getUserName() throws DbxApiException, DbxException {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("api-dropbox-testing").build();
		DbxClientV2 client = new DbxClientV2(config,
				"PhW0abwecvAAAAAAAAACZabskebC8pTlsdZfzXIXvILwQVGCln9Kt76y3QdNKGIe");
		FullAccount account = client.users().getCurrentAccount();
		System.out.println(account.getName().getDisplayName());
		System.out.println(account.getName().getSurname());
	}

	public boolean uploadAFile(String serverFolderName, String fileName) {
		boolean isUploaded = false;
		if(serverFolderName.isEmpty() || serverFolderName == null){
			serverFolderName = "/";
		}
		DbxClientV2 client = getClient();
		InputStream in = null;
		try {
			in = new FileInputStream(fileName);
			File sourceFile = new File(fileName);
			System.out.println(sourceFile.getName());
			client.files().uploadBuilder(serverFolderName + sourceFile.getName()).uploadAndFinish(in);
			isUploaded = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UploadErrorException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isUploaded;
	}

	public String downloadAllFilesFromFolder(String sourceFolderName, String localPath) {
		DbxClientV2 client = getClient();
		DbxDownloader<DownloadZipResult> downloadResult = null;
		OutputStream downloadZipStream = null;
		File sourceFolder = new File(sourceFolderName);
		try {
			downloadZipStream = new FileOutputStream(localPath + File.separator + sourceFolder.getName() + ".zip");
			downloadResult = client.files().downloadZip(sourceFolderName);
			downloadResult.download(downloadZipStream);
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (downloadZipStream != null)
					downloadZipStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return localPath + File.separator + sourceFolder.getName() + ".zip";
	}

	public String downloadFileFromAFolder(String fileNameWithCompletePath, String localFilePath) {
		DbxClientV2 client = getClient();
		String localFile = null;
		OutputStream downloadFile = null;
		try {
			File sourceFile = new File(fileNameWithCompletePath);
			downloadFile = new FileOutputStream(localFilePath + sourceFile.getName());
			localFile = localFilePath + sourceFile.getName();
			client.files().downloadBuilder(fileNameWithCompletePath).download(downloadFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				downloadFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return localFile;
	}

	public List<DropBoxRecord> getFilesFromFolder(String folderName) {
		DbxClientV2 client = getClient();
		List<DropBoxRecord> filesAndFolders = new LinkedList<DropBoxRecord>();
		ListFolderResult result;
		try {
			result = client.files().listFolder(folderName);
			for (;;) {
				for (Metadata metadata : result.getEntries()) {
					DropBoxRecord dropBoxRecord = null;
					String type= null;
					if ((metadata instanceof FileMetadata)) {
						type = "file";
					} else if ((metadata instanceof FolderMetadata)) {
						type = "folder";
					}
					String path = metadata.getPathLower();
					dropBoxRecord = new DropBoxRecord(path.substring(0,path.lastIndexOf("/")+1), metadata.getName(), type);
					filesAndFolders.add(dropBoxRecord);
				}
				if (!result.getHasMore()) {
					break;
				}
				result = client.files().listFolderContinue(result.getCursor());
			}
		} catch (DbxException e) {
			e.printStackTrace();
		}
		return filesAndFolders;
	}
}