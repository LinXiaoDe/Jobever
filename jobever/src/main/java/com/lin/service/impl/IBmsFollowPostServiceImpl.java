package com.lin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.mapper.BmsFollowPostMapper;
import com.lin.model.entity.BmsFollowPost;
import com.lin.service.IBmsFollowPostService;
import org.springframework.stereotype.Service;


@Service
public class IBmsFollowPostServiceImpl extends ServiceImpl<BmsFollowPostMapper, BmsFollowPost> implements IBmsFollowPostService {
}
