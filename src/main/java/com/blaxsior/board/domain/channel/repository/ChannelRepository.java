package com.blaxsior.board.domain.channel.repository;

import com.blaxsior.board.domain.channel.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    boolean existsByChanCodeOrName(String chanCode, String name);
    Optional<Channel> findByChanCode(String chanCode);
}
