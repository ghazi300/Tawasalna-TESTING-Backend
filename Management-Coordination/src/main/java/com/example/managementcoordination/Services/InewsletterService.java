package com.example.managementcoordination.Services;

import com.example.managementcoordination.entities.Newsletter;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InewsletterService {
    Newsletter createNewsletter(Newsletter newsletter);
    void deleteNewsletter(String id);
    Newsletter updateNewsletter(Newsletter newsletter);
    List<Newsletter> getAllNewsletters();
    Newsletter getNewsletterById(String id);
    Newsletter createNewsletterWithFile(MultipartFile file, Newsletter newsletter);
    Resource loadFile (String fileName) ;
}
