package com.surfer.apiserver.common.constant;

import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
public class CommonCode {
    @AllArgsConstructor
    @Getter
    public enum MemberStatus {
        USE("정상 상태"), //사용
        WITHDRAW("탈퇴 상태"),   //탈퇴
        DORMANT("휴먼 상태");//휴면
        private final String desc;
    }

    @AllArgsConstructor
    @Getter
    public enum MemberAuthority {
        ROLE_ADMIN("관리자"),
        ROLE_DJ("DJ"),
        ROLE_SINGER("가수"),
        ROLE_GENERAL("일반사용자");
        private final String desc;
    }

    @AllArgsConstructor
    @Getter
    public enum LocationType {
        DOMESTIC("국내"),
        INTERNATIONAL("국외");
        private final String desc;
        public static LocationType fromDesc(String desc) {
            return Arrays.stream(LocationType.values())
                    .filter(status -> status.getDesc().equalsIgnoreCase(desc))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(ApiResponseCode.UNACCEPTED_REQUEST, HttpStatus.BAD_REQUEST));
        }
    }

    @AllArgsConstructor
    @Getter
    public enum Sector {
        POPULAR("대중"),
        CLASSICAL("클래식"),
        FOLK("순수"),
        TRADITIONAL("국악"),
        NURSERY_RHYME("동요"),
        RELIGIOUS("종교"),
        ;
        private final String desc;
        public static Sector fromDesc(String desc) {
            return Arrays.stream(Sector.values())
                    .filter(status -> status.getDesc().equalsIgnoreCase(desc))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(ApiResponseCode.UNACCEPTED_REQUEST, HttpStatus.BAD_REQUEST));
        }
    }

    @AllArgsConstructor
    @Getter
    public enum ArtistApplicationStatus {
        Pending("처리중"),
        Rejected("거절"),
        Completed("처리완료"),
        Deleted("삭제"),
        ;
        private final String desc;

        public static ArtistApplicationStatus fromDesc(String desc) {
            return Arrays.stream(ArtistApplicationStatus.values())
                    .filter(status -> status.getDesc().equalsIgnoreCase(desc))
                    .findFirst()
                    .orElseThrow(() -> new BusinessException(ApiResponseCode.UNACCEPTED_REQUEST, HttpStatus.BAD_REQUEST));
        }
    }
}
