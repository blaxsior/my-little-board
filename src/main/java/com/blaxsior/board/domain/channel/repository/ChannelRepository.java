package com.blaxsior.board.domain.channel.repository;

import com.blaxsior.board.domain.channel.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
