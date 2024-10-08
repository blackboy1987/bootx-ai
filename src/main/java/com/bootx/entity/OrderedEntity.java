
package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;

/**
 * Entity - 排序基类
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@MappedSuperclass
public abstract class OrderedEntity<ID extends Serializable> extends BaseEntity<ID> implements Comparable<OrderedEntity<ID>> {

	/**
	 * "排序"属性名称
	 */
	public static final String ORDER_PROPERTY_NAME = "order";

	/**
	 * 排序
	 */
	@Min(0)
	@Column(name = "orders")
	@JsonView({PageView.class, ViewView.class})
	private Integer order;

	/**
	 * 获取排序
	 *
	 * @return 排序
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * 设置排序
	 * 
	 * @param order
	 *            排序
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}

	/**
	 * 实现compareTo方法
	 * 
	 * @param orderEntity
	 *            排序对象
	 * @return 比较结果
	 */
	@Override
	public int compareTo(OrderedEntity<ID> orderEntity) {
		if (orderEntity == null) {
			return 1;
		}
		return new CompareToBuilder().append(getOrder(), orderEntity.getOrder()).append(getId(), orderEntity.getId()).toComparison();
	}

}