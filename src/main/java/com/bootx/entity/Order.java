package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Long>{

    @NotEmpty
    @Column(nullable = false,updatable = false,unique = true)
    private String sn;

    /**
     * 状态
     * 0: 待支付
     * 1： 已取消
     * 2： 已支付
     * 3：已超时
     */
    @JsonView(BaseView.class)
    @Column(nullable = false)
    private Integer status;

    /**
     * 订单金额
     */
    @JsonView(BaseView.class)
    @Column(nullable = false, precision = 21, scale = 6)
    private BigDecimal amount;

    /**
     * 赠送积分
     */
    @Min(0)
    @Column(nullable = false)
    private Long rewardPoint;

    /**
     * 附言
     */
    @Length(max = 200)
    private String memo;

    /**
     * 过期时间
     */
    private Date expire;

    /**
     * 完成日期
     */
    private Date completeDate;

    /**
     * 会员
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberRank memberRank;

    @NotNull
    @Column(nullable = false,updatable = false)
    private Integer type;

    /**
     * 获取编号
     *
     * @return 编号
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置编号
     *
     * @param sn
     *            编号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status
     *            状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取订单金额
     *
     * @return 订单金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置订单金额
     *
     * @param amount
     *            订单金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取赠送积分
     *
     * @return 赠送积分
     */
    public Long getRewardPoint() {
        return rewardPoint;
    }

    /**
     * 设置赠送积分
     *
     * @param rewardPoint
     *            赠送积分
     */
    public void setRewardPoint(Long rewardPoint) {
        this.rewardPoint = rewardPoint;
    }

    /**
     * 获取附言
     *
     * @return 附言
     */
    public String getMemo() {
        return memo;
    }

    /**
     * 设置附言
     *
     * @param memo
     *            附言
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * 获取过期时间
     *
     * @return 过期时间
     */
    public Date getExpire() {
        return expire;
    }

    /**
     * 设置过期时间
     *
     * @param expire
     *            过期时间
     */
    public void setExpire(Date expire) {
        this.expire = expire;
    }

    /**
     * 获取会员
     *
     * @return 会员
     */
    public Member getMember() {
        return member;
    }

    /**
     * 设置会员
     *
     * @param member
     *            会员
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * 判断是否已过期
     *
     * @return 是否已过期
     */
    @JsonView(BaseView.class)
    @Transient
    public boolean hasExpired() {
        return getExpire() != null && !getExpire().after(new Date());
    }

    /**
     * 获取完成日期
     *
     * @return 完成日期
     */
    public Date getCompleteDate() {
        return completeDate;
    }

    /**
     * 设置完成日期
     *
     * @param completeDate
     *            完成日期
     */
    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public MemberRank getMemberRank() {
        return memberRank;
    }

    public void setMemberRank(MemberRank memberRank) {
        this.memberRank = memberRank;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
