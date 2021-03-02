package com.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lin.model.entity.BmsPost;
import com.lin.model.vo.PostVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BmsPostMapper extends BaseMapper<BmsPost> {
    /**
     * 分页查询首页话题列表
     * <p>
     *
     * @param page
     * @param tab
     * @return
     */
    Page<PostVO> selectListAndPage(@Param("page") Page<PostVO> page, @Param("tab") String tab,@Param("status") Integer status);


    // 查找关注话题
    Page<PostVO> selectfollowPost(@Param("page") Page<PostVO> page,@Param("userId") String userId);

    /**
     * 获取详情页推荐
     *
     * @param id
     * @return
     */
    List<BmsPost> selectRecommend(@Param("id") String id);
    /**
     * 全文检索
     *
     * @param page
     * @param keyword
     * @return
     */
    Page<PostVO> searchByKey(@Param("page") Page<PostVO> page, @Param("keyword") String keyword);
}
