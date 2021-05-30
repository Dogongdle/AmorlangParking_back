package com.parkinglot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 현재 하나의 아파트에서의 테스트이므로
// apart name="광교아이파크", code="sayharahihello"로 설정
// 추후에 다른 아파트의 확장 가능성을 고려하여 남겨둠

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
