package com.tawasalna.tawasalnacrm.controller;

import com.tawasalna.tawasalnacrm.models.About;
import com.tawasalna.tawasalnacrm.service.AboutService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crm/about")
@CrossOrigin("*")
@AllArgsConstructor
public class AboutController {

    private AboutService aboutService;

    @GetMapping("/all")
    public List<About> getAllAbout() {
        return aboutService.getAllAbout();
    }

    @GetMapping("/{id}")
    public About getAboutById(@PathVariable String id) {
        return aboutService.getAboutById(id);
    }

    @PostMapping("/save")
    public About saveAbout(@RequestBody About about) {
        return aboutService.saveAbout(about);
    }

    @PutMapping("/update")
    public About updateAbout(@RequestBody About about) {
        return aboutService.updateAbout(about);
    }

    @DeleteMapping("/{id}")
    public void deleteAbout(@PathVariable String id) {
        aboutService.deleteAbout(id);
    }
    @PostMapping("/archive/{id}")
    public ResponseEntity<?> archiveAbout(@PathVariable String id) {
        aboutService.archiveAbout(id);
        return ResponseEntity.ok().build();
    }
}

