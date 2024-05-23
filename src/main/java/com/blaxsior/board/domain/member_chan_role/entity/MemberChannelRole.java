package com.blaxsior.board.domain.member_chan_role.entity;

import com.blaxsior.board.domain.channel.entity.Channel;
import com.blaxsior.board.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Table(name = "member_channel_role")
@Entity
public class MemberChannelRole {

    @EmbeddedId
    private MemberChanRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chan_id")
    private Channel channel;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ChanAdminRole role;

    /**
     * MemberChannelRole 테이블은 member_id + chan_id를 복합 primary key로 사용
     */
    @Getter
    @Setter
    @Embeddable
    @EqualsAndHashCode
    public static class MemberChanRoleId implements Serializable {
        @Column(name = "member_id", insertable = false, updatable = false)
        private Long memberId;

        @Column(name = "chan_id", insertable = false, updatable = false)
        private Long chanId;
    }
}
