package com.api.redis.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {

    private String id;
    private String name;
    private String mobile;
    private String email;
}
