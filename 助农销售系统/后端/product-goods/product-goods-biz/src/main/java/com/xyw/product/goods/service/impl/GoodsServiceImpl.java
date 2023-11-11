package com.xyw.product.goods.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyw.product.common.core.constant.ProductConstants;
import com.xyw.product.common.core.util.R;
import com.xyw.product.common.security.util.SecurityUtils;
import com.xyw.product.goods.dto.GoodsDTO;
import com.xyw.product.goods.entity.BusinessDetail;
import com.xyw.product.goods.entity.Comment;
import com.xyw.product.goods.entity.Good;
import com.xyw.product.goods.entity.Grade;
import com.xyw.product.goods.mapper.GoodsMapper;
import com.xyw.product.goods.service.BusinessDetailService;
import com.xyw.product.goods.service.CommentService;
import com.xyw.product.goods.service.GoodsService;
import com.xyw.product.goods.service.GradeService;
import com.xyw.product.goods.vo.CommentVO;
import com.xyw.product.goods.vo.GoodsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class GoodsServiceImpl extends BaseServiceImpl<GoodsMapper, Good> implements GoodsService {

	private final GradeService gradeService;

	private final CommentService commentService;

	private final BusinessDetailService businessDetailService;

	public static List<String> getList(String fileName) {
		File file = new File(fileName);
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s;
			while ((s = br.readLine()) != null) {
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] str = result.toString().split("\n");
		List<String> strings = new ArrayList<>(Arrays.asList(str));
		strings.remove(0);
		return strings;
	}

	// @Override
	// public void autoInsert(String filename,String id) {
	// List<String> list =
	// getList("F:\\study\\products\\product\\product-goods\\product-goods-biz\\src\\main\\resources\\"+filename);
	// Random rd = new Random();
	// List<Grade> gradeList =
	// gradeService.list(Wrappers.<Grade>lambdaQuery().eq(Grade::getCategoryId, id));
	// gradeList.forEach(g -> {
	// List<Good> goodList = list(Wrappers.<Good>lambdaQuery().eq(Good::getGradeId,
	// g.getGradeId()).select(Good::getGoodsId, Good::getGoodsImage));
	// for (Good good : goodList) {
	// JSONArray array = JSONUtil.parseArray(good.getGoodsImage());
	// JSONArray newArray = JSONUtil.createArray();
	// for (int i = 0; i < array.size(); i++) {
	// newArray.add(list.get(rd.nextInt(list.size())));
	// }
	// good.setGoodsImage(JSONUtil.toJsonStr(newArray));
	// updateById(good);
	// List<TimeChartVO> chartVOS = timeChartService.getByGoodId("" + good.getGoodsId());
	// chartVOS.stream().forEach(c -> {
	// TimeChart timeChart = new TimeChart();
	// timeChart.setChartId(c.getChartId());
	// JSONArray jsonArray = JSONUtil.createArray();
	// for (String ignored : c.getImagesJson()) {
	// jsonArray.add(list.get(rd.nextInt(list.size())));
	// }
	// timeChart.setImages(JSONUtil.toJsonStr(jsonArray));
	// timeChartService.updateById(timeChart);
	// });
	// }
	// });
	// }

	@Override
	public Map<String, Object> getGoodsByBusinessId(Page page, Long businessId) {
		Page<Good> selectPage = getBaseMapper().selectPage(page,
				Wrappers.<Good>query().lambda().eq(Good::getBusinessId, businessId));

		return new HashMap<String, Object>() {
			{
				put("data", selectPage.getRecords().stream().map(bean -> {
					GoodsVO vo = new GoodsVO();
					BeanUtil.copyProperties(bean, vo);
					vo.setPrice(gradeService.getOne(Wrappers.<Grade>lambdaQuery().eq(Grade::getGradeId, vo.getGradeId())
							.select(Grade::getPrice)).getPrice());
					vo.setImages(JSONUtil.toList(bean.getGoodsImage(), String.class));
					return vo;
				}).collect(Collectors.toList()));
				put("total", selectPage.getTotal());
			}
		};
	}

	/**
	 * @param page:
	 * @param gradeId:
	 * @return Map<String, Object>
	 * @author 徐志炫
	 * @description 根据品级id获取商品
	 * @date 2023/1/18 10:59
	 */
	@Override
	public Map<String, Object> getGoodsInfoByGradeId(Page page, String gradeId) {
		Page<Good> selectPage = getBaseMapper().selectPage(page,
				Wrappers.<Good>query().lambda().eq(Good::getGradeId, gradeId).between(Good::getStatus,
						ProductConstants.IN_SALES, ProductConstants.OUT_OF_STOCK));
		return new HashMap<String, Object>() {
			{
				put("data", selectPage.getRecords().stream().map(bean -> {
					GoodsVO vo = new GoodsVO();
					BeanUtil.copyProperties(bean, vo);
					vo.setImages(JSONUtil.toList(bean.getGoodsImage(), String.class));
					vo.setBusinessAddress(businessDetailService.getOne(Wrappers.<BusinessDetail>lambdaQuery().eq(BusinessDetail::getBusinessId, vo.getBusinessId()).select(BusinessDetail::getAddress)).getAddress());
					return vo;
				}).collect(Collectors.toList()));
				put("total", selectPage.getTotal());
			}
		};
	}

	/**
	 * @param n:
	 * @return List<GoodsVO>
	 * @author 徐志炫
	 * @description 根据传入的n随机返回n条商品数据
	 * @date 2023/1/15 12:18
	 */
	@Override
	public List<GoodsVO> getGoodsInfoByRandom(Long n) {
		return getBaseMapper().selectGoodsInfoByRandom(n).stream().map(bean -> {
			GoodsVO vo = new GoodsVO();
			BeanUtil.copyProperties(bean, vo);
			vo.setImages(new ArrayList<String>() {
				{
					add(JSONUtil.toList(bean.getGoodsImage(), String.class).stream().findFirst().get());
				}
			});
			return vo;
		}).collect(Collectors.toList());
	}

	@Override
	public Map<String, Object> getByBusinessId(Page page, Long businessId) {
		HashMap<String, Object> map = new HashMap<>();
		Page<Good> selectPage = getBaseMapper().selectPage(page,
				Wrappers.<Good>lambdaQuery().eq(Good::getBusinessId, businessId));
		map.put("total", selectPage.getTotal());
		map.put("list", selectPage.getRecords().stream().map(bean -> {
			GoodsVO vo = new GoodsVO();
			BeanUtil.copyProperties(bean, vo);
			vo.setPrice(gradeService.getOne(
							Wrappers.<Grade>lambdaQuery().eq(Grade::getGradeId, vo.getGradeId()).select(Grade::getPrice))
					.getPrice());
			vo.setImages(new ArrayList<String>() {
				{
					add(JSONUtil.toList(bean.getGoodsImage(), String.class).stream().findFirst().get());
				}
			});
			return vo;
		}).collect(Collectors.toList()));
		return map;
	}

	/**
	 * @param dto:
	 * @return boolean
	 * @author 徐志炫
	 * @description 注册商品
	 * @date 2022/10/28 16:20
	 */
	@Override
	public R registerGoods(GoodsDTO dto) {
		if (dto.getImageList() == null || dto.getImageList().isEmpty()) {
			return R.failed("图片不能为空");
		}
		Good good = BeanUtil.copyProperties(dto, Good.class, "goodsId");
		good.setGoodsImage(JSONUtil.toJsonStr(dto.getImageList()));
		good.setBusinessId(SecurityUtils.getUser().getId());
		good.setStatus(ProductConstants.IN_PROCESS);
		return getBaseMapper().insert(good) > 0 ? R.ok("注册成功") : R.failed("注册失败");
	}

	/**
	 * @param dto:
	 * @return boolean
	 * @author 徐志炫
	 * @description 更新商品信息
	 * @date 2023/1/28 19:17
	 */
	@Override
	public R updateGoods(GoodsDTO dto) {
		if (!getBaseMapper().exists(Wrappers.<Good>lambdaQuery().eq(Good::getGoodsId, dto.getGoodsId())
				.between(Good::getStatus, ProductConstants.IN_SALES, ProductConstants.OUT_OF_STOCK))) {
			R.failed("数据不存在");
		}
		Good good = BeanUtil.copyProperties(dto, Good.class);
		good.setGoodsImage(JSONUtil.toJsonStr(dto.getImageList()));
		return getBaseMapper().updateById(good) > 0 ? R.ok() : R.failed();
	}

	@Override
	public GoodsVO getByIdToVO(String goodId) {
		Good good = getOne(Wrappers.<Good>lambdaQuery().eq(Good::getGoodsId, goodId).between(Good::getStatus,
				ProductConstants.IN_SALES, ProductConstants.OUT_OF_STOCK));
		return BeanUtil.copyProperties(good, GoodsVO.class)
				.setPrice(gradeService.getOne(
								Wrappers.<Grade>lambdaQuery().eq(Grade::getGradeId, good.getGradeId()).select(Grade::getPrice))
						.getPrice())
				.setImages(JSONUtil.toList(good.getGoodsImage(), String.class))
				.setCommentList(commentService
						.list(Wrappers.<Comment>lambdaQuery().eq(Comment::getGoodsId, goodId).last("limit 2")).stream()
						.map(c -> BeanUtil.copyProperties(c, CommentVO.class)).collect(Collectors.toList()));
	}

	@Override
	public List<GoodsVO> getByIdListToVO(List<String> goodIdList) {
		return list(
				Wrappers.<Good>lambdaQuery().in(Good::getGoodsId,
						goodIdList).between(Good::getStatus, ProductConstants.IN_SALES,
						ProductConstants.OUT_OF_STOCK))
				.stream().map(
						g -> BeanUtil
								.copyProperties(g,
										GoodsVO.class)
								.setPrice(gradeService.getOne(Wrappers.<Grade>lambdaQuery()
										.eq(Grade::getGradeId, g.getGradeId())
										.select(Grade::getPrice)).getPrice())
								.setImages(JSONUtil.toList(g.getGoodsImage(), String.class)))
				.collect(Collectors.toList());
	}

	@Override
	public boolean isExist(String goodId) {
		return getBaseMapper().exists(Wrappers.<Good>lambdaQuery().eq(Good::getGoodsId, goodId));
	}

}