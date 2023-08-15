//package com.chain.config.shardingjdbc;
//
//import cn.hutool.core.date.DateUtil;
//import cn.hutool.core.util.ObjectUtil;
//import org.apache.commons.lang.time.DateUtils;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
//import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
//import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//
///**
// * 标准分片策略
// */
//public class StandardShardingAlgorithm implements PreciseShardingAlgorithm<Date>, RangeShardingAlgorithm<Date> {
//    /**
//     * 精确匹配算法，可以实现对 `=`以及`in`的查询（必选）
//     *
//     * @param tbNames       规则中定义的真实表
//     * @param shardingValue 分片字段和值
//     * @return 返回匹配的数据表名
//     */
//    @Override
//    public String doSharding(Collection<String> tbNames, PreciseShardingValue<Date> shardingValue) {
//        String index = DateUtil.format(shardingValue.getValue(), "yyyyMM");
//        for (String tableName : tbNames) {
//            // 匹配满足当前分片规则的表名称
//            if (tableName.endsWith(index)) {
//                return tableName;
//            }
//        }
//        throw new RuntimeException("数据表不存在");
//    }
//
//    /**
//     * 范围匹配算法，可以实现对 > < >= <= between and 的查询（非必选，不配置则全库路由处理）
//     */
//    @Override
//    public Collection<String> doSharding(Collection<String> tbNames, RangeShardingValue<Date> rangeShardingValue) {
//        // 获取逻辑表名称
//        String logicTableName = rangeShardingValue.getLogicTableName();
//
//        Date lower = rangeShardingValue.getValueRange().hasLowerBound() ? DateUtil.beginOfMonth(rangeShardingValue.getValueRange().lowerEndpoint()) : null;
//        Date upper = rangeShardingValue.getValueRange().hasUpperBound() ? DateUtil.beginOfMonth(rangeShardingValue.getValueRange().upperEndpoint()) : null;
//
//        List<String> tableNameList = new ArrayList<>();
//        //当只有最大值或者只有最小值时 >= || <= || > || <
//        if (ObjectUtil.isEmpty(lower) || ObjectUtil.isEmpty(upper)) {
//            //循环所有定义的真实表
//            for (String tbName : tbNames) {
//                String dateStr = tbName.split("_")[2];
//                Date date = DateUtil.parse(dateStr, "yyyyMM");
//                //当只有最小值时，最小值时间应小于逻辑表时间，当只有最大值时，逻辑表时间应小于最大值时间
//                if ((ObjectUtil.isNotEmpty(lower) ? lower.compareTo(date) : date.compareTo(upper)) <= 0) {
//                    tableNameList.add(logicTableName + "_" + dateStr);
//                }
//            }
//        }
//        //当使用Between and 时
//        else {
//            //便利目标中间所有时间段
//            for (; lower.compareTo(upper) <= 0; lower = DateUtils.addMonths(lower, 1)) {
//                String tableName = logicTableName + "_" + DateUtil.format(lower, "yyyyMM");
//                //判断是否存在定义的真实表
//                if (tbNames.contains(tableName)) {
//                    tableNameList.add(tableName);
//                }
//            }
//        }
//        return tableNameList;
//    }
//}
