package com.lin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.model.dto.CommentDTO;
import com.lin.model.entity.BmsComment;
import com.lin.model.entity.UmsUser;
import com.lin.model.vo.CommentVO;

import java.util.List;


public interface IBmsCommentService extends IService<BmsComment> {
    /**
     *
     *
     * @param topicid
     * @return {@link BmsComment}
     */
    List<CommentVO> getCommentsByTopicID(String topicid);

    BmsComment create(CommentDTO dto, UmsUser principal);
}
