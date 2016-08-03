package utils;

import java.math.BigDecimal;

public class TestPojo {

    private String name;
    private Integer age;
    private BigDecimal money;

    public TestPojo(String name, Integer age, BigDecimal money) {
        this.name = name;
        this.age = age;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public BigDecimal getMoney() {
        return money;
    }
}
