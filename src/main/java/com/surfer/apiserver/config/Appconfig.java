package com.surfer.apiserver.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //환경설정을 돕는 클래스
public class Appconfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean // bean을 통해 서버가 시작될떄 싱글톤으로 글로벌 영역에 생성해놓고 필요한 곳에서 주입 받아 사용가능
    public JPAQueryFactory getJPAQueryFactory() {
        return new JPAQueryFactory(entityManager);


    }


}
