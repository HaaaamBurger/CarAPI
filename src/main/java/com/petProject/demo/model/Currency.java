package com.petProject.demo.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@ToString
@Setter
@Getter
@Builder
@Document("CURRENCIES")
public class Currency {
    @MongoId
    private String currencyId;

    private Date updatedAt;

    private String ccy;

    private String base_ccy;

    private String buy;

    private String sale;
}
