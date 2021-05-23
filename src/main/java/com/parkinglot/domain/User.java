package com.parkinglot.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity(name="users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User implements UserDetails {

    @Id @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private Long serviceId;

    @Column(unique = true)
    private String username;
    private String password;

    private String apart;
    private String phoneNumber;

    //어떤 소셜로그인 (구글, 카카오 등)인지 저장하는 필드
    private String provider;

    //푸쉬알람을 위한 디바이스토큰 필드
    private String deviceToken;

    //푸쉬알람을 위한 IOS, ANDROID 등의 플랫폼을 저장하는 enum 필드
    @Enumerated(EnumType.STRING)
    private Platform platform;

    //푸쉬알람이 항상가는지 자리가 비었을때나 찼을때만 가는지 설정하는 enum 필드
    @Enumerated(EnumType.STRING)
    private PushStatus pushStatus = PushStatus.BOTH;

    //푸쉬설정을 저장한 엔티티와 일대다 매핑
    @OneToMany(mappedBy = "user")
    private List<PushDetail> pushDetails = new ArrayList<>();



    @Builder
    public User(Long serviceId, String username, String provider){
        this.serviceId=serviceId;
        this.username=username;
        this.provider=provider;
    }

    // 사용자의 권한을 콜렉션 형태로 반환
    // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : provider.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 사용자의 id를 반환 (unique한 값)
    @Override
    public String getUsername() {
        return username;
    }

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}