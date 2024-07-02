package com.tawasalna.social.services;

import com.tawasalna.shared.userapi.model.Gender;
import com.tawasalna.shared.userapi.model.ResidentProfile;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.social.businesslogic.residentprofile.ResidentProfileServiceImpl;
import com.tawasalna.social.payload.request.CoverPictureDTO;
import com.tawasalna.social.payload.request.ProfilePictureDTO;
import com.tawasalna.social.payload.request.ResidentProfileDTO;
import com.tawasalna.social.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ResidentProfileServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ResidentProfileServiceImpl residentProfileService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    /*
        @Test
        public void testUpdateResidentProfileByUserId() {
            String userId = "user123";
            ResidentProfileDTO updateDTO = new ResidentProfileDTO();
            updateDTO.setFullName("John Doe");
            updateDTO.setPhoneNumber("123456789");
            updateDTO.setAddress("123 Main St");
            updateDTO.setAge(30);
            updateDTO.setGender("MALE");

<<<<<<< HEAD:tawasalna-auth/src/test/java/com/tawasalna/auth/businesslogic/ResidentProfile/ResidentProfileServiceImplTest.java
            Users user = new Users();
            ResidentProfile residentProfile = new ResidentProfile();
            user.setResidentProfile(residentProfile);
=======
    @Test
    void testUpdateResidentProfileByUserId() {
        String userId = "user123";
        ResidentProfileDTO updateDTO = new ResidentProfileDTO();
        updateDTO.setFullName("John Doe");
        updateDTO.setPhoneNumber("123456789");
        updateDTO.setAddress("123 Main St");
        updateDTO.setAge(30);
        updateDTO.setGender("MALE");
>>>>>>> a37da84ae7acd9a4c6d2a87ddddc8eae18bbac4f:tawasalna-social/src/test/java/com/tawasalna/social/services/ResidentProfileServiceImplTest.java

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));

            residentProfileService.updateResidentProfileByUserId(userId, updateDTO);

<<<<<<< HEAD:tawasalna-auth/src/test/java/com/tawasalna/auth/businesslogic/ResidentProfile/ResidentProfileServiceImplTest.java
            assertEquals(updateDTO.getFullName(), residentProfile.getFullName());
            assertEquals(updateDTO.getPhoneNumber(), residentProfile.getPhoneNumber());
            assertEquals(updateDTO.getAddress(), residentProfile.getAddress());
            assertEquals(updateDTO.getAge().intValue(), residentProfile.getAge());
            assertEquals(Gender.MALE, residentProfile.getGender());
            verify(userRepository, times(1)).save(user);
        }
=======
        residentProfileService.updateResidentProfileByUserId(userId, updateDTO);

        assertEquals(updateDTO.getFullName(), residentProfile.getFullName());
        assertEquals(updateDTO.getPhoneNumber(), residentProfile.getPhoneNumber());
        assertEquals(updateDTO.getAddress(), residentProfile.getAddress());
        assertEquals(updateDTO.getAge().intValue(), residentProfile.getAge());
        Assertions.assertEquals(Gender.MALE, residentProfile.getGender());
        verify(userRepository, times(1)).save(user);
    }
>>>>>>> a37da84ae7acd9a4c6d2a87ddddc8eae18bbac4f:tawasalna-social/src/test/java/com/tawasalna/social/services/ResidentProfileServiceImplTest.java

     */
/*
    @Test
    public void testUpdateProfilePicture() {
        String userId = "user123";
        ProfilePictureDTO profilePictureDTO = new ProfilePictureDTO();
        MockMultipartFile profilePhoto = new MockMultipartFile("profile.jpg", new byte[0]);
        profilePictureDTO.setProfilephoto(profilePhoto);

        Users user = new Users();
        ResidentProfile residentProfile = new ResidentProfile();
        user.setResidentProfile(residentProfile);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResidentProfile updatedProfile = residentProfileService.updateProfilePicture(userId, profilePictureDTO);

        Assertions.assertEquals(profilePhoto.getOriginalFilename(), residentProfile.getProfilephoto());
        verify(userRepository, times(1)).save(user);
    }
/*
    @Test
    void testUpdateCoverPicture() {
        String userId = "user123";
        CoverPictureDTO coverPictureDTO = new CoverPictureDTO();
        MultipartFile coverPhoto = new MockMultipartFile("cover.jpg", new byte[0]);
        coverPictureDTO.setCoverphoto(coverPhoto);

        Users user = new Users();
        ResidentProfile residentProfile = new ResidentProfile();
        user.setResidentProfile(residentProfile);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResidentProfile updatedProfile = residentProfileService.updateCoverPicture(userId, coverPictureDTO);

        Assertions.assertEquals(coverPhoto.getOriginalFilename(), residentProfile.getCoverphoto());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetResidentProfileById_WhenUserExists() {
        String userId = "user123";
        ResidentProfile residentProfile = new ResidentProfile();
        Users user = new Users();
        user.setResidentProfile(residentProfile);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        ResidentProfile result = residentProfileService.getResidentProfileById(userId);

        Assertions.assertEquals(residentProfile, result);
    }
*/
    @Test
    void testGetResidentProfileById_WhenUserDoesNotExist() {
        String userId = "user123";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            residentProfileService.getResidentProfileById(userId);
        });
    }
}
