package com.tawasalna.shared.exceptions;



public class ChatRoomNotFoundException extends RuntimeException {
    public ChatRoomNotFoundException() {
        super();
    }

    public ChatRoomNotFoundException(String message) {
        super(message);
    }

    public ChatRoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatRoomNotFoundException(Throwable cause) {
        super(cause);
    }
}
