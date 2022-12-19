package com.example.sb_rest.exceptionHandler;

import org.springframework.dao.DataIntegrityViolationException;

public class UserWhithSuchUsernameExist extends DataIntegrityViolationException {
    public UserWhithSuchUsernameExist(String msg) {
        super(msg); }
}
