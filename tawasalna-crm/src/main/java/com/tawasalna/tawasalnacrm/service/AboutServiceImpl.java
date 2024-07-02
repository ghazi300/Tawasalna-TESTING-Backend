package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.About;
import com.tawasalna.tawasalnacrm.repository.AboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    private AboutRepository aboutRepository;

    @Override
    public List<About> getAllAbout() {
        return aboutRepository.findAll();
    }

    @Override
    public About getAboutById(String id) {
        return aboutRepository.findById(id).orElse(null);
    }

    @Override
    public About saveAbout(About about) {
        return aboutRepository.save(about);
    }

    @Override
    public About updateAbout(About about) {
        return aboutRepository.save(about);
    }

    @Override
    public void deleteAbout(String id) {
        aboutRepository.deleteById(id);
    }

    @Override
    public void archiveAbout(String aboutId) {
        Optional<About> aboutOptional = aboutRepository.findById(aboutId);
        if (aboutOptional.isPresent()) {
            About about = aboutOptional.get();
            // Set the archived field to true
            about.setArchived(true);
            aboutRepository.save(about);
        }
    }
}

