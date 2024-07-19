package com.tawasalnasecuritysafety.services;


import com.tawasalnasecuritysafety.models.Protocol;
import com.tawasalnasecuritysafety.repos.ProtocolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProtocolService {
    @Autowired
    private ProtocolRepository protocolRepository;

    public Protocol createProtocol(Protocol protocol) {
        return protocolRepository.save(protocol);
    }

    public Optional<Protocol> getProtocolById(String id) {
        return protocolRepository.findById(id);
    }

    public List<Protocol> getAllProtocols() {
        return protocolRepository.findAll();
    }

    public Protocol updateProtocol(String id, Protocol protocol) {
        if (protocolRepository.existsById(id)) {
            protocol.setId(id);
            return protocolRepository.save(protocol);
        }
        return null;
    }

    public void deleteProtocol(String id) {
        protocolRepository.deleteById(id);
    }
}
