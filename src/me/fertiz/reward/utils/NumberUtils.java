package me.fertiz.reward.utils;

public class NumberUtils {

    public static boolean isNumber(String text) {
        return text.chars().allMatch(Character::isDigit);
    }
}
