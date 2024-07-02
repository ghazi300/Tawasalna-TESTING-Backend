package com.tawasalna.social.controller;


import com.tawasalna.shared.fileupload.IFileManagerService;
import com.tawasalna.shared.userapi.model.ResidentProfile;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.social.businesslogic.residentprofile.IResidentProfileService;
import com.tawasalna.social.models.Notification;
import com.tawasalna.social.models.ResidentComments;
import com.tawasalna.social.models.ResidentPost;
import com.tawasalna.social.payload.request.*;
import com.tawasalna.social.repository.ResidentPostRepository;
import com.tawasalna.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/residentprofile")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ResidentProfileController {

    private final IResidentProfileService residentProfileService;
    private final ResidentPostRepository residentPostRepository;
    private final UserRepository userRepository;
    private final IFileManagerService fileManagerService;

    @PutMapping("/updateresidenprofile/{userId}")
    public ResponseEntity<?> updateResidentProfile(@PathVariable String userId,
                                                   @RequestBody ResidentProfileDTO updateDTO) {
        try {
            residentProfileService.updateResidentProfileByUserId(userId, updateDTO);
            return ResponseEntity.ok("Resident profile updated successfully");
        } catch (Exception e) {
            log.error("Failed to update resident profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update resident profile");
        }
    }

    @GetMapping("/getresidentpostsWithID/{userId}")
    public ResponseEntity<?> getResidentPostsByUserIds(@PathVariable String userId) {
        try {
            List<ResidentPostDTO> residentPosts = residentProfileService.getResidentPostsByUserId(userId);
            if (residentPosts.isEmpty()) {
                return ResponseEntity.ok("No posts with captions found for user");
            } else {
                return ResponseEntity.ok(residentPosts);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve resident posts with captions");
        }
    }

    @GetMapping("/getresidentprofile/{userId}")
    public ResponseEntity<?> getResidentProfileById(@PathVariable String userId) {
        try {
            ResidentProfile residentProfile = residentProfileService.getResidentProfileById(userId);
            return ResponseEntity.ok(residentProfile);
        } catch (Exception e) {
            log.error("Failed to retrieve resident profile", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve resident profile");
        }
    }

    @PutMapping("/updateprofilepictures/{userId}")
    public ResponseEntity<?> updateProfilePictures(
            @PathVariable String userId,
            @RequestParam("profilePhoto") MultipartFile profilePhoto) {
        try {

            if (profilePhoto == null || profilePhoto.isEmpty()) {
                return ResponseEntity.badRequest().body("No profile photo provided");
            }
            ProfilePictureDTO profilePictureDTO = new ProfilePictureDTO();
            profilePictureDTO.setProfilephoto(profilePhoto);
            residentProfileService.updateProfilePicture(userId, profilePictureDTO);
            return ResponseEntity.ok("Profile pictures updated successfully");
        } catch (Exception e) {
            log.error("Failed to update profile pictures", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile pictures");
        }
    }

    @PutMapping("/updatecoverpictures/{userId}")
    public ResponseEntity<?> updateCoverPictures(
            @PathVariable String userId,
            @RequestParam("coverPhoto") MultipartFile coverPhoto) {
        try {
            CoverPictureDTO coverPictureDTO = new CoverPictureDTO();
            coverPictureDTO.setCoverphoto(coverPhoto);
            residentProfileService.updateCoverPicture(userId, coverPictureDTO);
            return ResponseEntity.ok("Cover picture updated successfully");
        } catch (Exception e) {
            log.error("Failed to update cover pictures", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update cover pictures");
        }
    }


    @GetMapping("/getprofilephoto/{userId}")
    public ResponseEntity<?> getProfilePhoto(@PathVariable String userId) {
        try {
            String profilePhotoPath = residentProfileService.getProfilePhotoByUserId(userId);

            if (profilePhotoPath != null && !profilePhotoPath.isEmpty()) {
                try {
                    Resource imageResource = residentProfileService.loadProfilePhoto(profilePhotoPath);

                    if (imageResource.exists() && imageResource.isReadable()) {
                        String contentType = "image/jpeg";
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.parseMediaType(contentType));

                        return ResponseEntity.ok()
                                .headers(headers)
                                .body(imageResource);
                    } else {
                        System.out.println("Profile photo not found at path: " + profilePhotoPath + " for user with ID: " + userId);
                        return ResponseEntity.ok().build(); // Return 200 OK with no body to indicate missing photo
                    }
                } catch (Exception e) {
                    System.out.println("Profile photo file not found for user with ID: " + userId + " at path: " + profilePhotoPath);
                    return ResponseEntity.ok().build(); // Return 200 OK with no body to indicate missing photo
                }
            } else {
                System.out.println("No profile photo found for user with ID: " + userId);
                return ResponseEntity.ok().build(); // Return 200 OK with no body to indicate missing photo
            }
        } catch (Exception e) {
            System.err.println("Error retrieving profile photo for user with ID: " + userId + ". Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve profile photo");
        }
    }


    @GetMapping("/getcoverphoto/{userId}")
    public ResponseEntity<?> getCoverPhoto(@PathVariable String userId) {
        try {
            String coverPhotoPath = residentProfileService.getCoverPhotoByUserId(userId);
            if (coverPhotoPath != null && !coverPhotoPath.isEmpty()) {

                Resource imageResource = residentProfileService.loadCoverPhoto(coverPhotoPath);

                String contentType = "image/jpeg";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(contentType));

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(imageResource);
            } else {
                System.out.println("No cover photo found for user with ID: " + userId);
                return ResponseEntity.ok().build(); //
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve Cover photo");
        }
    }


    @PostMapping("/sendVerificationCode/{userId}")
    public ResponseEntity<String> sendPhoneNumberVerificationCode(@PathVariable String userId, @RequestBody String phoneNumber) {
        try {
            residentProfileService.sendVerificationCodePhoneNumber(userId, phoneNumber);
            return ResponseEntity.ok("Verification code sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send verification code: " + e.getMessage());
        }

    }


    @PostMapping("/addresidentpost/{userId}")
    public ResponseEntity<?> addResidentPost(
            @PathVariable String userId,
            @ModelAttribute AddResidentPostDTO addResidentPostDTO) {
        try {
            ResidentProfile residentProfile = null;

            // Check if the DTO contains a photo, video, or caption
            if (addResidentPostDTO.getPhotos() != null || addResidentPostDTO.getVideo() != null || addResidentPostDTO.getCaption() != null) {
                // Call the service method to add the post
                residentProfile = residentProfileService.addResidentPost(userId, addResidentPostDTO);
            } else {
                // If none of the required fields are provided, return a bad request response
                return ResponseEntity.badRequest().body("Please provide a photo, a video, or a caption.");
            }

            return ResponseEntity.ok(residentProfile);
        } catch (Exception e) {
            log.error("Failed to add resident post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add resident post");
        }
    }


    // Définition de l'endpoint pour récupérer une image
    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        return ResponseEntity.ok(fileManagerService.getFileWithMediaType(imageName, "postPhotos"));
    }

    @GetMapping("/videos/{videoName}")
    public ResponseEntity<Resource> getVideo(@PathVariable String videoName) throws IOException {
        return ResponseEntity.ok(fileManagerService.getFileWithMediaType(videoName, "postVideos"));
    }

    @GetMapping("/getresidentpostsWithPhotos/{userId}")
    public ResponseEntity<?> getResidentPostsByUserIdWithPhotos(@PathVariable String userId) {
        try {
            List<ResidentPost> residentPosts = residentProfileService.getResidentPostsByUserIdWithPhotos(userId);
            if (residentPosts.isEmpty()) {
                return ResponseEntity.ok("No posts with photos found for user");
            } else {
                return ResponseEntity.ok(residentPosts);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve resident posts with photos");
        }
    }

    @GetMapping("/getresidentpostsWithVideos/{userId}")
    public ResponseEntity<?> getResidentPostsByUserIdWithVideos(@PathVariable String userId) {
        try {
            List<ResidentPost> residentPosts = residentProfileService.getResidentPostsByUserIdWithVideos(userId);
            if (residentPosts.isEmpty()) {
                return ResponseEntity.ok("No posts with videos found for user");
            } else {
                return ResponseEntity.ok(residentPosts);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve resident posts with videos");
        }
    }


    @GetMapping("/getresidentpostsWithCaptions/{userId}")
    public ResponseEntity<?> getResidentPostsByUserIdWithCaptions(@PathVariable String userId) {
        try {
            List<ResidentPost> residentPosts = residentProfileService.getResidentPostsByUserIdWithCaptions(userId);
            if (residentPosts.isEmpty()) {
                return ResponseEntity.ok("No posts with captions found for user");
            } else {
                return ResponseEntity.ok(residentPosts);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve resident posts with captions");
        }
    }

    @DeleteMapping("/deleteresidentpost/{userId}/{postId}")
    public ResponseEntity<?> deleteResidentPost(@PathVariable String userId, @PathVariable String postId) {
        try {
            // Find the user
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Find the post
            ResidentPost post = residentPostRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("Post not found"));

            // Check if the post belongs to the user
            if (!post.getUser().getId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to delete this post");
            }

            // Delete the post
            residentPostRepository.deleteById(postId);

            // Remove the post ID from the user's profile
            userRepository.save(user);

            return ResponseEntity.ok("Post deleted successfully");
        } catch (Exception e) {
            log.error("Failed to delete resident post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete resident post");
        }
    }


    @PutMapping("/likepost/{postId}/{userId}")
    public ResponseEntity<?> likePost(@PathVariable String postId, @PathVariable String userId) {
        try {
            String response = residentProfileService.likePost(postId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to like post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to like post");
        }
    }


    @DeleteMapping("/dislikepost/{postId}/{userId}")
    public ResponseEntity<?> dislikePost(@PathVariable String postId, @PathVariable String userId) {
        try {
            String response = residentProfileService.dislikePost(postId, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Failed to dislike post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to dislike post");
        }
    }


    @PostMapping("/addcomment/{postId}/{userId}")
    public ResponseEntity<?> addComment(@PathVariable String postId, @PathVariable String userId, @RequestBody Map<String, String> requestBody) {
        try {
            String commentText = requestBody.get("commentText");
            residentProfileService.addCommentToPost(postId, userId, commentText);
            return ResponseEntity.ok("Comment added successfully");
        } catch (Exception e) {
            log.error("Failed to add Comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add comment");
        }
    }


    @PostMapping("/replytocomment/{userId}/{commentId}")
    public ResponseEntity<?> replyToComment(
            @PathVariable String userId,
            @PathVariable String commentId,
            @RequestBody Map<String, String> requestBody) {
        try {
            String replyText = requestBody.get("replyText");
            ResidentComments updatedComment = residentProfileService.replyToComment(userId, commentId, replyText);
            return ResponseEntity.ok(updatedComment);
        } catch (Exception e) {
            log.error("Failed to reply to comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reply to comment");
        }
    }

    @GetMapping("/getreplies/{commentId}")
    public ResponseEntity<?> getRepliesByCommentId(@PathVariable String commentId) {
        try {
            List<ReplyDTO> replies = residentProfileService.getRepliesByCommentId(commentId);
            if (replies.isEmpty()) {
                return ResponseEntity.ok("No replies found for the specified comment");
            } else {
                return ResponseEntity.ok(replies);
            }
        } catch (Exception e) {
            log.error("Failed to retrieve replies for the specified comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve replies");
        }
    }


    @GetMapping("/getcomments/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable String postId) {
        try {
            List<ResidentCommentsDTO> comments = residentProfileService.getAllCommentsByPostId(postId);
            if (comments.isEmpty()) {
                return ResponseEntity.ok("No comments found for the specified post");
            } else {
                return ResponseEntity.ok(comments);
            }
        } catch (Exception e) {
            log.error("Failed to retrieve comments for the specified post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve comments");
        }
    }

    @GetMapping("/getallresidentposts")
    public ResponseEntity<?> getAllResidentPosts() {
        //  try {
        List<ResidentPostDTO> allResidentPosts = residentProfileService.getAllPosts();

        if (allResidentPosts.isEmpty()) {
            return ResponseEntity.ok("No posts found ");
        } else {
            return ResponseEntity.ok(allResidentPosts);
        }
    }

    @PostMapping("/followUser/{followerUserId}/{userIdToFollow}")
    public ResponseEntity<?> followUser(@PathVariable String
                                                followerUserId, @PathVariable String userIdToFollow) {
        try {
            residentProfileService.followUser(followerUserId, userIdToFollow);
            return ResponseEntity.ok("User followed successfully");
        } catch (Exception e) {
            log.error("Failed to follow user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to follow user");
        }
    }

    @DeleteMapping("/unfollowuser/{followerUserId}/{userIdToUnfollow}")
    public ResponseEntity<?> unfollowUser(
            @PathVariable String followerUserId,
            @PathVariable String userIdToUnfollow) {
        try {
            residentProfileService.unfollowUser(followerUserId, userIdToUnfollow);
            return ResponseEntity.ok("User unfollowed successfully");
        } catch (Exception e) {
            log.error("Failed to unfollow user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to unfollow user");
        }
    }

    @PutMapping("/updateresidentpost/{userId}/{postId}")
    public ResponseEntity<?> UpdateResidentPost(
            @RequestParam(value = "file", required = false) MultipartFile
                    file,
            @PathVariable String userId,
            @PathVariable String postId,
            AddResidentPostDTO addResidentPostDTO) {
        try {
            ResidentProfile residentProfile = null;

            if (file != null || addResidentPostDTO.getCaption() != null) {

                residentProfile = residentProfileService.updateResidentPost(userId, postId, file, addResidentPostDTO);
            } else {
                // If none of the required fields are provided, return a bad request response
                return ResponseEntity.badRequest().body("Please provide a photo, a video, or a caption.");
            }

            return ResponseEntity.ok(residentProfile);
        } catch (Exception e) {
            log.error("Failed to add resident post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add resident post");
        }
    }

    @GetMapping("/usersByFullName")
    public ResponseEntity<?> getUsersByFullName(@RequestParam("fullName") String fullName) {
        try {
            List<Users> users = residentProfileService.getUsersByCommunityMemberRoleAndFullName(fullName);
            if (users.isEmpty()) {
                return ResponseEntity.ok("No users found for the specified full name");
            } else {
                return ResponseEntity.ok(users);
            }
        } catch (Exception e) {
            log.error("Failed to retrieve users by full name", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve users by full name");
        }
    }

    @DeleteMapping("/deleteComentaire/{postId}/{userId}/{commentId}")
    public ResponseEntity<?> deleteComentaire(
            @PathVariable String postId,
            @PathVariable String userId,
            @PathVariable String commentId) {
        try {
            String message = residentProfileService.deleteComentaire(postId, userId, commentId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            log.error("Failed to delete Comentaire", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to delete Comentaire");
        }
    }

    @PutMapping("/changePhotoProfil/{userId}")
    public ResponseEntity<?> changeProfilePhoto
            (@RequestParam(value = "file", required = false) MultipartFile
                     file, @PathVariable String userId) {
        try {
            String message = residentProfileService.changePhotoProfil(file, userId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            log.error("Failed to delete Comentaire", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to delete Comentaire");
        }
    }

    @PutMapping("/changeCoverPhoto/{userId}")
    public ResponseEntity<?> changeCoverPhoto
            (@RequestParam(value = "file", required = false) MultipartFile
                     file, @PathVariable String userId) {
        try {
            String message = residentProfileService.changeCoverPhoto(file, userId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            log.error("Failed to delete Comentaire", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to delete Comentaire");
        }
    }

    @GetMapping("/getusernotifications/{userId}")
    public ResponseEntity<?> getUserNotifications(@PathVariable String
                                                          userId) {
        try {
            List<Notification> notifications = residentProfileService.getUserNotifications(userId);
            if (notifications.isEmpty()) {
                return ResponseEntity.ok("No notifications found");
            } else {
                return ResponseEntity.ok(notifications);
            }
        } catch (Exception e) {
            log.error("Failed to retrieve notifications", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve notifications");
        }
    }

    @GetMapping("/getPost/{postId}")
    public ResponseEntity<?> getPost(@PathVariable String postId) {
        try {
            return ResponseEntity.ok(residentProfileService.getPost(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve resident post");
        }
    }

    @GetMapping("/followers/search/{userId}")
    public Set<Users> getFollowersByPartialName(@PathVariable String userId, @RequestParam String partialName) {
        return residentProfileService.getFollowersByPartialName(userId, partialName);
    }
}
