package me.fertiz.reward.utils;

public class NumberUtils {
    
    //say whaaaaaaaaaaaaaaaaaaaaaaaat?!!?!?

    public static boolean isNumber(String text) {
        return text.chars().allMatch(Character::isDigit);
    }
}
