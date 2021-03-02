package com.lin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.model.entity.BmsFollow;
import com.lin.model.entity.UmsUser;
import com.lin.model.vo.PostVO;
import org.apache.ibatis.annotations.Param;


public interface IBmsFollowService extends IService<BmsFollow> {
    //分页获取用户的关注列表
    Page<UmsUser> getFollowList(@Param("page") Page<UmsUser> page, @Param("userId") String userId);

    Page<UmsUser> getFansList(@Param("page") Page<UmsUser> page, @Param("userId") String userId);
}
