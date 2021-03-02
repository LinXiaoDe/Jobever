package com.lin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.model.vo.PostVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BmsPostMapperTest {
    @Autowired
    BmsPostMapper bmsPostMapper;

    @Test
    void selectListAndPage() {
        // 查询话题
        Page<PostVO> hot = bmsPostMapper.selectListAndPage(new Page<>(1, 10), "hot", null);

        System.out.println("============"+hot.countId());
    }

    @Test
    void selectRecommend() {
    }

    @Test
    void searchByKey() {
    }
}