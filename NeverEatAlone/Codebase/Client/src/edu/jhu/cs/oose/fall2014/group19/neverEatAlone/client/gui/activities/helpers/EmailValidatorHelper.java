package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;

import java.util.regex.Pattern;

/**
 * This class is a helper class that
 * checks if an email is valid
 * @author Yueling Loh
 *
 */
public class EmailValidatorHelper {
	private Pattern p;
 
	private static final String EMAIL_PATTERN = 
	"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public EmailValidatorHelper(){
		p = Pattern.compile(EMAIL_PATTERN);
	}
	
	public boolean isValid(String email){
		return p.matcher(email).matches();
	}
}
