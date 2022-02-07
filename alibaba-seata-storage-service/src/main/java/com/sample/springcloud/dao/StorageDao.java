package com.sample.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StorageDao {

    @Update("update `storage` set `count` = `count` - #{count} where `commodity_code` = #{commodityCode}")
    int deduct(@Param("commodityCode") String commodityCode, @Param("count") Integer count);

}
