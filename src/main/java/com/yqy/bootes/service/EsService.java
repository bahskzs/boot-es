package com.yqy.bootes.service;

import com.yqy.bootes.entity.Hotel;
import com.yqy.bootes.repo.EsRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bahsk
 * @createTime 2022-06-04 21:39
 * @description
 * @program: boot-es
 */

@Service
public class EsService {

    @Resource
    private EsRepository esRepository;

    public List<Hotel> getHotelFromTitle(String keyword){
        return  esRepository.findByTitleLike(keyword);//调用搜索方法

    }
}
