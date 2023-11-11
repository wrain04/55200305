package com.xyw.product.goods.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyw.product.admin.api.feign.RemoteUserService;
import com.xyw.product.admin.api.vo.UserVO;
import com.xyw.product.common.core.util.R;
import com.xyw.product.goods.entity.Collection;
import com.xyw.product.goods.mapper.CollectionMapper;
import com.xyw.product.goods.service.BusinessDetailService;
import com.xyw.product.goods.service.CollectionService;
import com.xyw.product.goods.service.GoodsService;
import com.xyw.product.goods.vo.GoodsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

	private final GoodsService goodsService;

	private final RemoteUserService remoteUserService;

	private final BusinessDetailService businessDetailService;

	private void build(Long userId) {
		if (baseMapper.selectCount(Wrappers.<Collection>lambdaQuery().eq(Collection::getUserId, userId)) == 3) {
			return;
		}
		for (int i = 0; i < 3; i++) {
			if (!baseMapper.exists(
					Wrappers.<Collection>lambdaQuery().eq(Collection::getUserId, userId).eq(Collection::getMark, i))) {
				baseMapper.insert(new Collection().setUserId(userId).setInfo("[]").setMark((byte) i));
			}
		}
	}

	@Override
	public JSONArray getCollectByUserIdAndMark(Long userId, Byte mark) {
		return JSONUtil
				.parseArray(baseMapper.selectOne(Wrappers.<Collection>query().lambda().eq(Collection::getUserId, userId)
						.eq(Collection::getMark, mark).select(Collection::getInfo)).getInfo());
	}

	private boolean goodExist(String dataId) {
		return goodsService.isExist(dataId);
	}

	@Override
	public R collectWithMark(Long userId, String dataId, Byte mark) {
		switch (mark) {
			// 收藏商品
			case 0: {
				if (!goodExist(dataId)) {
					return R.failed("商品不存在");
				}
				Collection collection = baseMapper
						.selectOne(Wrappers.<Collection>query().lambda().eq(Collection::getUserId, userId)
								.eq(Collection::getMark, 0).select(Collection::getCollectionId, Collection::getInfo));
				String info = collection.getInfo();
				if (info.contains(dataId)) {
					return R.failed("该商品已被收藏");
				}
				JSONArray array = new JSONArray();
				GoodsVO goodsVO = goodsService.getByIdToVO(dataId);
				array.add(0, goodsVO.getGoodsId());
				array.add(1, goodsVO.getImages().get(0));
				array.add(2, goodsVO.getGoodsName());
				JSONArray jsonArray = JSONUtil.parseArray(info);
				jsonArray.add(array);
				collection.setInfo(JSONUtil.toJsonStr(jsonArray));
				return updateById(collection) ? R.ok() : R.failed("数据有误");
			}
			// 收藏店铺
			case 1: {
				if (!remoteUserService.isBusiness(Long.parseLong(dataId)).getData()) {
					return R.failed("该店铺不存在");
				}
				Collection collection = baseMapper
						.selectOne(Wrappers.<Collection>query().lambda().eq(Collection::getUserId, userId)
								.eq(Collection::getMark, 1).select(Collection::getCollectionId, Collection::getInfo));
				String info = collection.getInfo();
				if (info.contains(dataId)) {
					return R.failed("该店铺已被收藏");
				}
				JSONArray array = new JSONArray();
				UserVO userVO = remoteUserService.user(Long.parseLong(dataId)).getData();
				Map<String, Object> map = businessDetailService.getBusinessDetail(Long.parseLong(dataId));
				array.add(0, userVO.getUserId());
				array.add(1, userVO.getAvatar());
				array.add(2, userVO.getUsername());
				array.add(3, map.get("businessType"));
				array.add(4, map.get("mainProducts"));
				JSONArray jsonArray = JSONUtil.parseArray(info);
				jsonArray.add(array);
				collection.setInfo(JSONUtil.toJsonStr(jsonArray));
				return updateById(collection) ? R.ok() : R.failed("数据有误");
			}
			default: {
				return R.failed("数据异常");
			}
		}
	}

	@Override
	public void collectHistory(Long userId, String goodsId) {
		if (!goodExist(goodsId)) {
			return;
		}
		Collection collection = baseMapper
				.selectOne(Wrappers.<Collection>query().lambda().eq(Collection::getUserId, userId)
						.eq(Collection::getMark, 2).select(Collection::getCollectionId, Collection::getInfo));
		if (collection == null) {
			return;
		}
		String info = collection.getInfo();
		if (info.contains(goodsId)) {
			JSONArray jsonArray = JSONUtil.parseArray(info);
			for (int j = 0; j < jsonArray.size(); j++) {
				if (jsonArray.getJSONArray(j).getStr(0).contains(goodsId)) {
					JSONArray array = jsonArray.getJSONArray(j);
					jsonArray.remove(j);
					jsonArray.add(array);
					break;
				}
			}
			collection.setInfo(JSONUtil.toJsonStr(jsonArray));
			updateById(collection);
			return;
		}
		JSONArray array = new JSONArray();
		GoodsVO goodsVO = goodsService.getByIdToVO(goodsId);
		array.add(0, goodsVO.getGoodsId());
		array.add(1, goodsVO.getImages().get(0));
		array.add(2, goodsVO.getGoodsName());
		JSONArray jsonArray = JSONUtil.parseArray(info);
		jsonArray.add(array);
		collection.setInfo(JSONUtil.toJsonStr(jsonArray));
		updateById(collection);
	}

	@Override
	public R removeWithMark(Long userId, List<String> dataIds, Byte mark) {
		switch (mark) {
			// 商品
			case 0:
			case 1:
			case 2: {
				Collection collection = baseMapper.selectOne(Wrappers.<Collection>query().lambda()
						.eq(Collection::getUserId, userId).eq(Collection::getMark, mark)
						.select(Collection::getCollectionId, Collection::getInfo));
				JSONArray jsonArray = JSONUtil.parseArray(collection.getInfo());
				for (String dataId : dataIds) {
					for (int j = 0; j < jsonArray.size(); j++) {
						if (jsonArray.getJSONArray(j).getStr(0).contains(dataId)) {
							jsonArray.remove(j);
							break;
						}
					}
				}
				collection.setInfo(JSONUtil.toJsonStr(jsonArray));
				return updateById(collection) ? R.ok() : R.failed("数据有误");
			}
			default: {
				return R.failed("数据异常");
			}
		}
	}

	@Override
	public Boolean isCollect(Long userId, String dataId, Byte mark) {
		build(userId);
		return baseMapper
				.selectOne(Wrappers.<Collection>query().lambda().eq(Collection::getUserId, userId)
						.eq(Collection::getMark, mark).select(Collection::getCollectionId, Collection::getInfo))
				.getInfo().contains(dataId);
	}

	@Override
	public List<Integer> getSimpleInfo(Long userId) {
		build(userId);
		List<Collection> list = baseMapper.selectList(
				Wrappers.<Collection>query().lambda().eq(Collection::getUserId, userId).orderByDesc(Collection::getMark)
						.select(Collection::getCollectionId, Collection::getMark, Collection::getInfo));
		return list.stream().map(c -> JSONUtil.parseArray(c.getInfo()).size()).collect(Collectors.toList());
	}

}