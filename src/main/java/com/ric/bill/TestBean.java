package com.ric.bill;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Это пример работающего request bean или session bean, не удалять!
 * @author lev
 *
 */
@Slf4j
//@Scope(value = "request",  proxyMode = ScopedProxyMode.TARGET_CLASS)
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
@Service
public class TestBean {

	@PostConstruct
	public void constr() {
		log.info("TestBean Started!");
	}
	
	@PreDestroy
	public void dest() {
		log.info("TestBean Destroyed!");
	}
	
	public void getSome() {
		log.info("Get some from TestBean!");
	}
}
