package com.tawasalna.tawasalnacrm.controller;

import com.tawasalna.tawasalnacrm.models.ReseauxSociaux;
import com.tawasalna.tawasalnacrm.service.ReseauxSociauxService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crm/reseaux-sociaux")
@CrossOrigin("*")
@AllArgsConstructor
public class ReseauxSociauxController {

    private ReseauxSociauxService reseauxSociauxService;

    @GetMapping("/all")
    public List<ReseauxSociaux> getAllReseauxSociaux() {
        return reseauxSociauxService.getAllReseauxSociaux();
    }

    @GetMapping("/{id}")
    public ReseauxSociaux getReseauxSociauxById(@PathVariable String id) {
        return reseauxSociauxService.getReseauxSociauxById(id);
    }

    @PostMapping("/save")
    public ReseauxSociaux saveReseauxSociaux(@RequestBody ReseauxSociaux reseauxSociaux) {
        return reseauxSociauxService.saveReseauxSociaux(reseauxSociaux);
    }

    @PutMapping("/update")
    public ReseauxSociaux updateReseauxSociaux(@RequestBody ReseauxSociaux reseauxSociaux) {
        return reseauxSociauxService.updateReseauxSociaux(reseauxSociaux);
    }

    @DeleteMapping("/{id}")
    public void deleteReseauxSociaux(@PathVariable String id) {
        reseauxSociauxService.deleteReseauxSociaux(id);
    }

    @GetMapping("/details")
    public ResponseEntity<List<ReseauxSociaux>> getReseauxDetailsList() {
        List<ReseauxSociaux> reseauxSociauxesDetailsList = reseauxSociauxService.getReseauxDetailsList();

        if (!reseauxSociauxesDetailsList.isEmpty()) {
            return ResponseEntity.ok(reseauxSociauxesDetailsList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<?> archiveReseauxSociaux(@PathVariable String id) {
        reseauxSociauxService.archiveReseauxSociaux(id);
        return ResponseEntity.ok().build();
    }

}
