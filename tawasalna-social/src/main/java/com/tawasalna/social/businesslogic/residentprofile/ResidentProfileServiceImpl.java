package com.tawasalna.social.businesslogic.residentprofile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tawasalna.shared.sms.ISmsService;
import com.tawasalna.shared.userapi.model.AccountType;
import com.tawasalna.shared.userapi.model.Gender;
import com.tawasalna.shared.userapi.model.ResidentProfile;
import com.tawasalna.shared.userapi.model.Users;
import com.tawasalna.social.config.MyWebSocketHandler;
import com.tawasalna.social.models.Notification;
import com.tawasalna.social.models.Reply;
import com.tawasalna.social.models.ResidentComments;
import com.tawasalna.social.models.ResidentPost;
import com.tawasalna.social.payload.request.*;
import com.tawasalna.social.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class ResidentProfileServiceImpl implements IResidentProfileService {
    private final UserRepository userRepository;
    private final ResidentPostRepository residentPostRepository;
    private final ResidentCommentRepository residentCommentRepository;
    private final NotificationRepository notificationRepository;
    private final MyWebSocketHandler webSocketHandler;
    private final ISmsService smsService;

    private final ResidentProfileRepository residentProfileRepository;

    @Override
    public void updateResidentProfileByUserId(String userId, ResidentProfileDTO updateDTO) {
        Optional<Users> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (user.getResidentProfile() != null) {
                ResidentProfile residentProfile = user.getResidentProfile();

                if (updateDTO.getResidentId() != null) {
                    residentProfile.setResidentId(updateDTO.getResidentId());
                }
                if (updateDTO.getFullName() != null) {
                    residentProfile.setFullName(updateDTO.getFullName());
                }

                if (updateDTO.getAddress() != null) {
                    residentProfile.setAddress(updateDTO.getAddress());
                }
                if (updateDTO.getAge() != null) {
                    residentProfile.setAge(updateDTO.getAge());
                }
                if (updateDTO.getGender() != null) {
                    try {
                        Gender gender = Gender.valueOf(updateDTO.getGender());
                        residentProfile.setGender(gender);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid gender value: " + updateDTO.getGender());
                    }
                }
                if (updateDTO.getDateOfBirth() != null) {
                    residentProfile.setDateOfBirth(updateDTO.getDateOfBirth());
                }
                if (updateDTO.getBio() != null) {
                    residentProfile.setBio(updateDTO.getBio());
                }
                if (updateDTO.getAccounttype() != null) {
                    try {
                        AccountType accountType = AccountType.valueOf(updateDTO.getAccounttype());
                        residentProfile.setAccountType(accountType);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid accountType value: " + updateDTO.getAccounttype());
                    }
                }

                userRepository.save(user);
            } else {
                throw new RuntimeException("User does not have a resident profile");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public ResidentProfile getResidentProfileById(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getResidentProfile() != null) {
            return user.getResidentProfile();
        } else {
            throw new RuntimeException("Resident profile not found for user");
        }
    }

    @Override
    public ResidentProfile updateProfilePicture(String userId, ProfilePictureDTO profilePictureDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (profilePictureDTO.getProfilephoto() != null) {
            MultipartFile profilePictureFile = profilePictureDTO.getProfilephoto();
            String profilePictureFileName = UUID.randomUUID().toString() + "-" + profilePictureFile.getOriginalFilename();
            saveProfilePicture(profilePictureFile, userId, profilePictureFileName);
            user.getResidentProfile().setProfilephoto(profilePictureFileName);
        }
        userRepository.save(user);
        return user.getResidentProfile();
    }

    @Override
    public ResidentProfile updateCoverPicture(String userId, CoverPictureDTO coverPictureDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (coverPictureDTO.getCoverphoto() != null) {
            MultipartFile coverPictureFile = coverPictureDTO.getCoverphoto();
            String coverPictureFileName = UUID.randomUUID().toString() + "-" + coverPictureFile.getOriginalFilename();
            saveCoverPicture(coverPictureFile, userId, coverPictureFileName);
            user.getResidentProfile().setCoverphoto(coverPictureFileName);
        }

        userRepository.save(user);
        return user.getResidentProfile();
    }

    @Override
    public String deletePost(String postId, String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        ResidentPost residentPost = residentPostRepository.findById(postId).orElseThrow(() -> new RuntimeException("post not found"));
        if (user.equals(residentPost.getUser())) {
            residentPostRepository.delete(residentPost);
            return "user post deleted";
        } else {
            return "failed to delete post";
        }
    }

    public void saveProfilePicture(MultipartFile profilePictureFile, String userId, String fileName) {
        try {
            Path userDirectory = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\profilePhotos");
            if (!Files.exists(userDirectory)) {
                Files.createDirectories(userDirectory);
            }

            Path filePath = userDirectory.resolve(fileName);
            Files.copy(profilePictureFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save profile picture", e);
        }
    }

    @Override
    public void saveCoverPicture(MultipartFile coverPictureFile, String userId, String fileName) {
        try {
            Path userDirectory = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\coverPhotos");

            if (!Files.exists(userDirectory)) {
                Files.createDirectories(userDirectory);
            }

            Path filePath = userDirectory.resolve(fileName);
            Files.copy(coverPictureFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save profile picture", e);
        }
    }

    @Override
    public String getProfilePhotoByUserId(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getResidentProfile() != null && user.getResidentProfile().getProfilephoto() != null) {
            return user.getResidentProfile().getProfilephoto();
        } else {
            System.out.println("profil photo not found for user with ID: " + userId);
            return null;
        }
    }

    @Override
    public String getCoverPhotoByUserId(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getResidentProfile() != null && user.getResidentProfile().getCoverphoto() != null) {
            return user.getResidentProfile().getCoverphoto();
        } else {
            System.out.println("Cover photo not found for user with ID: " + userId);
            return null;
        }
    }

    @Override
    public Resource loadProfilePhoto(String profilePhotoPath) {
        try {
            Path filePath = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\profilePhotos", profilePhotoPath);

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Failed to read profile photo: " + profilePhotoPath);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL: " + profilePhotoPath, e);
        }
    }

    @Override
    public Resource loadCoverPhoto(String coverPhotoPath) {
        try {
            Path filePath = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\coverPhotos", coverPhotoPath);

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Failed to read profile photo: " + coverPhotoPath);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL: " + coverPhotoPath, e);
        }
    }

    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            verificationCode.append(random.nextInt(10));
        }
        return verificationCode.toString();
    }

    @Override
    public void sendVerificationCodePhoneNumber(String userId, String phoneNumber) {
        try {
            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Generate the verification code
            String verificationCode = generateVerificationCode();

            // Send the verification code via SMS
            smsService.deliverMessage(phoneNumber, "Your verification code is: " + verificationCode);

            // Update the resident profile with the verification code
            user.getResidentProfile().setVerificationCode(verificationCode);
            userRepository.save(user);

            log.info("Verification code sent successfully to {}", phoneNumber);
        } catch (Exception e) {
            log.error("Error sending verification code via SMS: {}", e.getMessage());
            throw new RuntimeException("Error sending verification code via SMS", e);
        }
    }

    @Override
    public ResidentProfile addResidentPost(String userId, AddResidentPostDTO addResidentPostDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ResidentPost residentPost = new ResidentPost();
        residentPost.setUser(user);
        residentPost.setPostDateTime(new Date());
        residentPost.setCaption(addResidentPostDTO.getCaption());

        // Handle multiple photos
        List<MultipartFile> photoFiles = addResidentPostDTO.getPhotos();
        if (photoFiles != null && !photoFiles.isEmpty()) {
            for (MultipartFile photoFile : photoFiles) {
                String photoFileName = UUID.randomUUID().toString() + "-" + photoFile.getOriginalFilename();
                savePostPhoto(photoFile, userId, photoFileName);
                residentPost.getPhotos().add(photoFileName);
            }
        }

        // Handle single video
        if (addResidentPostDTO.getVideo() != null) {
            MultipartFile videoFile = addResidentPostDTO.getVideo();
            String originalVideoFileName = videoFile.getOriginalFilename();
            String sanitizedVideoFileName = originalVideoFileName.replaceAll("#", "");
            String videoFileName = UUID.randomUUID().toString() + "-" + sanitizedVideoFileName;
            savePostVideo(videoFile, userId, videoFileName);
            residentPost.setVideo(videoFileName);
        }

        residentPostRepository.save(residentPost);

        userRepository.save(user);

        return user.getResidentProfile();
    }


    private void savePostPhoto(MultipartFile photoFile, String userId, String fileName) {
        try {
            Path userDirectory = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\postPhotos");

            if (!Files.exists(userDirectory)) {
                Files.createDirectories(userDirectory);
            }

            Path filePath = userDirectory.resolve(fileName);
            Files.copy(photoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save post photo", e);
        }
    }


    private void savePostVideo(MultipartFile videoFile, String userId, String fileName) {
        try {
            String validFileName = fileName.replace("#", "");

            Path userDirectory = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\postVideos");

            if (!Files.exists(userDirectory)) {
                Files.createDirectories(userDirectory);
            }

            Path filePath = userDirectory.resolve(validFileName);
            Files.copy(videoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save post video", e);
        }
    }


    @Override
    public Resource loadPostPhoto(String photoFileName) {
        try {
            Path filePath = Paths.get(System.getProperty("user.home") + "\\Tawasalna\\postPhotos", photoFileName);

            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Failed to read post photo: " + photoFileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL: " + photoFileName, e);
        }
    }

    @Override
    public List<ResidentPost> getResidentPostsByUserIdWithPhotos(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ResidentPost> residentPosts = residentPostRepository.findPostsByUserIdAndPhotosNotNull(userId);

        if (!residentPosts.isEmpty()) {
            return residentPosts;
        } else {
            log.info("No posts with photos found for user {}", userId);
            return Collections.emptyList(); // Return an empty list
        }
    }

    @Override
    public List<ResidentPost> getResidentPostsByUserIdWithVideos(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ResidentPost> residentPosts = residentPostRepository.findPostsByUserIdAndVideoNotNull(userId);
        if (!residentPosts.isEmpty()) {
            return residentPosts;
        } else {
            log.info("No posts with videos found for user {}", userId);
            return new ArrayList<>();
        }
    }

    @Override
    public List<ResidentPost> getResidentPostsByUserIdWithCaptions(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ResidentPost> residentPosts = residentPostRepository.findByUserIdAndPhotosEmptyAndVideoIsNull(userId);
        if (!residentPosts.isEmpty()) {
            return residentPosts;
        } else {
            log.info("No posts with captions found for user {}", userId);
            return new ArrayList<>();
        }


    }


    private void commentNotification(String postId, String userId) {
        ResidentPost post = residentPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("post not found"));

        Users userToLike = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(" user not found"));

        Users postOwner = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new RuntimeException(" post Owner not found"));


        Notification notification = new Notification();
        notification.setRecipientUser(postOwner);
        notification.setSenderUser(userToLike);
        notification.setMessage(postOwner.getResidentProfile().getFullName() + " added a comment to your post.");
        notification.setPostId(post.getId());
        notification.setType("commentNotification");
        notification.setCreatedAt(new Date());
        notification.setRead(false);

        notificationRepository.save(notification);

        postOwner.getResidentProfile().getNotifications().add(notification.getId());
        userRepository.save(postOwner);

        String jsonMessage = convertNotificationToJson(notification);
        webSocketHandler.sendCommentNotification(postOwner.getId(), jsonMessage);
    }

    @Override
    public void addCommentToPost(String postId, String userId, String commentText) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ResidentPost post = residentPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("post not found"));

        ResidentComments comment = new ResidentComments();
        comment.setPost(post);
        comment.setUser(user);
        comment.setText(commentText);
        comment.setCreatedAt(new Date());

        residentCommentRepository.save(comment);
        post.getComments().add(comment);
        residentPostRepository.save(post);
        if (!user.getId().equals(userId)) {
            commentNotification(postId, userId);
        }
    }


    private void replytNotification(String commentId, String userId) {
        ResidentComments comment = residentCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("comment not found"));

        Users userToComment = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(" user not found"));

        Users commentOwner = userRepository.findById(comment.getUser().getId())
                .orElseThrow(() -> new RuntimeException(" comment Owner not found"));


        Notification notification = new Notification();
        notification.setRecipientUser(commentOwner);
        notification.setSenderUser(userToComment);
        notification.setMessage(commentOwner.getResidentProfile().getFullName() + " added a reply to your comment.");
        notification.setPostId(comment.getPost().getId());
        notification.setType("replyNotification");
        notification.setCreatedAt(new Date());
        notification.setRead(false);

        notificationRepository.save(notification);

        commentOwner.getResidentProfile().getNotifications().add(notification.getId());
        userRepository.save(commentOwner);

        String jsonMessage = convertNotificationToJson(notification);
        webSocketHandler.sendReplyNotification(commentOwner.getId(), jsonMessage);
    }

    @Override
    public ResidentComments replyToComment(String userId, String commentId, String replyText) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ResidentComments comment = residentCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        Reply reply = new Reply();
        reply.setResidentId(userId);
        reply.setText(replyText);
        reply.setCreatedAt(new Date());

        if (comment.getReplies() == null) {
            comment.setReplies(new ArrayList<>());
        }
        comment.getReplies().add(reply);

        residentCommentRepository.save(comment);
        replytNotification(commentId, userId);
        log.info("User with ID {} replied to comment with ID {}", userId, commentId);
        return comment;
    }

    private String convertNotificationToJson(Notification notification) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(notification);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting notification to JSON", e);
        }
    }

    private boolean isImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".PNG") || fileName.endsWith(".gif"));
    }

    // Method to check if the file is a video
    private boolean isVideo(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && (fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mov") || fileName.endsWith(".mkv"));
    }


    @Override
    public List<ReplyDTO> getRepliesByCommentId(String commentId) {
        ResidentComments comment = residentCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        List<ReplyDTO> replyDTOs = new ArrayList<>();
        for (Reply reply : comment.getReplies()) {
            String residentId = reply.getResidentId();

            // Retrieve the user associated with the residentId
            Users user = userRepository.findById(residentId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Map the reply to a ReplyDTO and set user details
            ReplyDTO replyDTO = new ReplyDTO();
            replyDTO.setResidentId(residentId);
            replyDTO.setText(reply.getText());
            replyDTO.setCreatedAt(reply.getCreatedAt());
            replyDTO.setUserName(user.getResidentProfile().getFullName());

            replyDTOs.add(replyDTO);
        }

        return replyDTOs;
    }


    @Override
    public List<ResidentCommentsDTO> getAllCommentsByPostId(String postId) {

        ResidentPost post = residentPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        List<ResidentCommentsDTO> commentDTOs = new ArrayList<>();

        for (ResidentComments comment : post.getComments()) {
            String userId = comment.getUser().getId();

            Users user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ResidentCommentsDTO commentDTO = new ResidentCommentsDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setPostId(comment.getPost().getId());
            commentDTO.setResidentId(userId);
            commentDTO.setCommenttext(comment.getText());
            commentDTO.setCreatedAt(comment.getCreatedAt());
            commentDTO.setUserName(user.getResidentProfile().getFullName());
            commentDTO.setProfilePhoto(user.getResidentProfile().getProfilephoto());
            commentDTO.setReplies(comment.getReplies() != null ? comment.getReplies().size() : 0);
            commentDTOs.add(commentDTO);
        }

        return commentDTOs;
    }

    @Override
    public List<ResidentPostDTO> getAllPosts() {
        List<ResidentPost> allPosts = residentPostRepository.findAll();
        List<ResidentPostDTO> postDTOs = new ArrayList<>();

        for (ResidentPost post : allPosts) {
            ResidentPostDTO postDTO = new ResidentPostDTO();
            postDTO.setId(post.getId());
            postDTO.setResidentId(post.getUser().getId());
            postDTO.setCaption(post.getCaption());
            postDTO.setPostDateTime(post.getPostDateTime());
            postDTO.setPhotos(post.getPhotos());
            postDTO.setVideo(post.getVideo());
            postDTO.setLikedBy(post.getLikedBy());
            postDTO.setComments(post.getComments().stream().map(ResidentComments::getId).toList());

            ResidentProfile profile = post.getUser().getResidentProfile();

            if (profile != null) {
                postDTO.setUserName(profile.getFullName());
            }

            List<ResidentCommentsDTO> commentDTOs = residentCommentRepository
                    .findByPostId(post.getId())
                    .stream()
                    .map(comment -> {
                        final ResidentCommentsDTO commentDTO = new ResidentCommentsDTO();
                        commentDTO.setId(comment.getId());
                        commentDTO.setPostId(comment.getPost().getId());
                        commentDTO.setResidentId(comment.getUser().getId());
                        commentDTO.setCommenttext(comment.getText());
                        commentDTO.setUserName(comment.getUser().getUsername());
                        return commentDTO;
                    })
                    .toList();

            postDTO.setComments(residentCommentRepository.findByPostId(post.getId()).stream().map(ResidentComments::getId).toList());

            postDTOs.add(postDTO);
        }

        return postDTOs;
    }

    @Override
    public List<Users> getUsersByCommunityMemberRoleAndFullName(String fullName) {
        return userRepository.findByResidentProfileFullNameContainingIgnoreCase(fullName);
    }

    @Override
    public List<ResidentPostDTO> getResidentPostsByUserId(String userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<ResidentPost> residentPosts = residentPostRepository.findPostsByUserIdAndPhotosNotNull(userId);

        List<ResidentPostDTO> postDTOs = new ArrayList<>();

        for (ResidentPost post : residentPosts) {
            ResidentPostDTO postDTO = new ResidentPostDTO();
            postDTO.setId(post.getId());
            postDTO.setResidentId(post.getUser().getId());
            postDTO.setCaption(post.getCaption());
            postDTO.setPostDateTime(post.getPostDateTime());
            List<String> photos = new ArrayList<>();
            postDTO.setPhotos(photos);
            postDTO.setVideo(post.getVideo());
            postDTO.setLikedBy(post.getLikedBy());

            ResidentProfile profile = user.getResidentProfile();

            if ( profile !=null && profile.getFullName() != null && !profile.getFullName().isEmpty()) {
                postDTO.setUserName(profile.getFullName());
            } else {
                postDTO.setUserName("No Name");
            }
        }

        return postDTOs;
    }


    private void sendFollowRequestNotification(String followerUserId, String userIdToFollow) {
        Users user = userRepository.findById(followerUserId)
                .orElseThrow(() -> new RuntimeException("Follower user not found"));
        Users userTobfollowed = userRepository.findById(userIdToFollow)
                .orElseThrow(() -> new RuntimeException("Followed user not found"));

        Notification notification = new Notification();
        notification.setRecipientUser(userTobfollowed);
        notification.setSenderUser(user);
        notification.setMessage(user.getResidentProfile().getFullName() + " has requested to follow you. Do you accept?");
        notification.setType("followRequest");
        notification.setCreatedAt(new Date());
        notification.setRead(false);

        notificationRepository.save(notification);

        userTobfollowed.getResidentProfile().getNotifications().add(notification.getId());
        userRepository.save(userTobfollowed);

        String jsonMessage = convertNotificationToJson(notification);
        webSocketHandler.sendFollowRequestNotification(userIdToFollow, jsonMessage);
    }

    private void sendFollowNotification(String followerUserId, String
            userIdToFollow) {
        Users user = userRepository.findById(followerUserId)
                .orElseThrow(() -> new RuntimeException("Follower user not found"));

        Users userTobfollowed = userRepository.findById(userIdToFollow)
                .orElseThrow(() -> new RuntimeException("Followed user not found"));

        Notification notification = new Notification();
        notification.setRecipientUser(userTobfollowed);
        notification.setSenderUser(user);
        notification.setMessage(user.getResidentProfile().getFullName() + " started following you.");
        notification.setType("followNotification");

        notification.setCreatedAt(new Date());

        notification.setRead(false);

        notificationRepository.save(notification);

        userTobfollowed.getResidentProfile().getNotifications().add(notification.getId());

        userRepository.save(userTobfollowed);

        String jsonMessage = convertNotificationToJson(notification);

        webSocketHandler.sendFollowNotification(userIdToFollow, jsonMessage);
    }


    @Override
    public ResponseEntity<?> followUser(String followerUserId, String
            userIdToFollow) {
        Users followerUser = userRepository.findById(followerUserId)
                .orElseThrow(() -> new RuntimeException("Follower user not found"));

        Users userToFollow = userRepository.findById(userIdToFollow)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        ResidentProfile followerProfile = followerUser.getResidentProfile();
        ResidentProfile userToFollowProfile = userToFollow.getResidentProfile();

        if (userToFollowProfile.getAccountType() == AccountType.PRIVATE && !followerProfile.getFollowing().contains(userIdToFollow)) {
            sendFollowRequestNotification(followerUser.getId(), userToFollow.getId());
            log.info("Follow request notification sent from user with ID {} to user with ID {}", followerUserId, userIdToFollow);
            return ResponseEntity.ok("Follow request notification sent");
        } else {
            if (followerProfile.getFollowing() == null) {
                followerProfile.setFollowing(new ArrayList<>());
            }
            if (!followerProfile.getFollowing().contains(userIdToFollow)) {
                followerProfile.getFollowing().add(userIdToFollow);
            }

            if (userToFollowProfile.getFollowers() == null) {
                userToFollowProfile.setFollowers(new ArrayList<>());
            }
            if (!userToFollowProfile.getFollowers().contains(followerUserId)) {
                userToFollowProfile.getFollowers().add(followerUserId);
            }

            userRepository.save(followerUser);
            userRepository.save(userToFollow);
            sendFollowNotification(followerUser.getId(), userToFollow.getId());
            log.info("User with ID {} is now following user with ID {}", followerUserId, userIdToFollow);
            return ResponseEntity.ok("User followed");
        }
    }

    @Override
    public ResidentProfile updateResidentPost(String userId, String
            postId, MultipartFile file, AddResidentPostDTO addResidentPostDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("user id :" + user.getId());

        ResidentPost residentPost = residentPostRepository.findById(postId).orElseThrow(() -> new RuntimeException(("Post not Found")));
        System.out.println("post id :" + residentPost.getId());
        residentPost.setCaption(addResidentPostDTO.getCaption());

        if (file != null && !file.isEmpty() && isImage(file)) {
            String photoFileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            savePostPhoto(file, userId, photoFileName);
            residentPost.getPhotos().add(photoFileName);
        }

        if (file != null && !file.isEmpty() && isVideo(file)) {
            String originalVideoFileName = file.getOriginalFilename();
            String sanitizedVideoFileName = originalVideoFileName.replaceAll("#", "");
            String videoFileName = UUID.randomUUID() + "-" + sanitizedVideoFileName;
            savePostVideo(file, userId, videoFileName);
            residentPost.setVideo(videoFileName);
        }

        if (addResidentPostDTO.getCaption() != null) {
            residentPost.setCaption(addResidentPostDTO.getCaption());
        }

        residentPostRepository.save(residentPost);

        return user.getResidentProfile();
    }

    @Override
    public ResidentProfile addResidentPost(String userId, MultipartFile
            file, AddResidentPostDTO addResidentPostDTO) {
        return null;
    }


    @Override
    public void unfollowUser(String followerUserId, String userIdToUnfollow) {
        Users followerUser = userRepository.findById(followerUserId)
                .orElseThrow(() -> new RuntimeException("Follower user not found"));

        Users userToUnfollow = userRepository.findById(userIdToUnfollow)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        ResidentProfile followerProfile = followerUser.getResidentProfile();
        ResidentProfile userToUnfollowProfile = userToUnfollow.getResidentProfile();

        if (followerProfile.getFollowing() != null) {
            followerProfile.getFollowing().remove(userIdToUnfollow);
        }

        if (userToUnfollowProfile.getFollowers() != null) {
            userToUnfollowProfile.getFollowers().remove(followerUserId);
        }

        userRepository.save(followerUser);
        userRepository.save(userToUnfollow);

        log.info("User with ID {} unfollowed user with ID {}", followerUserId, userIdToUnfollow);
    }


    public String deleteComentaire(String postId, String userId, String
            commentId) {

        ResidentComments res = residentCommentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("residentComment not Found"));

        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        ResidentPost post = residentPostRepository.findById(postId).orElseThrow(() -> new RuntimeException("post not found"));

        if ((res.getUser().getId().equals(user.getId())) || (post.getUser().getId().equals(user.getId()))) {
            System.out.println("res.getResidentId() : " + res.getUser().getId());
            residentCommentRepository.delete(res);
            return "deleted";
        } else {
            return "coonot delete comment";
        }
    }

    @Override
    public String changePhotoProfil(MultipartFile file, String userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        ResidentProfile resprefile = user.getResidentProfile();
        MultipartFile photoFile = file;
        String photoFileName = UUID.randomUUID().toString() + "-" + photoFile.getOriginalFilename();
        savePostPhoto(photoFile, userId, photoFileName);
        resprefile.setProfilephoto(photoFileName);
        user.setResidentProfile(resprefile);
        residentProfileRepository.save(resprefile);
        userRepository.save(user);

        return "photo prefile saved";
    }

    @Override
    public String changeCoverPhoto(MultipartFile file, String userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("user not found"));
        ResidentProfile resprefile = user.getResidentProfile();
        MultipartFile photoFile = file;
        String photoFileName = UUID.randomUUID().toString() + "-" + photoFile.getOriginalFilename();
        savePostPhoto(photoFile, userId, photoFileName);
        resprefile.setCoverphoto(photoFileName);
        user.setResidentProfile(resprefile);
        residentProfileRepository.save(resprefile);
        userRepository.save(user);
        return "Cover photo saved";
    }

    @Override
    public List<Notification> getUserNotifications(String userId) {
        return notificationRepository.findByRecipientUserId(userId);
    }


    private void likeNotification(String postId, String userId) {
        ResidentPost post = residentPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("post not found"));

        Users userToLike = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(" user not found"));

        Users postOwner = userRepository.findById(post.getUser().getId())
                .orElseThrow(() -> new RuntimeException(" post Owner not found"));


        Notification notification = new Notification();
        notification.setRecipientUser(postOwner);
        notification.setSenderUser(userToLike);
        notification.setMessage(postOwner.getResidentProfile().getFullName() + " Liked your Post.");
        notification.setPostId(post.getId());
        notification.setType("likePostNotification");
        notification.setCreatedAt(new Date());
        notification.setRead(false);

        notificationRepository.save(notification);

        postOwner.getResidentProfile().getNotifications().add(notification.getId());
        userRepository.save(postOwner);

        String jsonMessage = convertNotificationToJson(notification);
        webSocketHandler.sendLikeNotification(postOwner.getId(), jsonMessage);
    }

    @Override
    public String likePost(String postId, String userId) {
        ResidentPost post = residentPostRepository.findById(postId).orElse(null);
        if (post == null) {
            return "Post not Found";
        }

        if (!post.getLikedBy().contains(userId)) {
            post.getLikedBy().add(userId);
            residentPostRepository.save(post);
            likeNotification(postId, userId);
            return "Post liked successfully";
        } else {
            return "You've already liked this post.";
        }
    }

    @Override
    public String dislikePost(String postId, String userId) {
        ResidentPost post = residentPostRepository.findById(postId).orElse(null);
        if (post == null) {
            return "Post not Found";
        }

        if (post.getLikedBy().contains(userId)) {
            post.getLikedBy().remove(userId);
            residentPostRepository.save(post);
            return "Post disliked successfully";
        } else {
            return "You haven't liked this post.";
        }
    }


    @Override
    public ResidentPost getPost(String postId) {
        ResidentPost post = residentPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("post not found"));

        return post;
    }


    @Override
    public Set<Users> getFollowersByPartialName(String userId, String partialName) {
        Users user = userRepository.findById(userId).orElse(null);
        Set<Users> followers = new HashSet<>();

        if (user != null && user.getResidentProfile() != null) {
            for (String followerId : user.getResidentProfile().getFollowers()) {
                Users follower = userRepository.findById(followerId).orElse(null);
                if (follower != null) {
                    followers.add(follower);
                    if (follower.getResidentProfile().getFullName().toLowerCase().contains(partialName.toLowerCase())) {
                        mentionNotification(userId, followerId);
                    }
                }
            }
        }

        return followers.stream()
                .filter(follower -> follower.getResidentProfile().getFullName().toLowerCase().contains(partialName.toLowerCase()))
                .collect(Collectors.toSet());
    }

    private void mentionNotification(String userToMetion, String usermentionned) {


        Users userTometion = userRepository.findById(userToMetion)
                .orElseThrow(() -> new RuntimeException(" user to mention not found"));

        Users userMentionned = userRepository.findById(usermentionned)
                .orElseThrow(() -> new RuntimeException(" user montionned  not found"));


        Notification notification = new Notification();
        notification.setRecipientUser(userMentionned);
        notification.setSenderUser(userTometion);
        notification.setMessage(userMentionned.getResidentProfile().getFullName() + " Mentionned you.");
        notification.setType("mentionNotification");
        notification.setCreatedAt(new Date());
        notification.setRead(false);

        notificationRepository.save(notification);

        userMentionned.getResidentProfile().getNotifications().add(notification.getId());
        userRepository.save(userMentionned);

        String jsonMessage = convertNotificationToJson(notification);
        webSocketHandler.sendMentionNotification(userMentionned.getId(), jsonMessage);
    }


}
