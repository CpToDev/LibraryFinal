package com.example.library2.library.User;


import com.example.library2.library.Student.Student;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", authorities='" + authorities + '\'' +
                ", student=" + student +
                '}';
    }

    public static final String DELIMITER=";";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;

    private String username;
    private String password;
    private String authorities;

    @OneToOne
    @JoinColumn
    private Student student;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

       return   Arrays.stream(this.authorities.split(DELIMITER))
                .map(authority-> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
