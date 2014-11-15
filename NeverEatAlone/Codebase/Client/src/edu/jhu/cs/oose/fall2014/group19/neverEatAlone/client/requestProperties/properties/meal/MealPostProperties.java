package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.requestProperties.properties.meal;

import java.util.List;
import java.util.Map;

public class MealPostProperties {

	MealProperties MealPropertiesObject;
	PostProperties PostPropertiesObject;
	List<String> RecipientList;
	
	
	static MealPostProperties MealPostPropertiesInstance;
	
	
	
	
	public void PopulatePageOneInformation(Map<String,String> pageOneMap){
		
		if(MealPostPropertiesInstance==null)
			MealPostPropertiesInstance = new MealPostProperties();
			
		String startTime = pageOneMap.get("StartTime");
		String endTime = pageOneMap.get("StartTime");
		String location = pageOneMap.get("Location");
		String maxNumberOfInvitees = pageOneMap.get("MaxNumberOfInvitees");
		String isNotificationExtendible = pageOneMap.get("IsNotificationExtendible");
		
		MealPostPropertiesInstance.MealPropertiesObject = new MealProperties(startTime , 
				endTime, location, maxNumberOfInvitees,
				isNotificationExtendible);
				
	}
	
	
	public MealPostProperties GetMealPostPropertiesInstance(){
		return MealPostPropertiesInstance;
	} 
	
	
}
