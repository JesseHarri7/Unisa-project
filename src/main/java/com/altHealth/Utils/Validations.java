package com.altHealth.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	    	String result = id + " is a invalid identification number";
	    	System.out.println(result);
	    	errorList.add(result);
	    	idTagList.add(ModelMappings.CLIENT_id);
	    }
	    return valid;
	}
	
	public boolean emailValidation(String email, List<String> errorList, List<String> idTagList) {
		boolean valid = true;
		
		String patternString = "(.*)(.@)(.*)";
		Pattern pattern = Pattern.compile(patternString);

    	//Pattern 1
        Matcher matcher = pattern.matcher(email);
        valid = matcher.matches();
        
        if(!valid) {
        	String result = email + " is a invalid email format";
	    	System.out.println(result);
	    	errorList.add(result);
	    	idTagList.add(ModelMappings.CLIENT_cEmail);
        }
        
		return valid;
	}
	
	public boolean telNumValidation(String telH, String telW, String telCell, List<String> errorList, List<String> idTagList) {
		boolean valid = true;
		boolean validtelH = true;
		boolean validtelW = true;
		boolean validtelCell = true;
		
		String patternString = "^(([0-9]{3}))?[-. ]?([0-9]{3})[-. ]?([0-9]{4})";
		Pattern pattern = Pattern.compile(patternString);

		if(telH != null) {
	        Matcher matcherTelH = pattern.matcher(telH);
	        validtelH = matcherTelH.matches();
		}
        
		if(telW != null) {
	        Matcher matcherTelW = pattern.matcher(telW);
	        validtelW = matcherTelW.matches();
		}
        
		if(telCell != null) {
	        Matcher matcherTelCell = pattern.matcher(telCell);
	        validtelCell = matcherTelCell.matches();
		}
        
        if(!validtelH) {
        	String result = telH + " is a invalid tel format";
	    	System.out.println(result);
	    	idTagList.add(ModelMappings.CLIENT_cTelH);
        }
        if(!validtelH) {
        	String result = telW + " is a invalid tel format";
	    	System.out.println(result);
	    	idTagList.add(ModelMappings.CLIENT_cTelW);
        }
        if(!validtelH) {
        	String result = telCell + " is a invalid tel format";
	    	System.out.println(result);
	    	idTagList.add(ModelMappings.CLIENT_cTelCell);
        }
        
        if(validtelH && validtelW && validtelCell) {
        	errorList.add("Please match the 000-000-0000 format");
        }
        
		return valid;
	}

}
