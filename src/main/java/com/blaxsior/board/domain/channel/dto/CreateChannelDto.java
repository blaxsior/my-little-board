package com.blaxsior.board.domain.channel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateChannelDto {
    @NotBlank
    private String chanCode;
    @NotBlank
    private String name;

    private String description;
}
