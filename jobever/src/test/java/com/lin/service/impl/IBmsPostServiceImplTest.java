package com.lin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.service.IBmsPostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IBmsPostServiceImplTest {
    @Autowired
    IBmsPostService iBmsPostService;

    @Test
    void getList() {
        iBmsPostService.getList(new Page<>(1, 10), "hot", null);
    }

    @Test
    void create() {
    }

    @Test
    void viewTopic() {
    }

    @Test
    void getRecommend() {
    }

    @Test
    void searchByKey() {
    }
}