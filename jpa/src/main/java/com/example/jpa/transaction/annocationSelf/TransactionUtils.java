package com.example.jpa.transaction.annocationSelf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * 手写事务注解:
 *  2,封装手动事务
 */
@Component
@Scope("prototype") //多例,线程安全
public class TransactionUtils {

	private TransactionStatus transactionStatus;

	// 获取事务源
	//dataSourceTransactionManager是留给我们实现的事务管理器
	@Resource
	private PlatformTransactionManager dataSourceTransactionManager ;

	// 开启事务
	public TransactionStatus begin() {
		transactionStatus =
				dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
		return transactionStatus;
	}

	// 提交事务
	public void commit(TransactionStatus transactionStatus) {
		dataSourceTransactionManager.commit(transactionStatus);
	}

	// 回滚事务
	public void rollback() {
		dataSourceTransactionManager.rollback(transactionStatus);
	}

}
