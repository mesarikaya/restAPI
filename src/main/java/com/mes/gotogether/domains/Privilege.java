package com.mes.gotogether.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class Privilege {

    @Id
    private String id;
    private String[] privilegeSet;
}
