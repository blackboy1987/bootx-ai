package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author black
 */
@Entity
public class Member extends User {

    /**
     * "登录失败尝试次数"缓存名称
     */
    public static final String FAILED_LOGIN_ATTEMPTS_CACHE_NAME = "memberFailedLoginAttempts";


    /**
     * 用户名
     */
    @NotEmpty(groups = Save.class)
    @Column(nullable = false, updatable = false,unique = true)
    @JsonView({PageView.class,ViewView.class})
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member parent;

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY)
    private Set<Member> children = new HashSet<>();

    private String deviceId;

    /**
     * 手机
     */
    @NotEmpty
    @Length(max = 11)
    @Pattern(regexp = "^1[3|4|5|7|8]\\d{9}$")
    @Column(nullable = false)
    private String mobile;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberRank memberRank;

    private Date memberRankExpiredDate;

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username
     *            用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Transient
    @Override
    public boolean isValidCredentials(Object credentials) {
        return true;
    }

    public Member getParent() {
        return parent;
    }

    public void setParent(Member parent) {
        this.parent = parent;
    }

    public Set<Member> getChildren() {
        return children;
    }

    public void setChildren(Set<Member> children) {
        this.children = children;
    }

    public @NotEmpty @Length(max = 11) @Pattern(regexp = "^1[3|4|5|7|8]\\d{9}$") String getMobile() {
        return mobile;
    }

    public void setMobile(@NotEmpty @Length(max = 11) @Pattern(regexp = "^1[3|4|5|7|8]\\d{9}$") String mobile) {
        this.mobile = mobile;
    }

    public MemberRank getMemberRank() {
        return memberRank;
    }

    public void setMemberRank(MemberRank memberRank) {
        this.memberRank = memberRank;
    }

    public Date getMemberRankExpiredDate() {
        return memberRankExpiredDate;
    }

    public void setMemberRankExpiredDate(Date memberRankExpiredDate) {
        this.memberRankExpiredDate = memberRankExpiredDate;
    }
}
