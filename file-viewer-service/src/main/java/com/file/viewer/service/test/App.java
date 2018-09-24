package com.file.viewer.service.test;

import java.util.List;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.file.viewer.service.entity.DropBoxConnection;
import com.file.viewer.service.entity.DropBoxRecord;
import com.file.viewer.service.providers.DropBoxDocumentProvider;

public class App {
	private static final String ACCESS_TOKEN = "PhW0abwecvAAAAAAAAACZabskebC8pTlsdZfzXIXvILwQVGCln9Kt76y3QdNKGIe";
	private static final String clientId = "api-dropbox-testing";

	public static void main(String[] args) throws DbxApiException, DbxException {
		DropBoxConnection connection = new DropBoxConnection(ACCESS_TOKEN, clientId);
		DropBoxDocumentProvider documentProvider = new DropBoxDocumentProvider(connection);
		List<DropBoxRecord> records = documentProvider.getFilesFromFolder("");
		System.out.println(records);
		documentProvider.downloadFileFromAFolder("/gfg/Graphs.pdf", "C:\\Avinash\\personal\\prep\\CE\\");
		documentProvider.downloadAllFilesFromFolder("/gfg", "C:\\Avinash\\personal\\prep\\CE\\");
	}
}
