package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.Equipment;
import com.ipactconsult.tawasalnabackendapp.payload.request.EquipementRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EquipementResponse;
import org.springframework.stereotype.Service;

@Service
public class EquipementMapper {
    public Equipment toEquipement(EquipementRequest equipementRequest) {
        return Equipment.builder()
                .name(equipementRequest.getName())
                .description(equipementRequest.getDescription())
                .location(equipementRequest.getLocation())

                .build();
    }

    public EquipementResponse toEquipementResponse(Equipment equipment) {
        return EquipementResponse.builder()
                .id(equipment.getId())
                .name(equipment.getName())
                .description(equipment.getDescription())
                .location(equipment.getLocation())


                .build();

            }
}
