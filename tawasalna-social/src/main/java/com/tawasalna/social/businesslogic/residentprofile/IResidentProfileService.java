package com.tawasalna.social.businesslogic.residentprofile;

import com.tawasalna.shared.userapi.model.ResidentProfile;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.social.models.Notification;
import com.tawasalna.social.models.ResidentComments;
import com.tawasalna.social.models.ResidentPost;
import com.tawasalna.social.payload.request.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface IResidentProfileService {
    void updateResidentProfileByUserId(String userId, ResidentProfileDTO updateDTO);

    ResidentProfile getResidentProfileById(String userId);

    void unfollowUser(String followerUserId, String userIdToUnfollow);

    ResidentProfile updateProfilePicture(String userId, ProfilePictureDTO ProfilePhotoDTO);

    ResidentProfile updateCoverPicture(String userId, CoverPictureDTO coverPictureDTO);

    void saveProfilePicture(MultipartFile profilePictureFile, String userId, String fileName);

    void saveCoverPicture(MultipartFile coverPictureFile, String userId, String fileName);

    String deletePost(String postId, String userId);

    String getProfilePhotoByUserId(String userId);

    String getCoverPhotoByUserId(String userId);

    Resource loadProfilePhoto(String profilePhotoPath);

    Resource loadCoverPhoto(String coverPhotoPath);

    void sendVerificationCodePhoneNumber(String userId, String phoneNumber);

    ResidentProfile addResidentPost(String userId, AddResidentPostDTO addResidentPostDTO);

    Resource loadPostPhoto(String photoFileName);

    List<ResidentPost> getResidentPostsByUserIdWithPhotos(String userId);

    List<ResidentPost> getResidentPostsByUserIdWithVideos(String userId);

    List<ResidentPost> getResidentPostsByUserIdWithCaptions(String userId);

    void addCommentToPost(String postId, String userId, String commentText);

    ResidentComments replyToComment(String userId, String commentId, String replyText);

    List<ReplyDTO> getRepliesByCommentId(String commentId);

    List<ResidentCommentsDTO> getAllCommentsByPostId(String postId);

    List<ResidentPostDTO> getAllPosts();

    ResponseEntity<?> followUser(String followerUserId, String userIdToFollow);

    List<Users> getUsersByCommunityMemberRoleAndFullName(String fullName);

    List<ResidentPostDTO> getResidentPostsByUserId(String userId);

    ResidentProfile updateResidentPost(String userId, String postId, MultipartFile file, AddResidentPostDTO addResidentPostDTO);

    ResidentProfile addResidentPost(String userId, MultipartFile file, AddResidentPostDTO addResidentPostDTO);

    String deleteComentaire(String postId, String userId, String commentId);

    String changePhotoProfil(MultipartFile file, String userId);

    String changeCoverPhoto(MultipartFile file, String userId);

    List<Notification> getUserNotifications(String userId);

    String likePost(String postId, String userId);

    String dislikePost(String postId, String userId);

    ResidentPost getPost(String postId);

    Set<Users> getFollowersByPartialName(String userId, String partialName);
}
