package gui;

import baseClasses.filter.FilterControlledByFloat;
import guiController.GuiManager;

public class OpacitySlider extends LabelledSlider {

	public OpacitySlider(String name, Float defaultValue, FilterControlledByFloat widgetToUpdate,
			GuiManager guiManager) {
		super(name, defaultValue, widgetToUpdate, guiManager);
		slider.setMaximum(100);
	}

}
