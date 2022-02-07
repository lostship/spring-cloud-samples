package com.sample.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AccountDao {

    @Update("update `account` set `money` = `money` - #{money} where `user_id` = #{userId}")
    int debit(@Param("userId") Long userId, @Param("money") Integer money);

}
