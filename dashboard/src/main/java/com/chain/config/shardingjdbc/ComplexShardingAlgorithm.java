package com.chain.config.shardingjdbc;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Range;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;

/**
 * 复合分片算法
 */
public class ComplexShardingAlgorithm implements ComplexKeysShardingAlgorithm {
    /**
     * 精确匹配查询 + 范围匹配查询
     *
     * @param availableTargetNames     分片相关信息["db_trading_202201","db_trading_202202","db_trading_202203"]
     * @param complexKeysShardingValue 数据库中所有的真实表{"columnNameAndRangeValuesMap":{"category":[a..b]],"commission_time":[2023-07-07 17:02:03.0..2023-07-08 00:00:00.0]},"columnNameAndShardingValuesMap":{},"logicTableName":"db_trading"}
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection availableTargetNames, ComplexKeysShardingValue complexKeysShardingValue) {
        System.out.println(JSON.toJSONString(availableTargetNames));
        System.out.println(JSON.toJSONString(complexKeysShardingValue));
        // 获取逻辑表名称
        String logicTableName = complexKeysShardingValue.getLogicTableName();
        List<String> tableNameList = new ArrayList<>();

        //1. 处理精确查找 = in
        Set<Map.Entry<String, List>> columnNameAndShardingValuesSet = complexKeysShardingValue.getColumnNameAndShardingValuesMap().entrySet();
        if (columnNameAndShardingValuesSet.size() > 0) {
            Boolean hasCommissionTime = columnNameAndShardingValuesSet.stream().anyMatch(i -> "commission_time".equals(i.getKey()));
            Boolean hasCategory = columnNameAndShardingValuesSet.stream().anyMatch(i -> "category".equals(i.getKey()));

            //1.1 有commission_time 无category
            if (hasCommissionTime && !hasCategory) {
                Map.Entry<String, List> commissionTimeMap = columnNameAndShardingValuesSet.stream().filter(i -> "commission_time".equals(i.getKey())).findFirst().get();
                List<Date> shardingValues = commissionTimeMap.getValue();
                for (Date value : shardingValues) {
                    for (Object availableTargetName : availableTargetNames) {
                        Date date = DateUtil.parse(String.valueOf(availableTargetName).split("_")[2], "yyyyMM");
                        if (DateUtil.year(date) == DateUtil.year(value)) {
                            tableNameList.add(String.valueOf(availableTargetName));
                        }
                    }
                }
            }
            //1.2 无commission_time 有category
            else if (!hasCommissionTime && hasCategory) {
                Map.Entry<String, List> categoryMap = columnNameAndShardingValuesSet.stream().filter(i -> "category".equals(i.getKey())).findFirst().get();
                List<String> shardingValues = categoryMap.getValue();
                for (String value : shardingValues) {
                    for (Object availableTargetName : availableTargetNames) {
                        Date date = DateUtil.parse(String.valueOf(availableTargetName).split("_")[2], "yyyyMM");
                        if (DateUtil.month(date) == Math.abs(value.hashCode()) % 12) {
                            tableNameList.add(String.valueOf(availableTargetName));
                        }
                    }
                }
            }
            //1.3 有commission_time 有category
            else if (hasCommissionTime && hasCategory) {
                Map.Entry<String, List> commissionTimeMap = columnNameAndShardingValuesSet.stream().filter(i -> "commission_time".equals(i.getKey())).findFirst().get();
                List<Date> shardingYearValues = commissionTimeMap.getValue();
                Map.Entry<String, List> categoryMap = columnNameAndShardingValuesSet.stream().filter(i -> "category".equals(i.getKey())).findFirst().get();
                List<String> shardingMonthValues = categoryMap.getValue();
                for (Date yearValue : shardingYearValues) {
                    for (String monthValue : shardingMonthValues) {
                        Integer year = DateUtil.year(yearValue);
                        Integer month = Math.abs(monthValue.hashCode()) % 12 + 1;
                        tableNameList.add(logicTableName + "_" + year + String.format("%02d", month));
                    }
                }
            }
        }

        //2. 处理范围 > < >= <= between and
        Set<Map.Entry<String, Range>> columnNameAndRangeValuesMap = complexKeysShardingValue.getColumnNameAndRangeValuesMap().entrySet();
        if (columnNameAndRangeValuesMap.size() > 0) {
            Boolean hasCommissionTime = columnNameAndRangeValuesMap.stream().anyMatch(i -> "commission_time".equals(i.getKey()));
            if (hasCommissionTime) {
                Map.Entry<String, Range> commissionTimeMap = columnNameAndRangeValuesMap.stream().filter(i -> "commission_time".equals(i.getKey())).findFirst().get();
                Range shardingValues = commissionTimeMap.getValue();
                Date lower = DateUtil.beginOfMonth((Date) shardingValues.lowerEndpoint());
                Date upper = DateUtil.beginOfMonth((Date) shardingValues.upperEndpoint());

                if (ObjectUtil.isEmpty(lower) || ObjectUtil.isEmpty(upper)) {
                    //循环所有定义的真实表
                    for (Object availableTargetName : availableTargetNames) {
                        String tbName = String.valueOf(availableTargetName);
                        String dateStr = tbName.split("_")[2];
                        Date date = DateUtil.parse(dateStr, "yyyyMM");
                        //当只有最小值时，最小值时间应小于逻辑表时间，当只有最大值时，逻辑表时间应小于最大值时间
                        if ((ObjectUtil.isNotEmpty(lower) ? lower.compareTo(date) : date.compareTo(upper)) <= 0) {
                            tableNameList.add(logicTableName + "_" + dateStr);
                        }
                    }
                }
                //当使用Between and 时
                else {
                    //便利目标中间所有时间段
                    for (; lower.compareTo(upper) <= 0; lower = DateUtils.addMonths(lower, 1)) {
                        String tableName = logicTableName + "_" + DateUtil.format(lower, "yyyyMM");
                        //判断是否存在定义的真实表
                        if (availableTargetNames.contains(tableName)) {
                            tableNameList.add(tableName);
                        }
                    }
                }
            }
        }
        return tableNameList;
    }
}
