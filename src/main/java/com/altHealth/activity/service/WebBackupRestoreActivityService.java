package com.altHealth.activity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altHealth.Utils.DirectoryReader;
import com.altHealth.Utils.ShellScriptRunner;
import com.altHealth.activity.WebBackupRestoreActivity;
import com.altHealth.mappings.ModelMappings;
import com.altHealth.model.ReturnModel;

@Service
public class WebBackupRestoreActivityService implements WebBackupRestoreActivity {
	
	@Autowired
	DirectoryReader directoryReader;
	@Autowired
	ShellScriptRunner shellScriptRunner;

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
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		try {
			String dbFileName = shellScriptRunner.scriptRunner(ModelMappings.BACKUP);
			returnModel.setEntity(dbFileName);
		}catch (Exception e) {
			errorList.add(e.getMessage());
		}
		
		return returnModel;
	}
	
	@Override
	public ReturnModel restoreDB(String fileName) {
		ReturnModel returnModel = new ReturnModel();
		List<String> errorList = new ArrayList<String>();
		List<String> idTagList = new ArrayList<String>();
		returnModel.setErrorList(errorList);
		returnModel.setIdTags(idTagList);
		
		try {
			shellScriptRunner.scriptRunner(ModelMappings.RESTORE, fileName);
			returnModel.setEntity(fileName);
		}catch (Exception e) {
			errorList.add(e.getMessage());
		}
		
		return returnModel;
	}

}
