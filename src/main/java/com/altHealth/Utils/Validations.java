package com.altHealth.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.ManagedBean;

import com.altHealth.mappings.ModelMappings;

@ManagedBean
public class Validations {
	
	public boolean DateFormatValidation(String format, String dateToFormat, List<String> errorList, List<String> idTagList) {
		boolean valid = true;
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		
		try {
	        sdf.parse(dateToFormat); 
	        System.out.println(dateToFormat+" is a valid date format");
	        valid = true;
	    } catch (ParseException e) {
	    	/* Date format is invalid */
	    	String result = dateToFormat+" is a invalid Date format";
	        System.out.println(result);
	        errorList.add(result);
	        idTagList.add(ModelMappings.CLIENT_id);
	        valid = false;
	    }
		
		return valid;
	}
	
	public boolean SAIdNumberValidation(String id, List<String> errorList, List<String> idTagList) {
		boolean valid = true;
		char[] idchars = id.toCharArray();
	    int sum = 0;
	    // loop over each digit right-to-left, including the check-digit
	    for (int i = 1; i <= idchars.length; i++) {
	        int digit = Character.getNumericValue(idchars[idchars.length - i]);
	        if ((i % 2) != 0) {
	            sum += digit;
	        } else {
	            sum += digit < 5 ? digit * 2 : digit * 2 - 9;
	        }
	    }
	    if((sum % 10) == 0) {
	    	valid = true;
	    }else {
	    	valid = false;
	    	String result = id+" is a invalid identification number";
	    	System.out.println(result);
	    	errorList.add(result);
	    	idTagList.add(ModelMappings.CLIENT_id);
	    }
	    return valid;
	}

}
