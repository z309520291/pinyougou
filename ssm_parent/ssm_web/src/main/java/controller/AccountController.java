package controller;

import domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.AccountService;

import java.util.List;

/**
 * 帐户web
 */
@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("findAll")
    public String findAll(Model model){
        System.out.println("表现层：查询所有账户...");
        // 调用service的方法
        List<Account> list = accountService.findAll();
        model.addAttribute("list",list);
        return "list";
    }

    /**
     * 保存
     * @return
     */
    @RequestMapping("save")
    public String save(Account account) {
        System.out.println("表现层：保存账户...");
        accountService.saveAccount(account);
        return "redirect:/account/findAll";
    }



}
