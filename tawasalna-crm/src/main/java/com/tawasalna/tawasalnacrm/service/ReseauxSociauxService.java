package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.ReseauxSociaux;

import java.util.List;

public interface ReseauxSociauxService {
    List<ReseauxSociaux> getAllReseauxSociaux();
    ReseauxSociaux getReseauxSociauxById(String id);
    ReseauxSociaux saveReseauxSociaux(ReseauxSociaux reseauxSociaux);
    ReseauxSociaux updateReseauxSociaux(ReseauxSociaux reseauxSociaux);
    void deleteReseauxSociaux(String id);
    List<ReseauxSociaux> getReseauxDetailsList();
    void archiveReseauxSociaux(String reseauxsociauxId); // New method added


}
