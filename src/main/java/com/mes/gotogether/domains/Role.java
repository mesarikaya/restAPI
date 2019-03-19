package com.mes.gotogether.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;

@Data
@Document
public class Role {

    @Id
    private String id;
    private String name;
    private HashSet<Privilege> privileges;
}
