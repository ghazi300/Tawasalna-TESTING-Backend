package com.tawasalna.tawasalnacrm.controller;

import com.tawasalna.tawasalnacrm.models.LiensUtiles;
import com.tawasalna.tawasalnacrm.service.LiensUtilesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crm/liens-utiles")
@CrossOrigin("*")
@AllArgsConstructor
public class LiensUtilesController {

    private final LiensUtilesService liensUtilesService;

    @GetMapping("/all")
    public List<LiensUtiles> getAllLiensUtiles() {
        return liensUtilesService.getAllLiensUtiles();
    }

    @GetMapping("/{id}")
    public LiensUtiles getLiensUtilesById(@PathVariable String id) {
        return liensUtilesService.getLiensUtilesById(id);
    }

    @PostMapping("/save")
    public LiensUtiles saveLiensUtiles(@RequestBody LiensUtiles liensUtiles) {
        return liensUtilesService.saveLiensUtiles(liensUtiles);
    }

    @PutMapping("/update")
    public LiensUtiles updateLiensUtiles(@RequestBody LiensUtiles liensUtiles) {
        return liensUtilesService.updateLiensUtiles(liensUtiles);
    }

    @DeleteMapping("/{id}")
    public void deleteLiensUtiles(@PathVariable String id) {
        liensUtilesService.deleteLiensUtiles(id);
    }

    @PostMapping("/archive/{id}")
    public ResponseEntity<?> archiveLiensUtiles(@PathVariable String id) {
        liensUtilesService.archiveLienUtiles(id);
        return ResponseEntity.ok().build();
    }
}
