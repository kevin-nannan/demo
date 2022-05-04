1. spring的事务管理器
2. SpringBootTest和junit的区别
3. 事务管理器PlatformTransactionManager和它的实现类 DataSourceTransactionManager
4. transactionTemplate的用法
这样粒度控制比较好
```java
import javax.annotation.Resource;

public class Main {
    @Resource
    TransactionTemplate transactionTemplate;

    public static void main(String[] args) {
        transactionTemplate.execute(new TransactionCallback() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                capitalImportDOMapper.updateByExample(capitalImport, modifyEx);
                capitalDetailDOMapper.updateByExample(capitalDetailDO, capitalDetailDOExample);
                return Boolean.TRUE;
            }
        });
    }
}
```
5， transactionTemplate的原理
6. 为什么自己调用自己不开启proxy
