
package com.bootx.listener;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener - 初始化
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Component
public class InitListener {

	/**
	 * 事件处理
	 * 
	 * @param contextRefreshedEvent
	 *            ContextRefreshedEvent
	 */
	@EventListener
	public void handle(ContextRefreshedEvent contextRefreshedEvent) {
		System.out.println("aaaaaaaaaaaaaaaaa");
	}

}