package com.lin.controller;

import com.lin.common.api.ApiResult;
import com.lin.common.exception.ApiAsserts;
import com.lin.model.dto.CommentDTO;
import com.lin.model.entity.BmsComment;
import com.lin.model.entity.UmsUser;
import com.lin.model.vo.CommentVO;
import com.lin.service.IBmsCommentService;
import com.lin.service.IUmsUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.lin.jwt.JwtUtil.USER_NAME;


@RestController
@RequestMapping("/comment")
public class BmsCommentController extends BaseController {

    @Resource
    private IBmsCommentService bmsCommentService;
    @Resource
    private IUmsUserService umsUserService;

    @GetMapping("/get_comments")
    public ApiResult<List<CommentVO>> getCommentsByTopicID(@RequestParam(value = "topicid", defaultValue = "1") String topicid) {
        List<CommentVO> lstBmsComment = bmsCommentService.getCommentsByTopicID(topicid);
        return ApiResult.success(lstBmsComment);
    }

    @PostMapping("/add_comment")
    public ApiResult<BmsComment> add_comment(@RequestHeader(value = USER_NAME) String userName,
                                             @RequestBody CommentDTO dto) {
        if(dto.getContent()=="")
            ApiAsserts.fail("评论不能为空~");
        UmsUser user = umsUserService.getUserByUsername(userName);
        BmsComment comment = bmsCommentService.create(dto, user);
        return ApiResult.success(comment);
    }
}
