package com.opencvtester.dataAccess;

import java.util.Stack;

public record LayerData( int layerIndex, Float opacityValue, Stack<String> filterNames)  implements DataRecord {

}
