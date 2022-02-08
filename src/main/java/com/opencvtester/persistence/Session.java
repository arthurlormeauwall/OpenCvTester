package com.opencvtester.persistence;

import java.util.List;

public record Session(String title, List<LayerData> layers, List<FilterData> filters) {
}
