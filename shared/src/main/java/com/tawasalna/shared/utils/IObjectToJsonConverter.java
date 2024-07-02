package com.tawasalna.shared.utils;

import java.util.Map;

public interface IObjectToJsonConverter {

    Map<String, Object> toJson(Object from);
}
