package per.owisho.learn.util;

import java.util.StringJoiner;

public class StringJoinerDemo {
    public static void main(String[] args) {
        StringJoiner joiner = new StringJoiner(",", "pre1", "suf1");
        StringJoiner joiner1 = new StringJoiner(";", "pre2", "suf2");
        joiner.add("1");
        joiner.add("1");
        joiner.add("1");
        joiner.add("1");
        joiner.add("1");
        joiner1.add("2");
        joiner1.add("2");
        joiner1.add("2");
        joiner1.add("2");
        joiner1.add("2");
        joiner.merge(joiner1);
        System.out.println(joiner.toString());
        System.out.println(joiner1.toString());
    }
}
