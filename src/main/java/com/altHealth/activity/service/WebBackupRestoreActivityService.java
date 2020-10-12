package com.altHealth.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.Utils.DirectoryReader;
import com.altHealth.activity.WebBackupRestoreActivity;
import com.altHealth.model.ReturnModel;

@Service
public class WebBackupRestoreActivityService implements WebBackupRestoreActivity {
	
	@Autowired
	DirectoryReader directoryReader;

	@Override
	public ReturnModel findAll() {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
//		returnModel.setEntity(client);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		List<String> fileNames = directoryReader.listFilesForFolder();
		if(!fileNames.isEmpty()) {
			returnModel.setResultList(fileNames);
		}
		
		return returnModel;
	}

	@Override
	public ReturnModel createBackup() {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
//		returnModel.setEntity(client);
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		List<String> fileNames = directoryReader.listFilesForFolder();
		if(!fileNames.isEmpty()) {
			returnModel.setResultList(fileNames);
		}
		
		return returnModel;
	}

}
