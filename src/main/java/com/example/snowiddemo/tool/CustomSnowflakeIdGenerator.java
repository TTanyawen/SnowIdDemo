package com.example.snowiddemo.tool;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.stereotype.Component;

@Component
public class CustomSnowflakeIdGenerator implements IdentifierGenerator {
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    public CustomSnowflakeIdGenerator() {
        // 这里可以根据实际情况传入不同的数据中心 ID 和机器 ID
        this.snowflakeIdGenerator = new SnowflakeIdGenerator(1, 1);
    }

    @Override
    public Number nextId(Object entity) {
        return snowflakeIdGenerator.nextId();
    }
}
