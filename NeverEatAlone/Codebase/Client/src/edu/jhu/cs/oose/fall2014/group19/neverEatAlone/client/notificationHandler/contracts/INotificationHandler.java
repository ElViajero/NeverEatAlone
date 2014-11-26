package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.notificationHandler.contracts;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.TabHostActivity;

public interface INotificationHandler {

	public void init(TabHostActivity tabHostactivity,String username);
	public void cleanUp();

}
