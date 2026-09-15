package org.hit.NCKH.service.impl;

import org.hit.NCKH.constant.ErrorMessage;
import org.hit.NCKH.domain.entity.User;
import org.hit.NCKH.security.UserPrincipal;
import org.hit.NCKH.service.CustomUserDetailsService;
import org.hit.NCKH.exception.NotFoundException;
import org.hit.NCKH.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_USERNAME,
                    new String[]{usernameOrEmail}));
    return UserPrincipal.create(user);
  }

  @Override
  @Transactional
  public UserDetails loadUserById(String id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{id}));
    return UserPrincipal.create(user);
  }

}
