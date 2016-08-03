package com.danieldisu;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class Lists {

    /**
     * Apply the given function to each element of the list and returns a list with the results
     *
     * @param originalList    -
     * @param functionToApply -
     * @param <A>             The type of the elements of the original list
     * @param <B>             The type of the elements of the returned list
     * @return Returns a list of B elements.
     */
    public static <A, B> List<B> map(Collection<A> originalList, Func1<A, B> functionToApply) {
        ArrayList<B> resultList = new ArrayList<>();

        if (originalList == null || originalList.size() == 0) return resultList;

        for (A element : originalList) {
            resultList.add(functionToApply.call(element));
        }

        return resultList;
    }

    /**
     * Apply the given function to each element of the list and returns a list with the results. This is different than
     * the normal map because the given function also receives the index of the number.
     *
     * @param originalList    -
     * @param functionToApply -
     * @param <A>             The type of the elements of the original list
     * @param <B>             The type of the elements of the returned list
     * @return Returns a list of B elements.
     */
    public static <A, B> List<B> mapOrdered(List<A> originalList, Func2<A, Integer, B> functionToApply) {
        ArrayList<B> resultList = new ArrayList<>();

        if (originalList == null || originalList.size() == 0) return resultList;

        for (int i = 0; i < originalList.size(); i++) {
            resultList.add(functionToApply.call(originalList.get(i), i));
        }

        return resultList;
    }

    /**
     * Apply the given function to each element of the list and returns a list with the results. If the given function returns
     * null that element will not be added to the result function.
     *
     * @param originalList    -
     * @param functionToApply -
     * @param <A>             The type of the elements of the original list
     * @param <B>             The type of the elements of the returned list
     * @return Returns a list of B elements.
     */
    public static <A, B> List<B> filteredMap(Collection<A> originalList, Func1<A, B> functionToApply) {
        ArrayList<B> resultList = new ArrayList<>();

        if (originalList == null || originalList.size() == 0) return resultList;

        for (A element : originalList) {
            B result = functionToApply.call(element);
            if (result != null) resultList.add(result);
        }

        return resultList;
    }

    /**
     * Apply the given function to each element of the list and returns the first element that is not null.
     *
     * @param originalList    -
     * @param functionToApply -
     * @param <A>             The type of the elements of the original list
     * @param <B>             The type of the elements of the returned list
     * @return Returns an element of B or null
     */
    @Nullable
    public static <A, B> B findMap(Collection<A> originalList, Func1<A, B> functionToApply) {

        if (checkIfListIsNullOrEmpty(originalList)) return null;

        for (A element : originalList) {
            B result = functionToApply.call(element);
            if (result != null) return result;
        }

        return null;
    }

    /**
     * Apply the given function to every element of the list
     *
     * @param originalList    -
     * @param functionToApply -
     * @param <A>             The type of the elements of the original list
     */
    public static <A> void each(final Collection<A> originalList, Action1<A> functionToApply) {

        if (checkIfListIsNullOrEmpty(originalList)) return;

        for (A element : originalList) {
            functionToApply.call(element);
        }

    }

    /**
     * Apply the given function to each element of the list and returns a list with the results, because each element of the original list
     * must be a list by itself, it will add all the returned elements to the same list, effectively flattening the list.
     *
     * @param originalList    -
     * @param functionToApply -
     * @param <A>             The type of the elements of the original list
     * @param <B>             The type of the elements of the returned list
     * @return Returns a list of B elements.
     */
    public static <A, B> List<B> flatMap(Collection<A> originalList, Func1<A, List<B>> functionToApply) {
        ArrayList<B> bElements = new ArrayList<>();

        if (checkIfListIsNullOrEmpty(originalList)) return bElements;

        for (A element : originalList) {
            List<B> result = functionToApply.call(element);
            bElements.addAll(result);
        }

        return bElements;
    }

    /**
     * Apply the given function to each element of the list and returns a list with the results, because each element of the original list
     * must be a list by itself, it will add all the returned elements to the same list, effectively flattening the list.
     *
     * @param originalList    -
     * @param functionToApply -
     * @param <A>             The type of the elements of the original list
     * @param <B>             The type of the elements of the returned list
     * @return Returns a list of B elements.
     */
    public static <A, B> Set<B> flatMapUnique(List<A> originalList, Func1<A, Collection<B>> functionToApply) {
        HashSet<B> bElements = new HashSet<>();

        if (checkIfListIsNullOrEmpty(originalList)) return bElements;

        for (A element : originalList) {
            Collection<B> result = functionToApply.call(element);
            bElements.addAll(result);
        }

        return bElements;
    }

    /**
     * @param originalList -
     * @param <A>          The type of the elements of the original list
     * @return Returns a list without duplicates
     */
    public static <A> List<A> distinct(List<A> originalList) {
        ArrayList<A> filteredList = new ArrayList<>();

        if (checkIfListIsNullOrEmpty(originalList)) return filteredList;

        for (A originalItem : originalList) {
            if (!filteredList.contains(originalItem)) filteredList.add(originalItem);
        }

        return filteredList;
    }

    /**
     * Applies the function to each element of the list, if the functions returns true the element will be present in the returning
     * list
     *
     * @param originalList -
     * @param func         a func that returns true if the element should be in the returning list
     * @param <A>          The type of the elements of the original list
     * @return a list with all the elements that the result of the func was true
     */
    public static <A> List<A> filter(Collection<A> originalList, Func1<A, Boolean> func) {
        ArrayList<A> filteredList = new ArrayList<>();

        if (checkIfListIsNullOrEmpty(originalList)) return filteredList;

        for (A element : originalList) {
            boolean shouldInclude = func.call(element);
            if (shouldInclude) filteredList.add(element);
        }

        return filteredList;
    }

    /**
     * Applies a function to each element of the list and returns the index of the first element that passes the predicate
     *
     * @param originalList -
     * @param func         a func that returns true if the element should be in the returning list
     * @param <A>          The type of the elements of the original list
     * @return the index of the first element
     */
    public static <A> int indexOf(List<A> originalList, Func1<A, Boolean> func) {
        if (checkIfListIsNullOrEmpty(originalList)) return -1;

        for (int i = 0; i < originalList.size(); i++) {
            boolean funcResult = func.call(originalList.get(i));
            if (funcResult) return i;
        }

        return -1;
    }

    /**
     * Map the original collection to a new collection without duplicates
     *
     * @param originalList -
     * @param func         -
     * @param <A>          The type of the elements of the original list
     * @param <B>          The type of the elements of the returned list
     * @return
     */
    public static <A, B> List<B> diff(List<A> originalList, Func1<A, B> func) {
        ArrayList<B> resultList = new ArrayList<>();

        if (checkIfListIsNullOrEmpty(originalList)) return resultList;

        for (A element : originalList) {
            B result = func.call(element);
            if (!resultList.contains(result)) resultList.add(result);
        }

        return resultList;
    }

    /**
     * @param originalList -
     * @param predicate    -
     * @param <A>          The type of the elements of the original list
     * @return Returns the first element that fulfill the predicate
     */
    @Nullable
    public static <A> A find(Collection<A> originalList, Func1<A, Boolean> predicate) {

        if (checkIfListIsNullOrEmpty(originalList)) return null;

        for (A element : originalList) {
            boolean shouldInclude = predicate.call(element);
            if (shouldInclude) return element;
        }

        return null;
    }

    /**
     * find the first element that fulfill the predicate
     *
     * @param originalList
     * @param object
     * @param <A>
     * @return
     */
    @Nullable
    public static <A> A findByHashCode(Collection<A> originalList, Object object) {
        for (A element : originalList) {
            boolean equal = object.hashCode() == element.hashCode();
            if (equal) return element;
        }

        return null;
    }

    /**
     * Find alias, this show the reader that the list may contain more than one element matching the predicate
     *
     * @param originalList -
     * @param predicate    -
     * @param <A>          The type of the elements of the original list
     * @return Returns the first element that fulfill the predicate
     */
    @Nullable
    public static <A> A first(List<A> originalList, Func1<A, Boolean> predicate) {
        return find(originalList, predicate);
    }

    /**
     * @param originalList -
     * @param predicate    -
     * @param <A>          The type of the elements of the original list
     * @return Returns true if any element matches the predicate
     */
    public static <A> boolean any(Collection<A> originalList, Func1<A, Boolean> predicate) {

        if (checkIfListIsNullOrEmpty(originalList)) return false;

        for (A element : originalList) {
            boolean res = predicate.call(element);
            if (res) return true;
        }

        return false;
    }

    /**
     * @param originalList -
     * @param predicate    -
     * @param <A>          The type of the elements of the original list
     * @return Returns true if ALL element matches the predicate
     */
    public static <A> boolean all(Collection<A> originalList, Func1<A, Boolean> predicate) {
        if (checkIfListIsNullOrEmpty(originalList)) return false;

        for (A aElement : originalList) {
            boolean res = predicate.call(aElement);
            if (!res) return false;
        }

        return true;
    }

    /**
     * This applies the function to each element of the list and returns the minimum value
     *
     * @param originalList -
     * @param func         -
     * @param <A>          The type of the elements of the original list
     * @return the minimum value
     */
    public static <A> int minInt(List<A> originalList, Func1<A, Integer> func) {
        int min = Integer.MAX_VALUE;

        if (checkIfListIsNullOrEmpty(originalList)) return 0;

        for (A aElement : originalList) {
            Integer integer = func.call(aElement);
            min = Math.min(integer, min);
        }

        return min;
    }

    /**
     * minInt alias
     *
     * @param originalList -
     * @param func         -
     * @param <A>          The type of the elements of the original list
     * @return the minimum value
     */
    public static <A> int min(List<A> originalList, Func1<A, Integer> func) {
        return minInt(originalList, func);
    }

    /**
     * This applies the function to each element of the list and returns the minimum value
     *
     * @param originalList -
     * @param func         -
     * @param <A>          The type of the elements of the original list
     * @return the minimum value
     */
    public static <A> BigDecimal minBigDecimal(List<A> originalList, Func1<A, BigDecimal> func) {
        BigDecimal min = new BigDecimal(Integer.MAX_VALUE);

        if (checkIfListIsNullOrEmpty(originalList)) return BigDecimal.ZERO;

        for (A aElement : originalList) {
            BigDecimal element = func.call(aElement);
            min = element.min(min);
        }

        return min;
    }

    /**
     * This turns a list of elements into a list of strings using .toString
     *
     * @param originalList
     * @return
     */
    public static List<String> toListString(List<?> originalList) {
        ArrayList<String> resultList = new ArrayList<>();

        if (checkIfListIsNullOrEmpty(originalList)) return resultList;


        for (Object selectable : originalList) {
            resultList.add(selectable.toString());
        }

        return resultList;
    }

    /**
     * This applies the function to each element of the list and returns the maximum value
     *
     * @param originalList -
     * @param func         -
     * @param <A>          The type of the elements of the original list
     * @return the maximum value
     */
    public static <A> int maxInt(List<A> originalList, Func1<A, Integer> func) {
        int max = Integer.MIN_VALUE;

        if (checkIfListIsNullOrEmpty(originalList)) return 0;

        for (A aElement : originalList) {
            Integer element = func.call(aElement);
            max = Math.max(element, max);
        }

        return max;
    }

    /**
     * Returns a list containing the given elements
     *
     * @param elements -
     * @param <T>      The type of the elements of the original list
     * @return the list
     */
    @SafeVarargs
    public static <T> List<T> of(T... elements) {
        ArrayList<T> list = new ArrayList<T>();

        for (T element : elements) {
            list.add(element);
        }
        return list;
    }

    /**
     * Returns the sum of the elements
     *
     * @param originalList -
     * @return the sum
     */
    public static int sum(Collection<Integer> originalList) {
        int total = 0;

        if (checkIfListIsNullOrEmpty(originalList)) return total;

        for (Integer element : originalList) {
            total += element;
        }

        return total;
    }

    /**
     * This applies the function to each element of the list and returns the sum of the elements
     *
     * @param originalList -
     * @param func         -
     * @param <A>          The type of the elements of the original list
     * @return the maximum value
     */
    public static <A> int sum(List<A> originalList, Func1<A, Integer> func) {
        int total = 0;

        if (checkIfListIsNullOrEmpty(originalList)) return total;

        for (A aElement : originalList) {
            Integer element = func.call(aElement);
            total += element;
        }

        return total;
    }

    /**
     * This applies the function to each element of the list and returns the sum of the elements
     *
     * @param originalList -
     * @param func         -
     * @param <A>          The type of the elements of the original list
     * @return the sum of the values
     */
    public static <A> BigDecimal sumBigDecimal(List<A> originalList, Func1<A, BigDecimal> func) {
        BigDecimal total = BigDecimal.ZERO;

        if (checkIfListIsNullOrEmpty(originalList)) return total;

        for (A aElement : originalList) {
            BigDecimal element = func.call(aElement);
            total = total.add(element);
        }

        return total;
    }

    /**
     * This applies the function to each element of the list and returns the number of elements that matches the predicate
     *
     * @param originalList -
     * @param predicate    -
     * @param <A>          The type of the elements of the original list
     * @return the number of elements that matches the predicate
     */
    public static <A> int count(Collection<A> originalList, Func1<A, Boolean> predicate) {
        int count = 0;

        if (checkIfListIsNullOrEmpty(originalList)) return count;

        for (A element : originalList) {
            boolean shouldInclude = predicate.call(element);
            if (shouldInclude) count++;
        }

        return count;
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(CharSequence delimiter, Iterable tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /**
     * Merges two homogeneus lists into one
     *
     * @param collection1 collection1
     * @param collection2 collection1
     * @param <A>         the type of the collections
     * @return a set containing unique values of both lists
     */
    public static <A> Set<A> zipUnique(Collection<A> collection1, Collection<A> collection2) {
        HashSet<A> resultSet = new HashSet<>();

        if (!checkIfListIsNullOrEmpty(collection1)) {
            for (A a : collection1) {
                if (!resultSet.contains(a)) resultSet.add(a);
            }
        }

        if (!checkIfListIsNullOrEmpty(collection2)) {
            for (A a : collection2) {
                if (!resultSet.contains(a)) resultSet.add(a);
            }
        }

        return resultSet;
    }

    /**
     * This applies the function to each element of the list and returns the sum of the elements
     *
     * @param originalList    -
     * @param functionToApply -
     * @param <A>             The type of the elements of the original list
     * @return the sum of the values
     */
    public static <A> String reduceToString(Collection<A> originalList, Func1<A, String> functionToApply) {
        String result = "";

        if (checkIfListIsNullOrEmpty(originalList)) return result;

        for (A element : originalList) {
            result += functionToApply.call(element);
        }

        return result;
    }

    /**
     * Returns the first element if the list is not null and have more than 0 elements, else returns null
     *
     * @param list
     * @param <A>
     * @return
     */
    @Nullable
    public static <A> A first(List<A> list) {
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * Returns a new list with the given elements at the beginning of the list
     *
     * @param originalList
     * @param elements
     * @param <A>
     * @return Returns a new list with the given elements at the beginning of the list
     */
    @SafeVarargs
    public static <A> List<A> unshift(List<A> originalList, A... elements) {
        ArrayList<A> newList = new ArrayList<>();
        for (A element : elements) {
            newList.add(element);
        }
        newList.addAll(originalList);

        return newList;
    }

    /**
     * Merges the lists into one
     *
     * @param lists
     * @param <A>
     * @return
     */
    @SafeVarargs
    public static <A> List<A> merge(List<A>... lists) {
        ArrayList<A> result = new ArrayList<>();

        if (lists.length == 0) return result;

        for (List<A> list : lists) {
            result.addAll(list);
        }

        return result;
    }

    private static <A> boolean checkIfListIsNullOrEmpty(Collection<A> originalList) {
        return originalList == null || originalList.isEmpty();
    }
}
