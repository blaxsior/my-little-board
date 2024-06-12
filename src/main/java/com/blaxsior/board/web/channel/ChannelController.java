package com.blaxsior.board.web.channel;

import com.blaxsior.board.domain.channel.service.ChannelService;
import com.blaxsior.board.domain.global.exception.NotFoundException;
import com.blaxsior.board.domain.member.annotation.LoginMember;
import com.blaxsior.board.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/chan/{chanCode}")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    /**
     * 기본 페이지
     */
    @GetMapping
    public String channelIndexPage(@PathVariable("chanCode") String chanCode, Model model) {
        var channel = channelService.findChannel(chanCode);
        if(channel == null) {
            throw new NotFoundException();
        }
        model.addAttribute("channel", channel);
        return "channel/index";
    }


    /**
     * 채널 생성
     */
    @GetMapping("/create")
    public String createChannelPage() {

        return "channel/createPage";
    }

    /**
     * 게시글 조회
     */
    @GetMapping("/{postId}")
    public String postPage(@PathVariable("postId") Integer postId) {
        return "channel/post";
    }

}
