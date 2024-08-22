package com.example.residentsupportservices.Services;


import com.example.residentsupportservices.Entity.ChildcareProgram;

import java.util.List;
import java.util.Optional;

public interface IChildcareProgramService {
    ChildcareProgram addChildcareProgram(ChildcareProgram program);
    Optional<ChildcareProgram> getChildcareProgramById(String id);
    ChildcareProgram updateChildcareProgram(ChildcareProgram program);
    void deleteChildcareProgram(String id);
    List<ChildcareProgram> getAllChildcarePrograms();
}
