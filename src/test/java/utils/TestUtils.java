package utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestUtils {

    public static List<TestPojo> getTestList() {
        ArrayList<TestPojo> list = new ArrayList<>();
        list.add(new TestPojo("James", 25, new BigDecimal(20)));
        list.add(new TestPojo("Peter", 20, new BigDecimal(30)));
        list.add(new TestPojo("Fred", 30, new BigDecimal(30)));
        return list;
    }

    public static List<ComplexTestPojo> getComplexTestList() {
        List<TestPojo> friendList1 = getTestList();
        ArrayList<TestPojo> friendList2 = new ArrayList<>();
        friendList2.add(friendList1.get(0));
        ArrayList<ComplexTestPojo> complexTestPojos = new ArrayList<>();
        complexTestPojos.add(new ComplexTestPojo("John", 30, new BigDecimal(500), friendList1));
        complexTestPojos.add(new ComplexTestPojo("Ned", 15, new BigDecimal(50), friendList2));
        return complexTestPojos;
    }


    public static <T> void assertEmptyList(List<T> list) {
        assertEquals(0, list.size());
    }

    public static <T> void assertEmptyCollection(Collection<T> collection) {
        assertEquals(0, collection.size());
    }
}
