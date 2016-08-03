package com.danieldisu;

import org.junit.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import rx.functions.Func1;
import utils.ComplexTestPojo;
import utils.TestPojo;

import static java.util.Collections.emptyList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static utils.TestUtils.assertEmptyCollection;
import static utils.TestUtils.assertEmptyList;
import static utils.TestUtils.getComplexTestList;
import static utils.TestUtils.getTestList;


public class ListsTest {

    @Test
    public void testMap() throws Exception {
        List<String> list = Lists.map(getTestList(), TestPojo::getName);

        assertEquals(3, list.size());

        List<String> result2 = Lists.map(new ArrayList<>(), Object::toString);

        assertNotNull(result2);
        assertEmptyList(result2);

        List<String> result3 = Lists.map(null, Object::toString);

        assertNotNull(result3);
        assertEmptyList(result3);
    }

    @Test
    public void testMapOrdered() throws Exception {

        List<String> result = Lists.mapOrdered(getTestList(), (testPojo, integer) -> testPojo.getName());
        assertEquals("James", result.get(0));

        List<String> result2 = Lists.mapOrdered(new ArrayList<>(), (o, integer) -> o.toString());

        assertNotNull(result2);
        assertEmptyList(result2);

        List<String> result3 = Lists.mapOrdered(null, (o, integer) -> o.toString());

        assertNotNull(result3);
        assertEmptyList(result3);

    }

    @Test
    public void testFilteredMap() throws Exception {

        List<Integer> result = Lists.filteredMap(getTestList(), testPojo -> {
            if (testPojo.getAge() == 25) {
                return null;
            } else {
                return testPojo.getAge();
            }
        });

        assertEquals(2, result.size());

        List<String> result2 = Lists.filteredMap(new ArrayList<>(), Object::toString);

        assertNotNull(result2);
        assertEmptyList(result2);

        List<String> result3 = Lists.filteredMap(null, Object::toString);

        assertNotNull(result3);
        assertEmptyList(result3);
    }

    @Test
    public void testFindMap() throws Exception {
        Func1<TestPojo, Integer> testPojoIntegerFunc1 = testPojo -> {
            if (testPojo.getAge() == 25) {
                return testPojo.getAge();
            } else {
                return null;
            }
        };

        Integer result = Lists.findMap(getTestList(), testPojoIntegerFunc1);

        assertNotNull(result);
        assertEquals(25, result.intValue());

        Integer result2 = Lists.findMap(null, testPojoIntegerFunc1);
        assertNull(result2);

        Integer result3 = Lists.findMap(emptyList(), testPojoIntegerFunc1);
        assertNull(result3);
    }

    @Test
    public void testEach() throws Exception {
        ArrayList<String> result = new ArrayList<>();
        Lists.each(getTestList(), testPojo -> result.add(testPojo.getName()));

        assertEquals(3, result.size());

        ArrayList<String> result2 = new ArrayList<>();
        Lists.each(new ArrayList<>(), Object::toString);

        assertEmptyList(result2);

        ArrayList<String> result3 = new ArrayList<>();
        Lists.each(null, Object::toString);

        assertEmptyList(result3);
    }

    @Test
    public void testFlatMap() throws Exception {
        List<TestPojo> result = Lists.flatMap(getComplexTestList(), ComplexTestPojo::getFriends);

        assertEquals(4, result.size());

        List<String> result2 = Lists.flatMap(new ArrayList<>(), o -> new ArrayList<String>());

        assertNotNull(result2);
        assertEmptyList(result2);

        List<String> result3 = Lists.flatMap(null, o -> new ArrayList<String>());

        assertNotNull(result3);
        assertEmptyList(result3);
    }

    @Test
    public void testFlatMapUnique() throws Exception {
        Set<TestPojo> result = Lists.flatMapUnique(getComplexTestList(), ComplexTestPojo::getFriends);

        assertEquals(3, result.size());

        Set<String> result2 = Lists.flatMapUnique(new ArrayList<>(), o -> new ArrayList<String>());

        assertNotNull(result2);
        assertEmptyCollection(result2);

        Set<String> result3 = Lists.flatMapUnique(null, o -> new ArrayList<String>());

        assertNotNull(result3);
        assertEmptyCollection(result3);
    }

    @Test
    public void testDistinct() throws Exception {
        ArrayList<TestPojo> list = new ArrayList<>();
        list.addAll(getTestList());
        list.addAll(getTestList());

        assertEquals(6, list.size());

        List<TestPojo> result = Lists.distinct(getTestList());

        assertEquals(3, result.size());
    }

    @Test
    public void testFilter() throws Exception {
        Func1<TestPojo, Boolean> testPojoBooleanFunc1 = testPojo -> testPojo.getAge() >= 25;

        List<TestPojo> result = Lists.filter(getTestList(), testPojoBooleanFunc1);

        assertEquals(2, result.size());

        List<TestPojo> result2 = Lists.filter(new ArrayList<>(), testPojoBooleanFunc1);

        assertNotNull(result2);
        assertEmptyCollection(result2);

        List<TestPojo> result3 = Lists.filter(null, testPojoBooleanFunc1);

        assertNotNull(result3);
        assertEmptyCollection(result3);
    }

    @Test
    public void testIndexOf() throws Exception {

        Func1<TestPojo, Boolean> testPojoBooleanFunc1 = testPojo -> testPojo.getAge() == 25;

        int result = Lists.indexOf(getTestList(), testPojoBooleanFunc1);

        assertEquals(0, result);

        int result2 = Lists.indexOf(null, testPojoBooleanFunc1);
        assertEquals(-1, result2);

        int result3 = Lists.indexOf(new ArrayList<>(), testPojoBooleanFunc1);
        assertEquals(-1, result3);
    }

    @Test
    public void testDiff() throws Exception {

        List<BigDecimal> result = Lists.diff(getTestList(), TestPojo::getMoney);

        assertEquals(2, result.size());

        List<BigDecimal> result2 = Lists.diff(new ArrayList<>(), TestPojo::getMoney);

        assertNotNull(result2);
        assertEmptyCollection(result2);

        List<BigDecimal> result3 = Lists.diff(null, TestPojo::getMoney);

        assertNotNull(result3);
        assertEmptyCollection(result3);

    }

    @Test
    public void testFind() throws Exception {

        Func1<TestPojo, Boolean> func = testPojo -> testPojo.getAge() == 25;

        TestPojo result = Lists.find(getTestList(), func);

        assertNotNull(result);
        assertTrue(result.getAge() == 25);

        TestPojo result2 = Lists.find(new ArrayList<>(), func);
        assertNull(result2);

        TestPojo result3 = Lists.find(null, func);
        assertNull(result3);
    }

    @Test
    public void testFirst() throws Exception {
        Func1<TestPojo, Boolean> func = testPojo -> testPojo.getAge() == 25;

        TestPojo result = Lists.first(getTestList(), func);

        assertNotNull(result);
        assertTrue(result.getAge() == 25);

        TestPojo result2 = Lists.first(new ArrayList<>(), func);
        assertNull(result2);

        TestPojo result3 = Lists.first(null, func);
        assertNull(result3);

    }

    @Test
    public void testAny() throws Exception {
        Func1<TestPojo, Boolean> func = testPojo -> testPojo.getAge() == 25;

        boolean result = Lists.any(getTestList(), func);
        assertTrue(result);

        boolean result2 = Lists.any(getTestList(), testPojo -> testPojo.getAge() == 99);
        assertFalse(result2);

        boolean result3 = Lists.any(new ArrayList<TestPojo>(), testPojo -> testPojo.getAge() == 99);
        assertFalse(result3);

        boolean result4 = Lists.any(null, testPojo -> true);
        assertFalse(result4);
    }

    @Test
    public void testAll() throws Exception {
        boolean result = Lists.all(getTestList(), testPojo -> testPojo.getAge() > 5);
        assertTrue(result);

        boolean result2 = Lists.all(getTestList(), testPojo -> testPojo.getAge() > 25);
        assertFalse(result2);

        boolean result3 = Lists.all(new ArrayList<TestPojo>(), testPojo -> testPojo.getAge() == 99);
        assertFalse(result3);

        boolean result4 = Lists.all(null, testPojo -> true);
        assertFalse(result4);

    }

    @Test
    public void testMinInt() throws Exception {
        int result = Lists.minInt(getTestList(), TestPojo::getAge);

        assertEquals(20, result);

        int result2 = Lists.minInt(new ArrayList<>(), TestPojo::getAge);
        assertEquals(0, result2);

        int result3 = Lists.minInt(null, TestPojo::getAge);
        assertEquals(0, result3);
    }

    @Test
    public void testMinBigDecimal() throws Exception {

        BigDecimal result = Lists.minBigDecimal(getTestList(), TestPojo::getMoney);
        assertEquals(new BigDecimal(20), result);

        BigDecimal result2 = Lists.minBigDecimal(new ArrayList<>(), TestPojo::getMoney);
        assertEquals(BigDecimal.ZERO, result2);

        BigDecimal result3 = Lists.minBigDecimal(null, TestPojo::getMoney);
        assertEquals(BigDecimal.ZERO, result3);
    }

    @Test
    public void testToListString() throws Exception {

        List<Integer> map = Lists.map(getTestList(), TestPojo::getAge);
        List<String> result = Lists.toListString(map);

        assertTrue(result.get(0).equals("25"));

        List<String> result2 = Lists.toListString(null);
        assertNotNull(result2);
        assertEmptyCollection(result2);

        List<String> result3 = Lists.toListString(emptyList());
        assertNotNull(result3);
        assertEmptyCollection(result3);
    }

    @Test
    public void testMaxInt() throws Exception {
        int result = Lists.maxInt(getTestList(), TestPojo::getAge);

        assertEquals(30, result);

        int result2 = Lists.maxInt(new ArrayList<>(), TestPojo::getAge);
        assertEquals(0, result2);

        int result3 = Lists.maxInt(null, TestPojo::getAge);
        assertEquals(0, result3);
    }

    @Test
    public void testOf() throws Exception {

        List<Integer> result = Lists.of(1, 2, 3, 4);

        assertEquals(4, result.size());
    }

    @Test
    public void testSum() throws Exception {

        List<Integer> list = Lists.of(1, 2, 3, 4);

        int result = Lists.sum(list);

        assertEquals(10, result);
    }

    @Test
    public void testSum2() throws Exception {

        int result = Lists.sum(getTestList(), TestPojo::getAge);

        assertEquals(75, result);

    }

    @Test
    public void testSumBigDecimal() throws Exception {

        BigDecimal result = Lists.sumBigDecimal(getTestList(), TestPojo::getMoney);

        assertEquals(new BigDecimal(80), result);
    }

    @Test
    public void testCount() throws Exception {

        int result = Lists.count(getTestList(), testPojo -> testPojo.getAge() > 25);

        assertEquals(1, result);
    }

    @Test
    public void testJoin() throws Exception {
        List<String> names = Lists.map(getTestList(), TestPojo::getName);
        String result = Lists.join(", ", names);
        assertEquals("James, Peter, Fred", result);
    }

    @Test
    public void testZipUnique() throws Exception {

        Set<TestPojo> result = Lists.zipUnique(getTestList(), Lists.of(new TestPojo("Jot", 25, BigDecimal.TEN)));

        assertEquals(4, result.size());

        Set<TestPojo> result2 = Lists.zipUnique(getTestList(), null);

        assertEquals(3, result2.size());

        Set<TestPojo> result3 = Lists.zipUnique(null, getTestList());

        assertEquals(3, result3.size());
    }

    @Test
    public void testReduceToString() throws Exception {
        String result = Lists.reduceToString(getTestList(), TestPojo::getName);
        assertEquals("JamesPeterFred", result);
    }

    @Test
    public void testFirst2() throws Exception {

        List<TestPojo> testList = getTestList();
        TestPojo result = Lists.first(testList);
        assertEquals(result, testList.get(0));

        Object result2 = Lists.first(null);
        assertNull(result2);

        Object result3 = Lists.first(new ArrayList<>());
        assertNull(result3);
    }

    @Test
    public void testUnshift() throws Exception {
        List<TestPojo> testList = getTestList();
        TestPojo testPojo = testList.get(2);
        List<TestPojo> result = Lists.unshift(testList, testPojo);

        assertEquals(4, result.size());
        assertEquals(testPojo, result.get(0));
    }

    @Test
    public void testMerge() throws Exception {
        List<TestPojo> testList = getTestList();
        List<TestPojo> testList2 = getTestList();

        List<TestPojo> result = Lists.merge(testList, testList2);

        assertEquals(6, result.size());
    }
}