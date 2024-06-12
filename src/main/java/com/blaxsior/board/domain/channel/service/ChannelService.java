package com.blaxsior.board.domain.channel.service;

import com.blaxsior.board.domain.channel.entity.Channel;

import java.util.List;

public interface ChannelService {
    // TODO: 채널 생성 유저에게 관리자 권한 주는 코드 작성
    void createChannel(String chanCode, String name, String description);
    void changeChannelDescription(String chanCode, String description);

    Channel findChannel(String chanCode);

}
