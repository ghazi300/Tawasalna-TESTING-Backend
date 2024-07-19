package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.About;

import java.util.List;

public interface AboutService {
    List<About> getAllAbout();
    About getAboutById(String id);
    About saveAbout(About about);
    About updateAbout(About about);
    void deleteAbout(String id);
    void archiveAbout(String aboutId); // New method added
}