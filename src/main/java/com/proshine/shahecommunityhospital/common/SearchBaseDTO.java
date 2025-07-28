package com.proshine.shahecommunityhospital.common;

import lombok.Data;

@Data
public class SearchBaseDTO {

    private String customerId;

    /**
     * 当前第几页数据
     * @mock 1
     */
    private Integer pageNumber = 1;

    /**
     * 每页显示条数
     * @mock 10
     */
    private Integer pageSize = 10;

}
