### 包说明
java security policy 相关学习内容
### 使用简例
java -cp security.jar -Djava.security.manager -Djava.security.policy=security.policy GetProps    
java -cp target/classes/ -Djava.security.manager -Djava.security.policy=security/policy/demoPolicy policy.GetProps     
policy 文件中的codeBase要对应真实执行class文件的目录    
自己整理的学习笔记：https://blog.csdn.net/owisho_java/article/details/129311580?spm=1001.2014.3001.5502   


git hub地址：https://github.com/owisho/learnInJava/tree/master/security    
官网教程：https://docs.oracle.com/javase/tutorial/security/index.html