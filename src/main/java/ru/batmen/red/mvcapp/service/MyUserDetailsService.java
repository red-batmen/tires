package ru.batmen.red.mvcapp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.batmen.red.mvcapp.dao.UserDao;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    //get user from the database, via Hibernate
    @Autowired
    private UserDao userDao;

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        ru.batmen.red.mvcapp.model.User user = userDao.findByUserName(username);
        List<GrantedAuthority> authorities =
                buildUserAuthority(user.getRoleText());

        return buildUserForAuthentication(user, authorities);

    }

    // Converts com.mkyong.users.model.User user to
    // org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(ru.batmen.red.mvcapp.model.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getEmail(), user.getPassword(),
                user.getState() == ru.batmen.red.mvcapp.model.User.STATE_ACTIVE, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(String role) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        setAuths.add(new SimpleGrantedAuthority(role));

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

}
