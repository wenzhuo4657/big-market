package cn.wenzhuo4657.LuckySphere.infrastructure.persistent.repository;

import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyAwardEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.entity.StrategyRuleEntity;
import cn.wenzhuo4657.LuckySphere.domain.strategy.model.valobj.*;
import cn.wenzhuo4657.LuckySphere.domain.strategy.repository.IStrategyRepository;
import cn.wenzhuo4657.LuckySphere.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.dao.*;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.po.*;
import cn.wenzhuo4657.LuckySphere.infrastructure.persistent.redis.IRedisService;
import cn.wenzhuo4657.LuckySphere.types.common.Constants;
import cn.wenzhuo4657.LuckySphere.types.enums.ResponseCode;
import cn.wenzhuo4657.LuckySphere.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RMap;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @className: StrategyRepository
 * @author: wenzhuo4657
 * @date: 2024/9/19 9:47
 * @Version: 1.0
 */
@Slf4j
@Repository
public class StrategyRepository implements IStrategyRepository {
    @Resource
    private RaffleActivityDao raffleActivityDao;

    @Resource
    private UserCreditAccountDao userCreditAccountDao;


    @Resource
    private IRedisService redissonService;
    @Resource
    private StrategyAwardDao strategyAwardDao;

    @Resource
    private StrategyDao strategyDao;



    @Resource
    private StrategyRuleDao strategyRuleDao;

    @Resource
    private RuleTreeDao ruleTreeDao;
    @Resource
    private RuleTreeNodeDao ruleTreeNodeDao;
    @Resource
    private RuleTreeNodeLineDao ruleTreeNodeLineDao;



    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        String cacheKey= Constants.RedisKey.STRATEGY_AWARD_LIST_KEY+strategyId;
        List<StrategyAwardEntity> strategyAwardEntityList=redissonService.getValue(cacheKey);
        if (!Objects.isNull(strategyAwardEntityList)&&!strategyAwardEntityList.isEmpty()) return strategyAwardEntityList;

        strategyAwardEntityList=strategyAwardDao.queryStrategyAwardListByStrategyId(strategyId);

        redissonService.setValue(cacheKey,strategyAwardEntityList);
        return strategyAwardEntityList;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(String key, Integer rateRange, Map<Integer, Integer> strategyAwardSearchRateTable) {

        redissonService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY+key,rateRange);
        RMap<Object, Object> map = redissonService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY +key);
        map.putAll(strategyAwardSearchRateTable);
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, Integer rateKey) {
        return redissonService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY+key,rateKey);
    }

    @Override
    public int getRateRange(Long strategyId) {
        return redissonService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY+strategyId);
    }

    @Override
    public int getRateRange(String key) {
        String cacheKey=Constants.RedisKey.STRATEGY_RATE_RANGE_KEY+key;
        if (!redissonService.isExists(cacheKey)){
            throw  new AppException(ResponseCode.UN_ASSEMBLED_STRATEGY_ARMORY.getCode(),cacheKey + Constants.COLON + ResponseCode.UN_ASSEMBLED_STRATEGY_ARMORY.getInfo());
        }
        return redissonService.getValue(cacheKey);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        String  cache=Constants.RedisKey.STRATEGY_KEY+strategyId;
        StrategyEntity strategyEntity = redissonService.getValue(cache);
        if (!Objects.isNull(strategyEntity))return  strategyEntity;
        strategyEntity=strategyDao.getStrateEntityByStrategyId(strategyId);
        redissonService.setValue(cache,strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        return strategyRuleDao.queryStrategyRuleEntity(strategyId,ruleModel);
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRule strategyRule=new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleValue(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRule);
    }

    @Override
    public StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId) {
        String ruleModels = strategyAwardDao.queryStrategyAwardRuleModels(strategyId,awardId);
        return StrategyAwardRuleModelVO.builder().ruleModels(ruleModels).build();
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, String ruleModel) {
        StrategyRule strategyRule=new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRule);
    }

    @Override
    public RuleTreeVo queryRuleTreeVOByTreeId(String treeId) {
//        1,redis中快速查询
        String cacheKey=Constants.RedisKey.RULE_TREE_VO_KEY+ treeId;
        RuleTreeVo ruleTreeVo = redissonService.getValue(cacheKey);
        if (!Objects.isNull(ruleTreeVo)){
            return  ruleTreeVo;
        }
//      2，从mysql中查询原始数据
        RuleTree ruleTree=ruleTreeDao.queryRuleTreeByTreeId(treeId);
        List<RuleTreeNode> ruleTreeNodeList=ruleTreeNodeDao.queryRuleTreeNodeListByTreeId(treeId);
        List<RuleTreeNodeLine> ruleTreeNodeLineList=ruleTreeNodeLineDao.queryRuleTreeNodeLineByTreeId(treeId);

//       3，装配规则树
        Map<String,List<RuleTreeNodeLineVo>> ruleTreeNodeVoMap=new HashMap<>();
        for (RuleTreeNodeLine ruleTreeNodeLine:ruleTreeNodeLineList){
            RuleTreeNodeLineVo lineVo = RuleTreeNodeLineVo.builder()
                    .treeId(ruleTreeNodeLine.getTreeId())
                    .ruleNodeTo(ruleTreeNodeLine.getRuleNodeTo())
                    .ruleNodeFrom(ruleTreeNodeLine.getRuleNodeFrom())
                    .ruleLimitType(RuleLimitTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitType()))
                    .ruleLimitValue(RuleLogicCheckTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitValue()))
                    .build();
            /**
             *  @author:wenzhuo4657
                des:
            computeIfAbsent(key,default)
             检查key是否存在，如果存在则返回，如果不存在则使用默认处理创建value并返回，且在map中创建对应映射。

             该函数表面上还似乎浪费了时间，例如为何不提前创建映射？但是请注意，在数据库中查询返回的数据是list，在这中间做任何操作都避免不了增加一个O（n）循环或者以上。
             那么以此为基础，该函数的意义就凸显出来了，且查看其函数内部逻辑与手动编写无异。

            */
            List<RuleTreeNodeLineVo> ruleTreeNodeLineVos = ruleTreeNodeVoMap.computeIfAbsent(lineVo.getRuleNodeFrom(), k -> new ArrayList<>());
            ruleTreeNodeLineVos.add(lineVo);
        }

        Map<String,RuleTreeNodeVo> treeNodeVoMap=new HashMap<>();
        for (RuleTreeNode ruleTreeNode:ruleTreeNodeList){
            RuleTreeNodeVo nodeVo = RuleTreeNodeVo.builder()
                    .treeId(ruleTreeNode.getTreeId())
                    .ruleKey(ruleTreeNode.getRuleKey())
                    .ruleDesc(ruleTreeNode.getRuleDesc())
                    .ruleValue(ruleTreeNode.getRuleValue())
                    .treeNodeLineVOList(ruleTreeNodeVoMap.get(ruleTreeNode.getRuleKey()))
                    .build();
            treeNodeVoMap.put(nodeVo.getRuleKey(),nodeVo);
        }
        RuleTreeVo treeVo = RuleTreeVo.builder()
                .treeId(ruleTree.getTreeId())
                .treeDesc(ruleTree.getTreeDesc())
                .treeName(ruleTree.getTreeName())
                .treeNodeMap(treeNodeVoMap)
                .treeRootRuleNode(ruleTree.getTreeNodeRuleKey())
                .build();


//        4,更新到redis中
        redissonService.setValue(Constants.RedisKey.RULE_TREE_VO_KEY+treeId,treeVo);
        return treeVo;
    }

    @Override
    public void cacheStrategyAwardCount(String cacheKey, Integer awardCount) {
        if (redissonService.isExists(cacheKey))return;
        redissonService.setAtomicLong(cacheKey,awardCount);

    }


    /**
     *  @author:wenzhuo4657
        des: 加锁是为了兜底，并没有自动恢复库存的方式，相当于备份消费记录。
    */
    @Override
    public Boolean subtractionAwardStock(String cacheKey) {
        long surplus=redissonService.decr(cacheKey);
        if (surplus<0){
            redissonService.setAtomicLong(cacheKey,0);
            return  false;
        }
        String lockKey=cacheKey+Constants.UNDERLINE+surplus;
        Boolean lock=redissonService.setNx(lockKey);
        if (!lock){
            log.info("策略奖品库存加锁失败 {}", lockKey);
        }
        return lock;
    }

    @Override
    public void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO) {
        String queryKey = Constants.RedisKey.STRATEGY_AWARD_COUNT_QUERY_KEY;
        RBlockingQueue<StrategyAwardStockKeyVO> blockingQueue = redissonService.getBlockingQueue(queryKey);
        RDelayedQueue<StrategyAwardStockKeyVO> delayedQueue = redissonService.getDelayedQueue(blockingQueue);
        delayedQueue.offer(strategyAwardStockKeyVO,3, TimeUnit.SECONDS);

    }

    @Override
    public StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException {
        String queryKey=Constants.RedisKey.STRATEGY_AWARD_COUNT_QUERY_KEY;
        RBlockingQueue<StrategyAwardStockKeyVO> blockingQueue = redissonService.getBlockingQueue(queryKey);
        return blockingQueue.poll();
    }

    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        StrategyAward strategyAward=StrategyAward.builder()
                .strategyId(strategyId)
                .awardId(awardId).build();
        strategyAwardDao.updateStrategyAwardStock(strategyAward);
    }

    @Override
    public StrategyAwardEntity queryStrategyAwardEntity(Long strategyId, Integer awardId) {
        String cacheKey=Constants.RedisKey.STRATEGY_AWARD_KEY+strategyId+Constants.UNDERLINE+awardId;
        StrategyAwardEntity strategyAwardEntity = redissonService.getValue(cacheKey);
        if (null!=strategyAwardEntity)return strategyAwardEntity;
        strategyAwardEntity=strategyAwardDao.queryStrategyAwardEntity(StrategyAwardEntity.builder()
                .awardId(awardId)
                .strategyId(strategyId).build());
        redissonService.setValue(cacheKey,strategyAwardEntity);
        return strategyAwardEntity;
    }

    @Override
    public Long queryStrategyIdByActivityId(Long activityId) {
        return raffleActivityDao.queryStrategyIdByActivityId(activityId);
    }

    @Override
    public Map<String, Integer> queryAwardRuleLockCount(String[] treeIds) {
        if (Objects.isNull(treeIds)||treeIds.length==0) return new HashMap<>();
        List<RuleTreeNode> ruleTreeNodes=ruleTreeNodeDao.queryRuleLocks(treeIds);
        Map<String,Integer> resultMap=new HashMap<>();
        for (RuleTreeNode node:ruleTreeNodes){
            String treeId = node.getTreeId();
            Integer ruleValue = Integer.valueOf(node.getRuleValue());
            resultMap.put(treeId,ruleValue);
        }
        return resultMap;
    }

    @Override
    public List<RuleWeightVO> queryAwardRuleWeight(Long strategyId) {
        String  cacheKey=Constants.RedisKey.STRATEGY_RULE_WEIGHT_KEY+strategyId;
        List<RuleWeightVO> ruleWeightVOList=redissonService.getValue(cacheKey);
        if (null!=ruleWeightVOList) return  ruleWeightVOList;
        ruleWeightVOList=new ArrayList<>();
//        1，获取ruleValue
        StrategyRule strategyRule=new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setRuleModel(DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode());

        String ruleValue=strategyRuleDao.queryStrategyRuleValue(strategyRule);

//        2,解析ruleValue
        StrategyRuleEntity strategyRuleEntity = new StrategyRuleEntity();
        strategyRuleEntity.setRuleValue(ruleValue);
        strategyRuleEntity.setRuleModel(DefaultChainFactory.LogicModel.RULE_WEIGHT.getCode());
        Map<String, List<Integer>> ruleWeightValues = strategyRuleEntity.getRuleWeightValues();

//        3，封装ruleWeightVOList
        Set<String> ruleWeightKeys = ruleWeightValues.keySet();
        for (String key:ruleWeightKeys){
            List<Integer> awardIds = ruleWeightValues.get(key);
            List<RuleWeightVO.Award>  awardList =new ArrayList<>();
            for(Integer awardId:awardIds){
                StrategyAwardEntity strategyAwardReq=new StrategyAwardEntity();
                strategyAwardReq.setAwardId(awardId);
                strategyAwardReq.setStrategyId(strategyId);
                StrategyAward strategyAward = strategyAwardDao.queryStrategyAward(strategyAwardReq);
                awardList.add(RuleWeightVO.Award.builder()
                        .awardId(awardId)
                        .awardTitle(strategyAward.getAwardTitle())
                        .build());
            }
            ruleWeightVOList.add(RuleWeightVO.builder()
                    .awardIds(awardIds)
                    .awardList(awardList)
                    .ruleValue(ruleValue)

//                    注意key在解析时的格式时。4000:102,103,104,105
                    .weight(Integer.valueOf(key.split(Constants.COLON)[0]))
                    .build());

        }
        redissonService.setValue(cacheKey, ruleWeightVOList);

        return ruleWeightVOList;
    }

    @Override
    public Long queryUserDepleteAmonunt(String userId) {
        UserCreditAccount userCreditAccountReq=new UserCreditAccount();
        userCreditAccountReq.setUserId(userId);
        try{
            UserCreditAccount userCreditAccount= userCreditAccountDao.queryUserCreditAccount(userCreditAccountReq);
            return userCreditAccount.getTotalAmount().subtract(userCreditAccount.getAvailableAmount()).longValue();
        }finally {
        }

    }
}
