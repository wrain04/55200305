package com.xyw.product.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyw.product.goods.entity.Comment;
import com.xyw.product.goods.mapper.CommentMapper;
import com.xyw.product.goods.service.CommentService;
import com.xyw.product.goods.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

	@Override
	public Map<String, Object> getCommentsByGoodsId(Page page, Long goodsId, Boolean isOrder, Boolean isOnlyImage) {
		HashMap<String, Object> ret = new HashMap<>();
		LambdaQueryWrapper<Comment> queryWrapper = Wrappers.<Comment>lambdaQuery().eq(Comment::getGoodsId, goodsId)
				.orderByAsc(isOrder ? Comment::getCreateTime : Comment::getCommentId);
		if (isOnlyImage) {
			queryWrapper.eq(Comment::getIsHaveImage, isOnlyImage);
		}
		Page selectPage = getBaseMapper().selectPage(page, queryWrapper);
		Object comments = selectPage.getRecords().stream().map(c -> BeanUtil.copyProperties(c, CommentVO.class))
				.collect(Collectors.toList());
		ret.put("comments", comments);
		ret.put("total", selectPage.getTotal());
		return ret;
	}

}