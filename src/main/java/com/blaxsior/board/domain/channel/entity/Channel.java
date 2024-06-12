package com.blaxsior.board.domain.channel.entity;

import com.blaxsior.board.domain.member_chan_role.entity.MemberChannelRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name="channel")
@Entity
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chan_code", unique = true)
    private String chanCode;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name="description")
    private String description;

    @Builder.Default // default 값으로 사용
    @Column(name="enabled")
    private boolean enabled = true;

    @OneToMany(mappedBy = "channel")
    private List<MemberChannelRole> memberRoles;

    @CreatedDate
    private LocalDateTime createdAt;
}
