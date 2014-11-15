package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.client.gui.activities;
/**
 * 
 * @author Hai Tang
 *
 */
public class Model {
	  private String name;
	  private boolean selected;

	  public Model(String name) {
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
