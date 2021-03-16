package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.Issue;
import com.angelangelov.remont_bg.model.entities.User;
import com.angelangelov.remont_bg.model.services.IssueServiceModel;
import com.angelangelov.remont_bg.repository.IssueRepository;
import com.angelangelov.remont_bg.service.EmailService;
import com.angelangelov.remont_bg.service.IssueService;
import com.angelangelov.remont_bg.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class IssueServiceImpl implements IssueService {
    private final ModelMapper modelMapper;
    private final IssueRepository issueRepository;
    private final UserService userService;
    private final EmailService emailService;

    public IssueServiceImpl(ModelMapper modelMapper, IssueRepository issueRepository, UserService userService, EmailService emailService) {
        this.modelMapper = modelMapper;
        this.issueRepository = issueRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public IssueServiceModel submitIssue(IssueServiceModel issueServiceModel, String name) {

        Issue issue = modelMapper.map(issueServiceModel, Issue.class);
        User user = modelMapper.map(userService.findUserByUsername(name),User.class);
        issue.setUser(user);
        String userEmail = user.getEmail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        IssueServiceModel savedIssue = modelMapper.map(issueRepository.save(issue), IssueServiceModel.class);
        emailService.sendSimpleMessage(userEmail,
                String.format("Проблем: %s",issue.getProblemDescription()),String.format("Здравейте, %s %s %n," +
                        "Получихме информацията за вашия проблем, очаквайте отговор най-скоро време. %n Поздрави,%n Екипът на Ремонт.бг " ,firstName,lastName),"remontprojectbg@gmail.com");

        return savedIssue;
    }
}
