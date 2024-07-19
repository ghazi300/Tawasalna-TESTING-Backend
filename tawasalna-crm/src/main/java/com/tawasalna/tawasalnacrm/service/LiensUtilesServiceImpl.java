package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.LiensUtiles;
import com.tawasalna.tawasalnacrm.repository.LiensUtilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiensUtilesServiceImpl implements LiensUtilesService {

    @Autowired
    private LiensUtilesRepository liensUtilesRepository;

    @Override
    public List<LiensUtiles> getAllLiensUtiles() {
        return liensUtilesRepository.findAll();
    }

    @Override
    public LiensUtiles getLiensUtilesById(String id) {
        return liensUtilesRepository.findById(id).orElse(null);
    }

    @Override
    public LiensUtiles saveLiensUtiles(LiensUtiles liensUtiles) {
        return liensUtilesRepository.save(liensUtiles);
    }

    @Override
    public LiensUtiles updateLiensUtiles(LiensUtiles liensUtiles) {
        return liensUtilesRepository.save(liensUtiles);
    }

    @Override
    public void deleteLiensUtiles(String id) {
        liensUtilesRepository.deleteById(id);
    }

    @Override
    public void archiveLienUtiles(String liensutilesId) {
        Optional<LiensUtiles> liensUtilesOptional = liensUtilesRepository.findById(liensutilesId);
        if (liensUtilesOptional.isPresent()) {
            LiensUtiles liensUtiles = liensUtilesOptional.get();
            // Set the archived field to true
            liensUtiles.setArchived(true);
            liensUtilesRepository.save(liensUtiles);
        }
    }
}

