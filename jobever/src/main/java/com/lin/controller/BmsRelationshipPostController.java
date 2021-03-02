package com.lin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.common.api.ApiResult;
import com.lin.common.exception.ApiAsserts;
import com.lin.model.entity.BmsFollowPost;
import com.lin.model.entity.UmsUser;
import com.lin.model.vo.PostVO;
import com.lin.service.IBmsFollowPostService;
import com.lin.service.IBmsPostService;
import com.lin.service.IUmsUserService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.lin.jwt.JwtUtil.USER_NAME;

@RestController
@RequestMapping("/followPost")
public class BmsRelationshipPostController extends BaseController {

    @Resource
    private IBmsFollowPostService bmsFollowPostService;

    @Resource
    private IUmsUserService umsUserService;

    @Resource
    private IBmsPostService iBmsPostService;

    @GetMapping("/subscribe/{postId}")
    public ApiResult<Object> handleFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("postId") String postId) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        BmsFollowPost one = bmsFollowPostService.getOne(
                new LambdaQueryWrapper<BmsFollowPost>()
                        .eq(BmsFollowPost::getPostId, postId)
                        .eq(BmsFollowPost::getFollowerId, umsUser.getId()));
        if (!ObjectUtils.isEmpty(one)) {
            ApiAsserts.fail("已收藏!");
        }

        BmsFollowPost follow = new BmsFollowPost();
        follow.setPostId(postId);
        follow.setFollowerId(umsUser.getId());
        bmsFollowPostService.save(follow);
        return ApiResult.success(null, "收藏成功");
    }

    @GetMapping("/unsubscribe/{postId}")
    public ApiResult<Object> handleUnFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("postId") String postId) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        BmsFollowPost one = bmsFollowPostService.getOne(
                new LambdaQueryWrapper<BmsFollowPost>()
                        .eq(BmsFollowPost::getPostId, postId)
                        .eq(BmsFollowPost::getFollowerId, umsUser.getId()));
        if (ObjectUtils.isEmpty(one)) {
            ApiAsserts.fail("未关注！");
        }
        bmsFollowPostService.remove(new LambdaQueryWrapper<BmsFollowPost>().eq(BmsFollowPost::getPostId, postId)
                .eq(BmsFollowPost::getFollowerId, umsUser.getId()));
        return ApiResult.success(null, "取消成功！");
    }

    // 验证用户是否已关注
    @GetMapping("/validate/{postId}")
    public ApiResult<Map<String, Object>> isFollow(@RequestHeader(value = USER_NAME) String userName
            , @PathVariable("postId") String postId) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        Map<String, Object> map = new HashMap<>(16);
        map.put("hasFollowPost", false);
        if (!ObjectUtils.isEmpty(umsUser)) {
            BmsFollowPost one = bmsFollowPostService.getOne(new LambdaQueryWrapper<BmsFollowPost>()
                    .eq(BmsFollowPost::getPostId, postId)
                    .eq(BmsFollowPost::getFollowerId, umsUser.getId()));
            if (!ObjectUtils.isEmpty(one)) {
                map.put("hasFollowPost", true);
            }
        }
        return ApiResult.success(map);
    }

    // 获取用户的关注话题列表
    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestHeader(value = USER_NAME) String userName,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        if (!ObjectUtils.isEmpty(umsUser)) {
            Page<PostVO> list = iBmsPostService.selectfollowPost(new Page<>(pageNo, pageSize),umsUser.getId());
            return ApiResult.success(list);
        }
        return ApiResult.success(null);
    }


}
