package com.happy.item.service.impl;

import com.happy.common.enums.ExceptionEnum;
import com.happy.common.exception.CommonException;
import com.happy.item.mapper.SpecGroupMapper;
import com.happy.item.mapper.SpecParamMapper;
import com.happy.item.pojo.SpecGroup;
import com.happy.item.pojo.SpecParam;
import com.happy.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/8/31 14:24
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    @Override
    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> groupList = specGroupMapper.select(specGroup);
        if(CollectionUtils.isEmpty(groupList)){
            throw new CommonException(ExceptionEnum.NOT_FOUND);
        }

        return groupList;
    }

    @Override
    public List<SpecParam> queryParamByGid(Long cid, Long gid, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        List<SpecParam> list = specParamMapper.select(specParam);
        if(CollectionUtils.isEmpty(list)){
            throw new CommonException(ExceptionEnum.NOT_FOUND);
        }

        return list;
    }

    @Override
    public List<SpecGroup> querySpecsByCid(Long cid) {
        //查询所有的规格参数组
        List<SpecGroup> specGroups = this.queryGroupByCid(cid);
        specGroups.forEach(specGroup -> {
            //查询该规格参组下的所有规格参数
            List<SpecParam> specParams = this.queryParamByGid(specGroup.getId(), null, null);
            //封装到规格参数组中
            specGroup.setParams(specParams);
        });
        return specGroups;
    }
}
