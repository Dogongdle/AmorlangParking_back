package com.parkinglot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name="parking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parking {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "parking_id")
    private Long id;

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apart_id")
    private Apart apart;*/

    private String sector;


}