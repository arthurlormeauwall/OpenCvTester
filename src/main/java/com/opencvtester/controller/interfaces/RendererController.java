package com.opencvtester.controller.interfaces;

import com.opencvtester.renderer.interfaces.FrameInterface;


public interface RendererController {

	FrameInterface getFrameOut();

	void openImage(String fileName);

	void render();

	void clearAll();
}
