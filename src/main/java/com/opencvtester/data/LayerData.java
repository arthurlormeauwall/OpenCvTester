package com.opencvtester.data;

import com.opencvtester.data.interfaces.LayerDataInterface;

public class LayerData extends Index implements LayerDataInterface
{
	public LayerData() {
		super(0,-2);
	}
	
	public LayerData (int layerIndex) {
		super(layerIndex,-2);
	}
}
