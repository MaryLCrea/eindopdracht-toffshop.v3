package toff.novi.eindopdrachttoffshop.dtos;

import jakarta.validation.constraints.NotNull;

public class CartRequestDto {

    @NotNull(message = "User ID is required")
    private Integer userId;

    public CartRequestDto() {}

    public CartRequestDto(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}