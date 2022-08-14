package dev.mvvasilev.service;

import dev.mvvasilev.exception.UnknownUserException;
import dev.mvvasilev.model.dto.UserSelfDTO;
import dev.mvvasilev.model.entity.User;
import dev.mvvasilev.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

    @Value("${mail.from}")
    private String fromEmail;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final MailSender mailSender;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, MailSender mailSender) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.mailSender = mailSender;
    }

    public UserSelfDTO self() {
        return findCurrentUser()
                .map(u -> modelMapper.map(u, UserSelfDTO.class))
                .orElseThrow(UnknownUserException::new);
    }

    public void sendRegistrationConfirmationEmail(User user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Confirm Your E-mail");
        simpleMailMessage.setText("Your confirmation code: " + user.getRegistrationCode());

        mailSender.send(simpleMailMessage);
    }

    public Optional<User> findCurrentUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            long id = Long.parseLong(userId);
            return userRepository.findById(id);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
