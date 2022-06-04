package com.yqy.bootes.controller;

import com.yqy.bootes.entity.Hotel;
import com.yqy.bootes.service.EsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bahsk
 * @createTime 2022-06-04 21:40
 * @description
 * @program: boot-es
 */

@RestController
public class TestController {

    @Resource
    private EsService esService;

    @GetMapping("/test")
    public List<Hotel> findByTitle(@RequestParam String title) {
        List<Hotel> hotelFromTitle = esService.getHotelFromTitle(title);
        return hotelFromTitle;

    }
}
