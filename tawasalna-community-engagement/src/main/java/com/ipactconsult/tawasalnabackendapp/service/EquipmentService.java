package com.ipactconsult.tawasalnabackendapp.service;

import com.ipactconsult.tawasalnabackendapp.models.Equipment;
import com.ipactconsult.tawasalnabackendapp.payload.request.EquipementRequest;
import com.ipactconsult.tawasalnabackendapp.payload.response.EquipementResponse;
import com.ipactconsult.tawasalnabackendapp.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class EquipmentService implements IEquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final EquipementMapper equipementMapper;

    @Override
    public String save(EquipementRequest equipementRequest) {
        Equipment equipment=equipementMapper.toEquipement(equipementRequest);
        return equipmentRepository.save(equipment).getId();
    }

    @Override
    public List<EquipementResponse> getAllEquipment() {
        List<Equipment>equipmentList=equipmentRepository.findAll();
        return equipmentList.stream().map(equipementMapper::toEquipementResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteEquipment(String id) {
        equipmentRepository.deleteById(id);
    }
}
