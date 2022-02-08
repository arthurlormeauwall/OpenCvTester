package com.opencvtester.persistence;

import java.util.Stack;

public record LayerData( int layerIndex, Float opacityValue, Stack<String> filterNames)  implements DataRecord {

}
