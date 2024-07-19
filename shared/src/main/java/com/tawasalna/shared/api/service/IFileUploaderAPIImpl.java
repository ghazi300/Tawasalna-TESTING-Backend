package com.tawasalna.shared.api.service;

import com.tawasalna.shared.api.model.MultiResponse;
import com.tawasalna.shared.api.model.SingleResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class IFileUploaderAPIImpl implements IFileUploaderAPI {

    private final RestClient restClient = RestClient.create();

    @Value("${app.fileUploader.api}")
    private String fileUploaderEndpoint;

    @Override
    public Optional<String> sendFileToServer(MultipartFile file, String subDir) {
        try {
            final SingleResponse resp = restClient.post()
                    .uri(fileUploaderEndpoint + "/upload-single")
                    .body(buildHttpEntity(file))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(SingleResponse.class);

            if (resp == null) return Optional.empty();

            return Optional.of(resp.getFileName());

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<String>> sendFilesToServer(List<MultipartFile> files, String subDir) {
        try {
            final MultiResponse resp = restClient.post()
                    .uri(fileUploaderEndpoint + "/upload-multiple")
                    .body(buildHttpEntity(files))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(MultiResponse.class);

            if (resp == null) return Optional.empty();

            return Optional.of(resp.getFileNames());

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private HttpEntity<MultiValueMap<String, HttpEntity<?>>> buildHttpEntity(List<MultipartFile> files) {
        HttpHeaders headers = new HttpHeaders();

        // ContentType
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();

        multipartBodyBuilder.part("files[]", files);

        // multipart/form-data request body
        MultiValueMap<String, HttpEntity<?>> multipartBody = multipartBodyBuilder.build();

        // The complete http request body.
        return new HttpEntity<>(multipartBody, headers);
    }

    private HttpEntity<MultiValueMap<String, HttpEntity<?>>> buildHttpEntity(MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();

        // ContentType
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();

        multipartBodyBuilder.part("file", file);

        // multipart/form-data request body
        MultiValueMap<String, HttpEntity<?>> multipartBody = multipartBodyBuilder.build();

        // The complete http request body.
        return new HttpEntity<>(multipartBody, headers);
    }
}
