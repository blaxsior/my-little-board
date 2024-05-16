package com.blaxsior.board.member.entity;

import com.blaxsior.board.channel.entity.Channel;
import com.blaxsior.board.comment.entity.Comment;
import com.blaxsior.board.member_chan_role.entity.MemberChannelRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Table(name = "member")
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name ="login_id", unique = true)
    private String loginId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

    @OneToMany(mappedBy = "member")
    private List<MemberChannelRole> memberRoles;

    @CreatedDate
    private LocalDateTime createdAt;
}
