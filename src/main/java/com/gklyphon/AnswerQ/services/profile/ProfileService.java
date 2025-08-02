package com.gklyphon.AnswerQ.services.profile;

import com.gklyphon.AnswerQ.dtos.EmailUpdateDto;
import com.gklyphon.AnswerQ.dtos.PasswordUpdateDto;
import com.gklyphon.AnswerQ.exceptions.exception.ElementNotFoundException;
import com.gklyphon.AnswerQ.mapper.IMapper;
import com.gklyphon.AnswerQ.models.User;
import com.gklyphon.AnswerQ.repositories.IUserRepository;
import com.gklyphon.AnswerQ.services.email.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final IUserRepository userRepository;
    private final IMapper mapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public ProfileService(IUserRepository userRepository, IMapper mapper, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void updatePassword(Long id, PasswordUpdateDto passwordUpdateDto) throws ElementNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
        if (!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), passwordUpdateDto.getNewPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        if (passwordEncoder.matches(passwordUpdateDto.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from old password");
        }
        user.setPassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateEmail(Long id, EmailUpdateDto emailUpdateDto) throws ElementNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
        if (!passwordEncoder.matches(emailUpdateDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (userRepository.existsByEmail(emailUpdateDto.getNewEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setEmail(emailUpdateDto.getNewEmail());
        userRepository.save(user);
    }

}
