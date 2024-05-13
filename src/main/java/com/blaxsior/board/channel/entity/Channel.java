package com.blaxsior.board.channel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Table(name="channel")
@Entity
@Getter
@Setter
public class Channel {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="activated")
    private Boolean activated;

    @CreatedDate
    private LocalDateTime createdAt;
}
