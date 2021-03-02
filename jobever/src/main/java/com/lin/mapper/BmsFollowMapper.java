package com.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.model.entity.BmsFollow;
import com.lin.model.entity.UmsUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BmsFollowMapper extends BaseMapper<BmsFollow> {
    Page<UmsUser> getFollowList(@Param("page") Page<UmsUser> page, @Param("userId") String userId);

    Page<UmsUser> getFansList(@Param("page") Page<UmsUser> page, @Param("userId") String userId);
}
