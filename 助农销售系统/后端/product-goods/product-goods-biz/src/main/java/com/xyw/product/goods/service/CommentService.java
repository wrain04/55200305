package com.xyw.product.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.goods.entity.Comment;

import java.util.Map;

public interface CommentService extends IService<Comment> {

	Map<String, Object> getCommentsByGoodsId(Page page, Long goodsId, Boolean isOrder, Boolean isOnlyImage);

}
