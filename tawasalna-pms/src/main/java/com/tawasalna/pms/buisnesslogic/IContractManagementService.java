package com.tawasalna.pms.buisnesslogic;

import com.tawasalna.pms.payload.request.ContractDTO;
import com.tawasalna.shared.dtos.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IContractManagementService {
    ResponseEntity<ApiResponse> addContract (ContractDTO contractDTO, List<MultipartFile> images) throws ExecutionException, InterruptedException;
    public void delete(String id);


}
