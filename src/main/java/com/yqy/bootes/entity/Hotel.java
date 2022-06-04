package com.yqy.bootes.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author bahsk
 * @createTime 2022-06-04 21:37
 * @description
 * @program: boot-es
 */

@Document(indexName = "hotel")
@Data
public class Hotel {
    @Id                 //对应Elasticsearch的_id
    String id;
    String title;      //对应索引中的title
    String city;       //对应索引中的city
    String price;      //对应索引中的price
}
