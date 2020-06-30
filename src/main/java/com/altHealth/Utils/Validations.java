package com.altHealth.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.ManagedBean;

@ManagedBean
public class Validations {
	
	public boolean DateFormatValidation(String format, String dateToFormat, List<String> errorList, List<String> idTagList, String... tags) {
		boolean valid = true;
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		
		try {
	        sdf.parse(dateToFormat); 
	        System.out.println(dateToFormat+" is a valid date format");
	        valid = true;
	    } catch (ParseException e) {
	    	/* Date format is invalid */
	    	String result = "Error! " + dateToFormat+" is a invalid Date format";
	        System.out.println(result);
	        errorList.add(result);
	        for(String tag : tags) {
	        	idTagList.add(tag);
	        }
	        
	        valid = false;
	    }
		
		return valid;
	}
	
	public boolean SAIdNumberValidation(String id, List<String> errorList, List<String> idTagList, String... tags) {
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
	    	String result = "Error! " + id + " is a invalid identification number";
	    	System.out.println(result);
	    	errorList.add(result);
	    	for(String tag : tags) {
	        	idTagList.add(tag);
	        }
	    }
	    return valid;
	}
	
	public boolean emailValidation(String email, List<String> errorList, List<String> idTagList, String... tags) {
		boolean valid = true;
		
		String patternString = "(.*)(.@)(.*)";
		Pattern pattern = Pattern.compile(patternString);

    	//Pattern 1
        Matcher matcher = pattern.matcher(email);
        valid = matcher.matches();
        
        if(!valid) {
        	String result = "Error! " + email + " is a invalid email format";
	    	System.out.println(result);
	    	errorList.add(result);
	    	for(String tag : tags) {
	    		idTagList.add(tag);
	    	}
        }
        
		return valid;
	}
	
	public boolean telNumValidation(String tel1, String tel2, String tel3, List<String> errorList, List<String> idTagList, String... tags) {
		boolean valid = true;
		boolean validtel1 = true;
		boolean validtel2 = true;
		boolean validtel3 = true;
		
		String patternString = "^(([0-9]{3}))?[-. ]?([0-9]{3})[-. ]?([0-9]{4})";
		Pattern pattern = Pattern.compile(patternString);

		if(tel1 != null) {
	        Matcher matcherTelH = pattern.matcher(tel1);
	        validtel1 = matcherTelH.matches();
		}
        
		if(tel2 != null) {
	        Matcher matcherTelW = pattern.matcher(tel2);
	        validtel2 = matcherTelW.matches();
		}
        
		if(tel3 != null) {
	        Matcher matcherTelCell = pattern.matcher(tel3);
	        validtel3 = matcherTelCell.matches();
		}
        
        if(!validtel1) {
        	String result = tel1 + " is a invalid tel format";
	    	System.out.println(result);
	    	idTagList.add(tags[0]);
        }
        if(!validtel2) {
        	String result = tel2 + " is a invalid tel format";
	    	System.out.println(result);
	    	idTagList.add(tags[1]);
        }
        if(!validtel3) {
        	String result = tel3 + " is a invalid tel format";
	    	System.out.println(result);
	    	idTagList.add(tags[2]);
        }
        
        if(!validtel1 || !validtel2 || !validtel3) {
        	errorList.add("Error! Please match the 000-000-0000 format");
        	valid = false;
        }
        
		return valid;
	}

}
