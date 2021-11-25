package ru.netology.info;

import lombok.*;

@Data
@RequiredArgsConstructor
public class UserInfo {
    private final String login;
    private final String password;
    private final String status;
}
