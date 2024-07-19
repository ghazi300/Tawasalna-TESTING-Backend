package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.payload.request.EquipementRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EquipementResponse;

import java.util.List;

public interface IEquipmentService {
    String save(EquipementRequest equipementRequest);

    List<EquipementResponse> getAllEquipment();

    void deleteEquipment(String id);
}
