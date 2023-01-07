package per.owisho.learn.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;

/**
 * TODO
 * 未成功获取参数化类型的实际参数类型
 */
public class GenericInterfaceDemo {

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        ArrayList<String> list = new ArrayList<String>();

        //获取对象类型
        Class cls = list.getClass();

        //输出类型的名称
        System.out.println("typeName:" + cls.getTypeName());

        //获取类型的参数变量
        TypeVariable[] variables = cls.getTypeParameters();
        for (TypeVariable variable : variables) {
            System.out.println("variable name:" + variable.getName());
            System.out.println("variable typeName:" + variable.getTypeName());
            System.out.println("variable class:" + variable.getClass());
        }

        //获取类型的父类型
        Type sptype = cls.getGenericSuperclass();
        if (sptype instanceof ParameterizedType) {
            Type[] subTypes = ((ParameterizedType) sptype).getActualTypeArguments();
            for (Type subType : subTypes) {
                System.out.println("realType:" + subType.getTypeName());
            }
        }

        //获取类型的接口类型
        Type[] types = cls.getGenericInterfaces();
        System.out.println("内容长度：" + types.length);
        for (Type type : types) {
            if (type instanceof ParameterizedType) {

                System.out.println("rawType:" + ((ParameterizedType) type).getRawType());
                System.out.println("ownerType:" + ((ParameterizedType) type).getOwnerType());

                Type[] subTypes = ((ParameterizedType) type).getActualTypeArguments();
                for (Type subType : subTypes) {
                    System.out.println("toString:" + subType.toString());
                    System.out.println("typeClass:" + subType.getClass());
                    System.out.println("ActualTypes:" + subType.getTypeName());
                }

            }
            System.out.println("type:" + type);
        }
    }

}
