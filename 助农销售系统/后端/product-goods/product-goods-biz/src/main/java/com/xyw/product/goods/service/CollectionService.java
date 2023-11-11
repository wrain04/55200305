package com.xyw.product.goods.service;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyw.product.common.core.util.R;
import com.xyw.product.goods.entity.Collection;

import java.util.ArrayList;
import java.util.List;

public interface CollectionService extends IService<Collection> {

	JSONArray getCollectByUserIdAndMark(Long userId, Byte mark);

	R collectWithMark(Long userId, String dataId, Byte mark);

	void collectHistory(Long userId, String goodsId);

	R removeWithMark(Long userId, List<String> dataIds, Byte mark);

	Boolean isCollect(Long userId, String dataId, Byte mark);

	List<Integer> getSimpleInfo(Long userId);

}
