package per.owisho.learn.extend;

public class Demo {
    class Parent {}

    class Sub extends Parent{}

    public static void main(String[] args) {
        System.out.println(Parent.class.isAssignableFrom(Sub.class));
    }
}
