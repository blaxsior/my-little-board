package com.blaxsior.board.channel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chan/{chanName}")
public class ChannelController {

    /**
     * 기본 페이지
     */
    @GetMapping
    public String channelIndexPage(@PathVariable("chanName") String chanId) {
        return "channel/index";
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/{postId}")
    public String postPage(@PathVariable("postId") Integer postId) {
        return "channel/post";
    }
}
