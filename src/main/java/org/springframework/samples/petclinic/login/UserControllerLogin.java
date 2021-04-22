package org.springframework.samples.petclinic.login;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.samples.petclinic.login.UserDTO;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class UserControllerLogin {
    private final UserRepository users;

    public UserControllerLogin(UserRepository clinicService) {
        this.users = clinicService;
        // this.owners = clinicService;
    }

    @PostMapping("/user")
    public UserDTO login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

        User userTest = new User();
        userTest.setEmail(username);
        userTest.setPassword(pwd);
        User _user = this.users.findUser(username, pwd);
        if (_user != null) {
            String token = getJWTToken(username);
            UserDTO user = new UserDTO();
            user.setId(_user.getId());
            user.setUser(username);
            user.setToken(token);
            return user;
        } else
            return null;
    }

    private String getJWTToken(String username) {
        String secretKey = "secreto";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts.builder().setId("petJWT").setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (30 * 600000)))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
