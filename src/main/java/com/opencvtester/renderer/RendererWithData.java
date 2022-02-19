package com.opencvtester.renderer;

import com.opencvtester.appControllers.DataProvider;
import com.opencvtester.appControllers.Renderer;

public abstract class RendererWithData extends Renderer implements DataProvider {
	public void openImage(String fileName) {
		try {
			frameIn.readFromFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
