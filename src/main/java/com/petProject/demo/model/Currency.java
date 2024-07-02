package com.petProject.demo.model;

import com.petProject.demo.common.type.Currencies;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@ToString
@Setter
@Getter
@Builder
@Document("CURRENCIES")
public class Currency {
    private String ccy;

    private String baseCcy;

    private String buy;

    private String sale;

}
