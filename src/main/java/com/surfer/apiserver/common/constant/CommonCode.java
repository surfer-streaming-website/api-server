package com.surfer.apiserver.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommonCode {
    @AllArgsConstructor
    @Getter
    public enum MemberStatus {
        USE("정상 상태"), //사용
        WITHDRAW("탈퇴 상태"),   //탈퇴
        DORMANT("휴먼 상태");//휴면
        private String desc;
    }

    @AllArgsConstructor
    @Getter
    public enum MemberAuthority {
        ROLE_ADMIN("관리자"),
        ROLE_DJ("DJ"),
        ROLE_SINGER("가수"),
        ROLE_GENERAL("일반사용자");
        private String desc;
    }
}
