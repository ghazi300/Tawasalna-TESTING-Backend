package com.example.residentsupportservices.Services;

import com.example.residentsupportservices.Entity.ChildcareProgram;
import com.example.residentsupportservices.Repository.ChildcareProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChildcareProgramService implements IChildcareProgramService {

    @Autowired
    private ChildcareProgramRepository repository;

    @Override
    public ChildcareProgram addChildcareProgram(ChildcareProgram childcareProgram) {
        return repository.save(childcareProgram);
    }

    @Override
    public Optional<ChildcareProgram> getChildcareProgramById(String id) {
        return repository.findById(id);
    }

    @Override
    public ChildcareProgram updateChildcareProgram(ChildcareProgram program) {
        return repository.save(program);
    }

    @Override
    public void deleteChildcareProgram(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<ChildcareProgram> getAllChildcarePrograms() {
        return repository.findAll();
    }
}