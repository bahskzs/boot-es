package com.yqy.bootes.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.yqy.bootes.entity.Hotel;
//import com.yqy.bootes.repo.EsRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
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


//    @Resource
//    private RestHighLevelClient restHighLevelClient;
//
//    public List<Hotel> getHotelFromTitle(String keyword){
//        SearchRequest searchRequest=new SearchRequest("hotel");//客户端请求
//        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
//        //构建query
//        searchSourceBuilder.query(QueryBuilders.matchQuery("title",keyword));
//        searchRequest.source(searchSourceBuilder);
//        List<Hotel> resultList=new ArrayList<Hotel>();
//        try{
//            SearchResponse searchResponse=restHighLevelClient.search(searchRequest,
//                    RequestOptions.DEFAULT);
//            RestStatus status = searchResponse.status();
//            if(status!=RestStatus.OK){
//                return null;
//            }
//            SearchHits searchHits=searchResponse.getHits();
//            for(SearchHit searchHit:searchHits){
//                Hotel hotel=new Hotel();
//                hotel.setId(searchHit.getId());                                 //文档_id
//                hotel.setIndex(searchHit.getIndex());                           //索引名称
//                hotel.setScore(searchHit.getScore());                           //文档得分
//
//                //转换为Map
//                Map<String, Object> dataMap= searchHit.getSourceAsMap();
//                hotel.setTitle((String) dataMap.get("title"));  //设置标题
//                hotel.setCity((String) dataMap.get("city"));    //设置城市
//                hotel.setPrice((Double) dataMap.get("price"));  //设置价格
//                resultList.add(hotel);
//            }
//            return resultList;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    @Autowired
    private ElasticsearchClient client;

    public List<Hotel> getHotelFromTitle(String keyword) throws IOException {

        List<Hotel> resultList=new ArrayList<Hotel>();

        // 目前感觉新版本的更好写，因为语法更贴近原始的DSL
        SearchResponse<Hotel> search = client.search(
                s -> s.index("hotel")
                        .query(
                                q -> q.term(
                                        t -> t.field("city")
                                         .value(v -> v.stringValue(keyword))
                                )
                        )
                , Hotel.class);

        for (Hit<Hotel> hit : search.hits().hits()) {
            Hotel hotel = hit.source();
            hotel.setIndex(hit.index());
            hotel.setId(hit.id());
            resultList.add(hotel);
        }
        return resultList;
    }
}
