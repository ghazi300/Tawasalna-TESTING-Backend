package com.tawasalna.tawasalnacrm.service;

import com.tawasalna.tawasalnacrm.models.Logo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface LogoService {

    Logo saveLogo(MultipartFile file) throws IOException;

    void deleteLogo(String logoId);

    Optional<Logo> getLogo(String logoId);

    Logo updateLogo(String logoId, MultipartFile file) throws IOException;

    List<Logo> getAllLogos();

    void archiveLogo(String logoId);

}

