package com.lin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.model.entity.BmsTip;

public interface IBmsTipService extends IService<BmsTip> {
    BmsTip getRandomTip();
}
