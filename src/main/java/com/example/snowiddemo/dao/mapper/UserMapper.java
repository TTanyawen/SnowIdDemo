package com.example.snowiddemo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.snowiddemo.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
