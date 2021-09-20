package com.happy.common.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author ：shenjuncai
 * @description：通用mapper
 * @date ：2021/9/1 16:23
 */
@RegisterMapper
public interface BaseMapper<T> extends Mapper<T> , IdListMapper<T,Long>, InsertListMapper<T> {
}
