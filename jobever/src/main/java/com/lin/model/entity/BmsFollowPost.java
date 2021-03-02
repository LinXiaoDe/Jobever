package com.lin.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("bms_follow_post")
public class BmsFollowPost implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    @TableField("post_id")
    private String postId;

    /**
     * 关注人id
     */
    @TableField("follower_id")
    private String followerId;

    public BmsFollowPost() {
    }

}
