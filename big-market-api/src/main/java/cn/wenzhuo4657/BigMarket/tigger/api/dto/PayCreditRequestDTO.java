package cn.wenzhuo4657.BigMarket.tigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: wenzhuo4657
 * @date: 2024/12/8
 * @description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PayCreditRequestDTO implements Serializable {

/**
 *  唯一凭证
*/

    String openID;


/**
* 充值额度
* */
    String Quota;
}
