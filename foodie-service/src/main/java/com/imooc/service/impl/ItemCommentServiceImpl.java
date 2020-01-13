package com.imooc.service.impl;

import com.imooc.mapper.ItemsCommentsMapper;
import com.imooc.pojo.ItemsComments;
import com.imooc.pojo.vo.CommentLevel;
import com.imooc.pojo.vo.ItemCommentCountVO;
import com.imooc.service.ItemCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ItemCommentServiceImpl implements ItemCommentService {

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Override
    public ItemCommentCountVO queryItemCommentCount(String itemId) {
        ItemCommentCountVO itemCommentCountVO =new ItemCommentCountVO();
        Integer goodCounts = queryItemComentCountByLevel(itemId, CommentLevel.GOOD.value);
        Integer normalCounts = queryItemComentCountByLevel(itemId, CommentLevel.NORMAL.value);
        Integer badCounts = queryItemComentCountByLevel(itemId, CommentLevel.BAD.value);
        Integer totalCounts = goodCounts+normalCounts+badCounts;
        itemCommentCountVO.setGoodCounts(goodCounts);
        itemCommentCountVO.setNormalCounts(normalCounts);
        itemCommentCountVO.setBadCounts(badCounts);
        itemCommentCountVO.setTotalCounts(totalCounts);
        return itemCommentCountVO;
    }

    private Integer queryItemComentCountByLevel(String itemId,Integer level){
        ItemsComments itemsComments =new ItemsComments();
        itemsComments.setItemId(itemId);
        itemsComments.setCommentLevel(level);
         return itemsCommentsMapper.selectCount(itemsComments);
    }
}
