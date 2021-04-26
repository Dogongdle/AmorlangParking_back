package com.parkinglot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="apart")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApartCode {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String apart;

    private String code;
}
