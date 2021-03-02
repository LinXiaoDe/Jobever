package com.lin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.mapper.BmsBillboardMapper;
import com.lin.model.entity.BmsBillboard;
import com.lin.service.IBmsBillboardService;
import org.springframework.stereotype.Service;

@Service
public class IBmsBillboardServiceImpl extends ServiceImpl<BmsBillboardMapper
        , BmsBillboard> implements IBmsBillboardService {

}
