package sk.ukf.restapi.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {
    private T data;
    private String message;
    private String datetime;

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
        this.datetime = LocalDateTime.now().toString();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(null, message);
    }

    // Gettery a settery

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}