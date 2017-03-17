当下列任意一种情况第一次发生时，类或接口类型T将在紧靠此时刻之前被初始化：</br>
T是类，并且创建了T的实例</br>
T是类，并且T声明的static方法被调用</br>
T声明的static域被赋值</br>
T声明的static域被使用，并且该域不是常量变量</br>
T是顶层类，并且在词法上嵌套在T内的assert语句被执行</br>

```
class Super{
  static {System.out.print("Super");}
}
class One{
  static {System.out.print("One ")}
}
class Two extends Super{
  static {System.out.print("Two")}
}
class Test{
  public static void main(String[] args){
    One o = null;
    Two t = new Two();
    System.out.println((Object) o == (Object) t);
  }
}
```

</br>
这个程序会产生下面的输出：</br>
Super Two false</br>
类One永远不会被初始化，因为他没有实际使用，因此也就永远不会被连接。类Two只有在其超类Super被初始化之后才会被初始化。</br>

```
interface I{
  int i=1,ii=Test.out("ii",2);
}
interface J extends I{
  int j=Test.out("j",3),jj=Test.out("jj",4);
}
interface K extends J{
  int k = Test.out("k",5);
}
class Test{
  public static void main(String[] args){
    System.out.println(J.i);
    System.out.println(K.j);
  }
  static int out(String s,int i){
    System.out.println(s+"="+i);
    return i;
  }
}
```
</br>
J.i引用的域是一个常量变量；因此，它不会导致I被初始化</br>
K.j引用的与实际上是在接口J中声明的，他不是常量变量；他会导致J接口的域被初始化，但是超接口I中的域以及接口K中的域都不会被初始化。</br>
