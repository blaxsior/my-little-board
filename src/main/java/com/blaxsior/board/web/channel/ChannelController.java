package com.blaxsior.board.web.channel;

import com.blaxsior.board.domain.channel.service.ChannelService;
import com.blaxsior.board.domain.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/channel/{chanCode}")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    /**
     * 기본 페이지
     */
    @GetMapping
    public String channelIndexPage(@PathVariable("chanCode") String chanCode, Model model) {
        log.info(chanCode);
        var channel = channelService.findChannel(chanCode);
        if (channel == null) {
            throw new NotFoundException();
        }
        model.addAttribute("channel", channel);
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
