package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.services.IssueServiceModel;

public interface IssueService {
    IssueServiceModel submitIssue(IssueServiceModel issueServiceModel, String name);
}
