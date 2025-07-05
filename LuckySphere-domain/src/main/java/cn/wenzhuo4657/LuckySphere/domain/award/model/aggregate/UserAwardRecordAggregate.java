package cn.wenzhuo4657.LuckySphere.domain.award.model.aggregate;

import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.UserAwardRecordEntity;
import cn.wenzhuo4657.LuckySphere.domain.award.model.entity.TaskEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wenzhuo4657
 * @date: 2024/10/28
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAwardRecordAggregate {
    private UserAwardRecordEntity userAwardRecordEntity;

    private TaskEntity taskEntity;
}
