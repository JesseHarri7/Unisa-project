package com.altHealth.activity;

import com.altHealth.model.ReturnModel;

public interface WebBackupRestoreActivity {
	
	abstract ReturnModel findAll();
	abstract ReturnModel createBackup();
}
