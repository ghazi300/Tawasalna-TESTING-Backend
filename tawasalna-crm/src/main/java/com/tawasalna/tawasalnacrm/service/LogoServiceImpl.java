package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.Logo;
import com.tawasalna.tawasalnacrm.repository.LogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class LogoServiceImpl implements LogoService {

    private final LogoRepository logoRepository;

    @Autowired
    public LogoServiceImpl(LogoRepository logoRepository) {
        this.logoRepository = logoRepository;
    }

    @Override
    public Logo saveLogo(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        byte[] data = file.getBytes();
        Logo logo = new Logo();
        logo.setFilename(filename);
        // If you're storing the file content in the Logo entity, you can add it here
        // For example,
        logo.setData(data);
        return logoRepository.save(logo);
    }

    @Override
    public void deleteLogo(String logoId) {
        logoRepository.deleteById(logoId);    }

    @Override
    public Optional<Logo> getLogo(String logoId) {
        return logoRepository.findById(logoId);    }

    @Override
    public Logo updateLogo(String logoId, MultipartFile file) throws IOException {
        // Logique pour mettre à jour un logo
        return null;

    }

    @Override
    public List<Logo> getAllLogos() {
        return logoRepository.findAll();
    }

    @Override
    public void archiveLogo(String logoId) {
        Optional<Logo> logoOptional = logoRepository.findById(logoId);
        if (logoOptional.isPresent()) {
            Logo logo = logoOptional.get();
            // Logique pour archiver le logo, par exemple, changer un champ "actif" à false
            logo.setArchived(true);
            logoRepository.save(logo);
        }
    }

    // Ajoutez d'autres méthodes de service selon les besoins
}


