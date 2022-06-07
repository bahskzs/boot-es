package com.yqy.bootes.service;

import com.yqy.bootes.entity.Hotel;
//import com.yqy.bootes.repo.EsRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author bahsk
 * @createTime 2022-06-04 21:39
 * @description
 * @program: boot-es
 */

@Service
public class EsService {

//    @Resource
//    private EsRepository esRepository;

//    public List<Hotel> getHotelFromTitle(String keyword){
//        return  esRepository.findByTitleLike(keyword);//调用搜索方法
//
//    }


    @Resource
    private RestHighLevelClient restHighLevelClient;

    public List<Hotel> getHotelFromTitle(String keyword){
        SearchRequest searchRequest=new SearchRequest("hotel");//客户端请求
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //构建query
        searchSourceBuilder.query(QueryBuilders.matchQuery("title",keyword));
        searchRequest.source(searchSourceBuilder);
        List<Hotel> resultList=new ArrayList<Hotel>();
        try{
            SearchResponse searchResponse=restHighLevelClient.search(searchRequest,
                    RequestOptions.DEFAULT);
            RestStatus status = searchResponse.status();
            if(status!=RestStatus.OK){
                return null;
            }
            SearchHits searchHits=searchResponse.getHits();
            for(SearchHit searchHit:searchHits){
                Hotel hotel=new Hotel();
                hotel.setId(searchHit.getId());                                 //文档_id
                hotel.setIndex(searchHit.getIndex());                           //索引名称
                hotel.setScore(searchHit.getScore());                           //文档得分

                //转换为Map
                Map<String, Object> dataMap= searchHit.getSourceAsMap();
                hotel.setTitle((String) dataMap.get("title"));  //设置标题
                hotel.setCity((String) dataMap.get("city"));    //设置城市
                hotel.setPrice((Double) dataMap.get("price"));  //设置价格
                resultList.add(hotel);
            }
            return resultList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
