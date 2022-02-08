package com.opencvtester.dataAccess;

import java.io.Serializable;
import java.util.List;

public record Session(String title, List<LayerData> layers, List<FilterData> filters) implements Serializable {
}
