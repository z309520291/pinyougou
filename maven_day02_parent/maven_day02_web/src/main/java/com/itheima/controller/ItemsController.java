package com.itheima.controller;

import com.itheima.domain.Items;
import com.itheima.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @RequestMapping("/update")
    public String update(Model model) {
        Items items = itemsService.findById(1);
        model.addAttribute("item", items);

        System.out.println("update");
        return null;
    }

    @RequestMapping("/add")
    public String add(Model model){
        Items items = itemsService.findById(1);
        model.addAttribute("item", items);

        System.out.println("add");
        return "itemDetail";
    }
    @RequestMapping("/findDetail")
    public String findDetail(Model model){
        Items items = itemsService.findById(1);
        model.addAttribute("item", items);
        return "itemDetail";
    }
}
