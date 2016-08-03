package utils;


import java.math.BigDecimal;
import java.util.List;

public class ComplexTestPojo extends TestPojo {

    private List<TestPojo> friends;

    public ComplexTestPojo(String name, Integer age, BigDecimal money, List<TestPojo> friends) {
        super(name, age, money);
        this.friends = friends;
    }

    public List<TestPojo> getFriends() {
        return friends;
    }
}
