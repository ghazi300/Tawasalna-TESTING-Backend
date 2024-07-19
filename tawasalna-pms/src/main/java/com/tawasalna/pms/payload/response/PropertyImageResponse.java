package com.tawasalna.pms.payload.response;

import com.tawasalna.pms.models.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyImageResponse {
    private Property property;
    private List<byte[]> images;
}
