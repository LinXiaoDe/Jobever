package com.lin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.common.api.ApiResult;
import com.lin.common.exception.ApiAsserts;
import com.lin.model.dto.CreateTopicDTO;
import com.lin.model.dto.StatusDTO;
import com.lin.model.entity.BmsPost;
import com.lin.model.entity.UmsUser;
import com.lin.model.vo.PostVO;
import com.lin.service.IBmsPostService;
import com.lin.service.IUmsUserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lin.jwt.JwtUtil.USER_NAME;


@RestController
@RequestMapping("/post")
public class BmsPostController extends BaseController {

    @Resource
    private IBmsPostService iBmsPostService;
    @Resource
    private IUmsUserService umsUserService;

    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "status", defaultValue = "1") Integer status,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = iBmsPostService.getList(new Page<>(pageNo, pageSize), tab,status);
        return ApiResult.success(list);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<BmsPost> create(@RequestHeader(value = USER_NAME) String userName
            , @RequestBody CreateTopicDTO dto) {
        UmsUser user = umsUserService.getUserByUsername(userName);
        BmsPost topic = iBmsPostService.create(dto, user);
        return ApiResult.success(topic);
    }

    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        Map<String, Object> map = iBmsPostService.viewTopic(id);
        return ApiResult.success(map);
    }

    @GetMapping("/recommend")
    public ApiResult<List<BmsPost>> getRecommend(@RequestParam("topicId") String id) {
        List<BmsPost> topics = iBmsPostService.getRecommend(id);
        return ApiResult.success(topics);
    }

    @PostMapping("/update")
    public ApiResult<BmsPost> update(@RequestHeader(value = USER_NAME) String userName, @Valid @RequestBody BmsPost post) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        Assert.isTrue(umsUser.getId().equals(post.getUserId()), "只能修改自己的话题哦～");
        post.setModifyTime(new Date());
        post.setContent(EmojiParser.parseToAliases(post.getContent()));
        iBmsPostService.updateById(post);
        return ApiResult.success(post);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(@RequestHeader(value = USER_NAME) String userName, @PathVariable("id") String id) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        BmsPost byId = iBmsPostService.getById(id);
        Assert.notNull(byId, "来晚一步，话题已不存在");
        Assert.isTrue(byId.getUserId().equals(umsUser.getId()), "只能删除自己的话题哦～");
        iBmsPostService.removeById(id);
        return ApiResult.success(null,"删除成功");
    }


    //管理员进行文章状态修改
    @PostMapping("/admin/status")
    public ApiResult<BmsPost> status(@RequestHeader(value = USER_NAME) String userName, @Valid @RequestBody StatusDTO statusDTO) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        Assert.isTrue(umsUser.getId().equals(statusDTO.getUser_id()), "只能修改自己的话题状态哦～");

        BmsPost post = iBmsPostService.getById(statusDTO.getTopic_id());
        Assert.notNull(post, "来晚一步，话题已不存在");
        post.setStatus(statusDTO.getStatus());
        post.setModifyTime(new Date());

        iBmsPostService.updateById(post);
        return ApiResult.success(post);
    }

    //普通用户进行修改,只能开启或关闭
    @PostMapping("/status")
    public ApiResult<Map<String, Object>> statusOpen(@RequestHeader(value = USER_NAME) String userName,
                                                     @Valid @RequestBody StatusDTO statusDTO) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        if(!umsUser.getId().equals(statusDTO.getUser_id()))
            ApiAsserts.fail("只能修改自己的话题状态哦～😃");
        BmsPost post = iBmsPostService.getById(statusDTO.getTopic_id());
        Assert.notNull(post, "来晚一步，话题已不存在");
        if(statusDTO.getStatus() == 1){
            if(post.getStatus()==1)
                ApiAsserts.fail("话题已开启！");
            else if(post.getStatus()==2)
                post.setStatus(1);
        }
        else if(statusDTO.getStatus() == 2){
            if(post.getStatus()==2)
                ApiAsserts.fail("话题已关闭！");
            else if(post.getStatus()==1)
                post.setStatus(2);
        }
        post.setModifyTime(new Date());
        iBmsPostService.updateById(post);
        Map<String, Object> map = new HashMap<>(16);
        map.put("status",post.getStatus());
        return ApiResult.success(map);
    }
}
