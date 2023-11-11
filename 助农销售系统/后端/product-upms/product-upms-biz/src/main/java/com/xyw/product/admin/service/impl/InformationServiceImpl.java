package com.xyw.product.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyw.product.admin.api.entity.Information;
import com.xyw.product.admin.mapper.InformationMapper;
import com.xyw.product.admin.service.InformationService;
import org.springframework.stereotype.Service;

@Service
public class InformationServiceImpl extends ServiceImpl<InformationMapper, Information> implements InformationService {

}
