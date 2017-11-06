package com.kpi.drproject;

import com.kpi.drproject.entity.CClass;
import com.kpi.drproject.entity.TClass;
import com.kpi.drproject.service.i.CClassService;
import com.kpi.drproject.service.i.TClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableCaching
public class DrProjectApplication {
    public static String HTML_DIR;

    public static void main(String[] args) {
        HTML_DIR = "/html/";
        SpringApplication.run(DrProjectApplication.class, args);
    }

    @Autowired
    private Environment environment;

    @Bean
    public CommandLineRunner demo(final TClassService tClassService,
                                  final CClassService cClassService) {
        return strings -> {
            System.out.println(environment.getProperty("hibernate.hbm2ddl.auto"));
            if ("create-drop".equals(environment.getProperty("hibernate.hbm2ddl.auto"))){
                initCClass(cClassService);
                initTClass(tClassService);
            }
        };
    }

    private void initCClass(CClassService cClassService) {
        cClassService.add(new CClass("object", "Об'єкт"));
        cClassService.add(new CClass("process", "Процес"));
        cClassService.add(new CClass("tech", "Технологія"));
        cClassService.add(new CClass("actor", "Дійова особа"));
        cClassService.add(new CClass("technology", "Тестологічний термін"));
        cClassService.add(new CClass("fact", "Факт, закон, явище"));
        cClassService.add(new CClass("activity", "Діяльність"));
        cClassService.add(new CClass("science", "Наука"));
    }

    private void initTClass(TClassService tClassService) {
        tClassService.add(new TClass("Definition", "Визначення"));
        tClassService.add(new TClass("Destination", "Призначення"));
        tClassService.add(new TClass("Entity", "Сутність"));
        tClassService.add(new TClass("Image", "Зображення"));
        tClassService.add(new TClass("Other", "Інше"));
    }
}
