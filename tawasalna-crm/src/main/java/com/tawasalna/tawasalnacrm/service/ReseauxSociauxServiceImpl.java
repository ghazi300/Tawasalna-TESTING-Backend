package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.ReseauxSociaux;
import com.tawasalna.tawasalnacrm.repository.ReseauxSociauxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReseauxSociauxServiceImpl implements ReseauxSociauxService {

    @Autowired
    private ReseauxSociauxRepository reseauxSociauxRepository;

    @Override
    public List<ReseauxSociaux> getAllReseauxSociaux() {
        return reseauxSociauxRepository.findAll();
    }

    @Override
    public ReseauxSociaux getReseauxSociauxById(String id) {
        return reseauxSociauxRepository.findById(id).orElse(null);
    }

    @Override
    public ReseauxSociaux saveReseauxSociaux(ReseauxSociaux reseauxSociaux) {
        return reseauxSociauxRepository.save(reseauxSociaux);
    }

    @Override
    public ReseauxSociaux updateReseauxSociaux(ReseauxSociaux reseauxSociaux) {
        return reseauxSociauxRepository.save(reseauxSociaux);
    }

    @Override
    public void deleteReseauxSociaux(String id) {
        reseauxSociauxRepository.deleteById(id);
    }

    @Override
    public List<ReseauxSociaux> getReseauxDetailsList() {
        // Fetch the contact details from the database dynamically
        List<ReseauxSociaux> reseauxSociauxesDetailsList = reseauxSociauxRepository.findAll();

        return reseauxSociauxesDetailsList;
    }

    @Override
    public void archiveReseauxSociaux(String reseauxsociauxId) {
        Optional<ReseauxSociaux> reseauxSociauxOptional = reseauxSociauxRepository.findById(reseauxsociauxId);
        if (reseauxSociauxOptional.isPresent()) {
            ReseauxSociaux reseauxSociaux = reseauxSociauxOptional.get();
            // Set the archived field to true
            reseauxSociaux.setArchived(true);
            reseauxSociauxRepository.save(reseauxSociaux);
        }
    }

}


