package service.impl;

import dao.AccountDao;
import domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import service.AccountService;

import java.io.File;
import java.util.List;

@Component("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    public List<Account> findAll() {

        ApplicationContext application = new ClassPathXmlApplication(new File("applicationContext.xml"));
        System.out.println("业务层：查询所有账户...");
        List<Account> list = accountDao.findAll();
        return list;
    }

    public void saveAccount(Account account) {
        System.out.println("业务层：保存帐户...");
        accountDao.saveAccount(account);
    }
}
