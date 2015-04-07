package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.securityManager.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.securityManager.contracts.ISecurityManager;

/**
 * This class handles security and encryption related services.
 * 
 * @author tejasvamsingh
 *
 */
@Stateless
public class SecurityManager implements ISecurityManager {

	/**
	 * Encrypt password using MD-5
	 * 
	 * @author tejasvamsingh
	 */

	@Override
	public String getEncryptedString(String unencryptedString) {

		System.out.println("inside getEncryptedString in SecurityManager");
		MessageDigest messageDigestObject;

		try {
			messageDigestObject = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// this should never happen.
			System.out.println("No such algorithm Exception fired.");
			return null;
		}
		messageDigestObject.update(unencryptedString.getBytes());

		byte byteData[] = messageDigestObject.digest();

		StringBuffer stringBufferObject = new StringBuffer();
		for (int i = 0; i < byteData.length; i++)
			stringBufferObject.append(Integer.toString(
					(byteData[i] & 0xff) + 0x100, 16).substring(1));

		System.out.println("ENCRYPTED : " + stringBufferObject.toString());
		return stringBufferObject.toString();
	}

}
