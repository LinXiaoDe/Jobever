package com.lin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.mapper.BmsFollowMapper;
import com.lin.model.entity.BmsFollow;
import com.lin.model.entity.UmsUser;
import com.lin.model.vo.PostVO;
import com.lin.service.IBmsFollowService;
import org.springframework.stereotype.Service;


@Service
public class IBmsFollowServiceImpl extends ServiceImpl<BmsFollowMapper, BmsFollow> implements IBmsFollowService {
    @Override
    public Page<UmsUser> getFollowList(Page<UmsUser> page, String userId) {
        Page<UmsUser> iPage = this.baseMapper.getFollowList(page,userId);
        return iPage;
    }

    @Override
    public Page<UmsUser> getFansList(Page<UmsUser> page, String userId) {
        Page<UmsUser> iPage = this.baseMapper.getFansList(page,userId);
        return iPage;
    }
}
