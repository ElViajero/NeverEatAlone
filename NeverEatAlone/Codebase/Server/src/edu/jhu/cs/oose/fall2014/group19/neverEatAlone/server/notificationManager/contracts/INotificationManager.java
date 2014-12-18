package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.notificationManager.contracts;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;
/**
 * 
 * @author tejasvamsingh
 *
 */
@Local
public interface INotificationManager {

	public void pushNotification(List<Map<String,String>> notificationList,
			List<String> recipientList);

}
