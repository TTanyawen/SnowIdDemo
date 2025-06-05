package com.example.snowiddemo.tool;

public class SnowflakeIdGenerator {
    // 起始时间戳，可自定义
    private final long startTimeStamp = 1609459200000L; // 2021-01-01 00:00:00

    // 数据中心 ID 所占位数
    private final long dataCenterIdBits = 5L;
    // 机器 ID 所占位数
    private final long machineIdBits = 5L;
    // 序列号所占位数
    private final long sequenceBits = 12L;

    // 数据中心 ID 最大值
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    // 机器 ID 最大值
    private final long maxMachineId = -1L ^ (-1L << machineIdBits);
    // 序列号最大值
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    // 机器 ID 向左移位数
    private final long machineIdShift = sequenceBits;
    // 数据中心 ID 向左移位数
    private final long dataCenterIdShift = sequenceBits + machineIdBits;
    // 时间戳向左移位数
    private final long timestampLeftShift = sequenceBits + machineIdBits + dataCenterIdBits;

    // 数据中心 ID
    private final long dataCenterId;
    // 机器 ID
    private final long machineId;
    // 序列号
    private long sequence = 0L;
    // 上一次生成 ID 的时间戳
    private long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long dataCenterId, long machineId) {
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException("Data center ID can't be greater than " + maxDataCenterId + " or less than 0");
        }
        if (machineId > maxMachineId || machineId < 0) {
            throw new IllegalArgumentException("Machine ID can't be greater than " + maxMachineId + " or less than 0");
        }
        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();

        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for " + (lastTimestamp - currentTimestamp) + " milliseconds");
        }

        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 序列号溢出，等待下一毫秒
                currentTimestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，重置序列号
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        return ((currentTimestamp - startTimeStamp) << timestampLeftShift) |
                (dataCenterId << dataCenterIdShift) |
                (machineId << machineIdShift) |
                sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}