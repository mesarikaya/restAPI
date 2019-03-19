package com.mes.gotogether.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Privilege {

    @Id
    private String id;
    private String name;
    private String privilege;

}
