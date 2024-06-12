package com.blaxsior.board.domain.channel.service;

import com.blaxsior.board.domain.channel.entity.Channel;
import com.blaxsior.board.domain.channel.repository.ChannelRepository;
import com.blaxsior.board.domain.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    @Override
    @Transactional
    public void createChannel(String chanCode, String name, String description) {
        // 채널 코드 및 이름은 중복되면 안됨.
        boolean chanExist = channelRepository.existsByChanCodeOrName(chanCode, name);
        // 이미 존재하는 채널 => 안만듬
        if(chanExist) {
            throw new RuntimeException("channel already exists");
        }
        
        var channel = Channel.builder()
                .chanCode(chanCode)
                .name(name)
                .description(description)
                .build();
        // 채널 생성
        channelRepository.save(channel);
    }

    @Override
    public void changeChannelDescription(String chanCode, String description) {
    }

    @Override
    public Channel findChannel(String chanCode) {
        var optChannel = channelRepository.findByChanCode(chanCode);
        return optChannel.orElse(null);
    }
}
