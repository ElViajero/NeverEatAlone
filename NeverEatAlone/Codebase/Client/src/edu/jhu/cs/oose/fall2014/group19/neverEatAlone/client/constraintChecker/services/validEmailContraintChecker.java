package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.services;

import java.util.Map;
import java.util.regex.Pattern;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.constraintChecker.contracts.IConstraintChecker;

/**
 * 
 * Constraint checker for email validity.
 * 
 * @author yueling loh
 * 
 * @author tejasvamsingh
 *
 */
public class validEmailContraintChecker implements IConstraintChecker {

	private Pattern p;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public validEmailContraintChecker() {
		p = Pattern.compile(EMAIL_PATTERN);
	}

	@Override
	public boolean areConstraintsSatisfied(Map<String, Object> constraintMap) {
		String email = constraintMap.get("email").toString();
		if (email.equals(""))
			return true;
		return p.matcher(email).matches();
	}

}
