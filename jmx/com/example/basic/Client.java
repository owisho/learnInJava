package com.example.basic;

import javax.management.Attribute;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class Client {
    public static void main(String[] args) {
        try {
            echo("\nCreate an RMI connector client and connect it to the RMI connector server");
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/server");
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

            ClientListener listener = new ClientListener();

            echo("\nGet an MBeanServerConnection");
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
            waitForEnterPressed();

            echo("\nDomains:");
            String domains[] = mbsc.getDomains();
            for (int i = 0; i < domains.length; i++) {
                echo("\tDomain[" + i + "] = " + domains[i]);
            }
            waitForEnterPressed();

            String domain = mbsc.getDefaultDomain();

            ObjectName stdMBeanName = new ObjectName(domain + ":type=SimpleStandard,name=2");
            echo("\nCreate SimpleStandard MBean...");
            mbsc.createMBean("SimpleStandard", stdMBeanName, null, null);
            waitForEnterPressed();

            ObjectName dynMBeanName = new ObjectName(domain + ":type=SimpleDynamic,name=2");
            echo("\nCreate SimpleDynamic MBean...");
            mbsc.createMBean("SimpleDynamic", dynMBeanName, null, null);
            waitForEnterPressed();

            echo("\nMBean count = " + mbsc.getMBeanCount());

            echo("\nQuery MBeanServer MBeans:");
            Set names = mbsc.queryNames(null, null);
            for (Iterator i = names.iterator(); i.hasNext(); ) {
                echo("\tObjectName = " + i.next());
            }
            waitForEnterPressed();

            echo("\n>>> Perform operations on SimpleStandard MBean <<<");
            echo("\nState = " + mbsc.getAttribute(stdMBeanName, "State"));
            mbsc.setAttribute(stdMBeanName, new Attribute("State", "changed state"));
            SimpleStandardMBean proxy = JMX.newMBeanProxy(mbsc, stdMBeanName, SimpleStandardMBean.class, true);
            echo("\nState = " + proxy.getState());
            echo("\nAdd notification listener...");
            mbsc.addNotificationListener(stdMBeanName, listener, null, null);
            echo("\nInvoke reset() in SimpleStandard MBean...");
            mbsc.invoke(stdMBeanName, "reset", null, null);
            echo("\nWaiting for notification...");
            sleep(2000);
            echo("\nRemove notification Listener...");
            mbsc.removeNotificationListener(stdMBeanName, listener);
            echo("\nUnregister SimpleStandard MBean...");
            mbsc.unregisterMBean(stdMBeanName);
            waitForEnterPressed();

            echo("\n>>> Perform operations on SimpleDynamic MBean <<<");
            echo("\nState = " + mbsc.getAttribute(dynMBeanName, "State"));
            mbsc.setAttribute(dynMBeanName, new Attribute("State", "changed state"));
            echo("\nState = " + mbsc.getAttribute(dynMBeanName, "State"));
            echo("\nAdd notification listener...");
            mbsc.addNotificationListener(dynMBeanName, listener, null, null);
            echo("\nInvoke reset() in SimpleDynamic MBean...");
            mbsc.invoke(dynMBeanName, "reset", null, null);
            echo("\nWaiting for notification...");
            sleep(2000);
            echo("\nRemove notification listener...");
            mbsc.removeNotificationListener(dynMBeanName, listener);
            echo("\nUnregister SimpleDynamic MBean...");
            mbsc.unregisterMBean(dynMBeanName);
            waitForEnterPressed();

            echo("\nClose the connection to the server");
            jmxc.close();
            echo("\nBye! Bye!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void waitForEnterPressed() {
        try {
            echo("\nPress <Enter> to continue...");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
