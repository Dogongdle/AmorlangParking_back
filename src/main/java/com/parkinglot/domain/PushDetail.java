package com.parkinglot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PushDetail {

    @Id @GeneratedValue
    @Column(name = "push_details_id")
    private Long id;

    private String sector;
    private int seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //연관관계 메서드
    public void setUser(User user){
        this.user = user;
        user.getPushDetails().add(this);
    }
}
