package com.happy.item.service;

import com.happy.item.pojo.SpecGroup;
import com.happy.item.pojo.SpecParam;

import java.util.List;

/**
 * @author ：shenjuncai
 * @description：TODO
 * @date ：2021/8/31 14:07
 */
public interface SpecificationService {
    List<SpecGroup> queryGroupByCid(Long cid);

    List<SpecParam> queryParamByGid(Long cid, Long gid, Boolean searching);

    List<SpecGroup> querySpecsByCid(Long cid);
}
