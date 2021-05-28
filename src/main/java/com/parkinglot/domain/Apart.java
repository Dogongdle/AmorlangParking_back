package com.parkinglot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="aparts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Apart {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "apart_id")
    private Long id;

    private String name;
    private String code;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User users;

//    @OneToMany(mappedBy = "apart", cascade = CascadeType.ALL)
//    private List<Parking> parkings = new ArrayList<>();
}
