package com.opencvtester.controller.interfaces;

import com.opencvtester.history.Action;

public interface HistoryInterface {

	void setState(Action action);

	void undo();

	void redo();

	void storeCurrentStateInHistory();

	void clearAll();

}
