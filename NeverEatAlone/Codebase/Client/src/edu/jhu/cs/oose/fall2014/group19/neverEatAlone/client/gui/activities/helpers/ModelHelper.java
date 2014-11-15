package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities.helpers;
/**
 * 
 * @author Hai Tang
 * The Model holds the name and the information if the element is currently selected
 */
public class ModelHelper {
	  private String name;
	  private boolean selected;

	  public ModelHelper(String name) {
	    this.name = name;
	    selected = false;
	  }

	  public String getName() {
	    return name;
	  }

	  public void setName(String name) {
	    this.name = name;
	  }

	  public boolean isSelected() {
	    return selected;
	  }

	  public void setSelected(boolean selected) {
	    this.selected = selected;
	  }
}
