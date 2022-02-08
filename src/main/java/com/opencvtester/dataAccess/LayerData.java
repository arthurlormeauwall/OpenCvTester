package com.opencvtester.dataAccess;

import java.io.Serializable;
import java.util.Stack;

public record LayerData( int layerIndex, Float opacityValue, Stack<String> filterNames)  implements DataRecord, Serializable {

}
