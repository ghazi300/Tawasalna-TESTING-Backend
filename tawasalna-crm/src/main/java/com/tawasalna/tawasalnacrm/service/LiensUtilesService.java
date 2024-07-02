package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.LiensUtiles;

import java.util.List;

public interface LiensUtilesService {
    List<LiensUtiles> getAllLiensUtiles();
    LiensUtiles getLiensUtilesById(String id);
    LiensUtiles saveLiensUtiles(LiensUtiles liensUtiles);
    LiensUtiles updateLiensUtiles(LiensUtiles liensUtiles);
    void deleteLiensUtiles(String id);
    void archiveLienUtiles(String liensutilesId); // New method added

}

