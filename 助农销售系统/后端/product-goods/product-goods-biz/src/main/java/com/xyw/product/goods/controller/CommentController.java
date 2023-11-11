package com.xyw.product.goods.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.log.annotation.SysLog;
import com.xyw.product.common.security.annotation.Inner;
import com.xyw.product.goods.entity.Comment;
import com.xyw.product.goods.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/commentApi")
@Tag(name = "管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CommentController {

	private final CommentService commentService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param comment
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('goods_comment_get')")
	public R getCommentPage(Page page, Comment comment) {
		return R.ok(commentService.page(page, Wrappers.query(comment)));
	}

	/**
	 * 通过id查询
	 * @param commentId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{commentId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_comment_get')")
	public R getById(@PathVariable("commentId") Long commentId) {
		return R.ok(commentService.getById(commentId));
	}

	/**
	 * 新增
	 * @param comment
	 * @return R
	 */
	@Operation(summary = "新增", description = "新增")
	@SysLog("新增")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('goods_comment_add')")
	public R save(@RequestBody Comment comment) {
		return R.ok(commentService.save(comment));
	}

	/**
	 * 修改
	 * @param comment
	 * @return R
	 */
	@Operation(summary = "修改", description = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_comment_edit')")
	public R updateById(@RequestBody Comment comment) {
		return R.ok(commentService.updateById(comment));
	}

	/**
	 * 通过id删除
	 * @param commentId id
	 * @return R
	 */
	@Operation(summary = "通过id删除", description = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{commentId:\\d+}")
	@PreAuthorize("@pms.hasPermission('goods_comment_del')")
	public R removeById(@PathVariable Long commentId) {
		return R.ok(commentService.removeById(commentId));
	}

	@Operation(summary = "通过商品id获取评论", description = "通过商品id获取评论")
	@SysLog("通过商品id获取评论")
	@GetMapping("/getCommentByGoodsId/{goodsId:\\d+}")
	@Inner(false)
	public R getCommentByGoodsId(Page page, @PathVariable Long goodsId,
			@RequestParam(defaultValue = "false") Boolean isOrder,
			@RequestParam(defaultValue = "false") Boolean isOnlyImage) {
		return R.ok(commentService.getCommentsByGoodsId(page, goodsId, isOrder, isOnlyImage));
	}

}
