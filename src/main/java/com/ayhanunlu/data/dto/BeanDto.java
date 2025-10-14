package com.ayhanunlu.data.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
@Builder
public class BeanDto {
    private Long id;
    private String beanName;
    private String beanData;

    // Start
    public void initialBeanMethod() {
        log.info("Before Bean is initialized Method works earlier");
        System.out.println("This is the method which will work before Bean starts initialization.");
    }

    // End
    public void destroyBeanMethod() {
        log.info("After bean is destroyed this Method will work afterwards");
        System.out.println("This is the method which will work after bean is destroyed.");
    }

}
