package com.lin.model.dto;

import lombok.Data;

@Data
public class StatusDTO {
    private static final long serialVersionUID = -5957433707110390852L;

    // 文章id
    private String topic_id;

    // 用户id
    private String user_id;

    // status
    private Integer status;
}
