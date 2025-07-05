package cn.wenzhuo4657.LuckySphere.domain.activity.service;

import cn.wenzhuo4657.LuckySphere.domain.activity.model.valobj.ActivitySkuStockKeyVO;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/20
 * @description: 活动库存接口-sku层次管理活动抽奖次数
 */
public interface IRaffleActivitySkuStockService {
    /**
     * 获取活动sku库存消耗队列头部
     *
     * @return 奖品库存Key信息
     * @throws InterruptedException 异常
     */
    ActivitySkuStockKeyVO takeQueueValue() throws InterruptedException;

    /**
     * 清空队列
     */
    void clearQueueValue();

    /**
     * 延迟队列 + 任务趋势更新活动sku库存
     *
     * @param sku 活动商品
     */
    void updateActivitySkuStock(Long sku);

    /**
     * 缓存库存以消耗完毕，清空数据库库存
     *
     * @param sku 活动商品
     */
    void clearActivitySkuStock(Long sku);

}
