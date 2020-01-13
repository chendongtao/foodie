package com.imooc.service;

import com.imooc.pojo.vo.ItemCommentCountVO;

public interface ItemCommentService {
    ItemCommentCountVO queryItemCommentCount(String itemId);
}
