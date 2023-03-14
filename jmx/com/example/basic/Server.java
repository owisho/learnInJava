import java.io.IOException;
import javax.management.Attribute;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class Server {
    public static void main(String[] args) {
        try {
            echo("\n>>> Create the MBean server");
            MBeanServer mbs = MBeanServerFactory.createMBeanServer();
            waitForEnterPressed();

            echo("\n>>> Get the MBean server's default domain");
            String domain = mbs.getDefaultDomain();
            echo("\t Default Domain = " + domain);
            waitForEnterPressed();

            String mbeanClassName = "SimpleStandard";
            String mbeanObjectNameStr =
                    domain + ":type=" + mbeanClassName + ",name=1";
            ObjectName mbeanObjectName =
                    createSimpleMBean(mbs, mbeanClassName, mbeanObjectNameStr);
            waitForEnterPressed();

            printMBeanInfo(mbs, mbeanObjectName, mbeanClassName);
            waitForEnterPressed();

            manageSimpleMBean(mbs, mbeanObjectName, mbeanClassName);
            waitForEnterPressed();

            mbeanClassName = "SimpleDynamic";
            mbeanObjectNameStr =
                    domain + ":type=" + mbeanClassName + ",name=1";
            mbeanObjectName =
                    createSimpleMBean(mbs, mbeanClassName, mbeanObjectNameStr);
            waitForEnterPressed();

            printMBeanInfo(mbs, mbeanObjectName, mbeanClassName);
            waitForEnterPressed();

            manageSimpleMBean(mbs, mbeanObjectName, mbeanClassName);
            waitForEnterPressed();

            JMXServiceURL url =
                    new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/server");
            JMXConnectorServer cs =
                    JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);

            echo("\nStart the RMI connector server");
            cs.start();
            echo("\nThe RMI connector server successfully started ");
            echo("and is ready to handle incoming connections");
            echo("\nStart the client on a different window and ");
            echo("press <Enter> once the client has finished");
            waitForEnterPressed();

            echo("\nStop the RMI connector server");
            cs.stop();
            System.out.println("\nBye! Bye!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ObjectName createSimpleMBean(MBeanServer mbs,
                                                String mbeanClassName, String mbeanObjectNameStr) {
        echo("\n>>> Create the " + mbeanClassName +
                " MBean within the MBeanServer");
        echo("ObjectName = " + mbeanObjectNameStr);
        try {
            ObjectName mbeanObjectName = ObjectName.getInstance(mbeanObjectNameStr);
            mbs.createMBean(mbeanClassName, mbeanObjectName);
            return mbeanObjectName;
        } catch (Exception e) {
            echo("!!! Could not create the " + mbeanClassName + " MBean !!!");
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private static void printMBeanInfo(MBeanServer mbs, ObjectName mbeanObjectName, String mbeanClassName) {
        MBeanInfo info = null;
        try {
            info = mbs.getMBeanInfo(mbeanObjectName);
        } catch (Exception e) {
            echo("!!! Could not get MBeanInfo object for" +
                    mbeanClassName + " !!!");
            e.printStackTrace();
            return;
        }

        MBeanAttributeInfo[] attrInfo = info.getAttributes();
        if (attrInfo.length > 0) {
            for (int i = 0; i < attrInfo.length; i++) {
                echo("**NAME:  " + attrInfo[i].getName());
                echo("  DESCR: " + attrInfo[i].getDescription());
                echo("  TYPE:  " + attrInfo[i].getType() + "READ: " + attrInfo[i].isReadable() + "WRITE: " + attrInfo[i].isWritable());
            }
        } else echo("**No attributes**");

        MBeanConstructorInfo[] constrInfo = info.getConstructors();
        for (int i = 0; i < constrInfo.length; i++) {
            echo("**Name:\t" + constrInfo[i].getName());
            echo("  DESCR:\t" + constrInfo[i].getDescription());
            echo("  PARAM:\t" + constrInfo[i].getSignature().length + " parameter(s)");
        }

        MBeanOperationInfo[] opInfo = info.getOperations();
        if (opInfo.length > 0) {
            for (int i = 0; i < opInfo.length; i++) {
                echo("**Name:\t" + opInfo[i].getName());
                echo("  DESCR:\t" + opInfo[i].getDescription());
                echo("  PARAM:\t" + opInfo[i].getSignature().length + " parameter(s)");
            }
        } else echo("**No operations**");

        MBeanNotificationInfo[] notifInfo = info.getNotifications();
        if (notifInfo.length > 0) {
            for (int i = 0; i < notifInfo.length; i++) {
                echo("**Name:\t" + notifInfo[i].getName());
                echo("  DESCR:\t" + notifInfo[i].getDescription());
                String notifTypes[] = notifInfo[i].getNotifTypes();
                for (int j = 0; j < notifTypes.length; j++) {
                    echo("  TYPE:\t" + notifTypes[j]);
                }
            }
        } else echo("**No notifications**");
    }

    private static void manageSimpleMBean(MBeanServer mbs, ObjectName mbeanObjectName, String mbeanClassName) {
        echo("\n>>> Manage the" + mbeanClassName + "MBean using its attributes ");
        echo(" and operations exposed for management");
        try {
            printSimpleAttributes(mbs, mbeanObjectName);

            echo("\n Setting State attribute to value \"new state\"");
            Attribute stateAttribute = new Attribute("state", "new state");
            mbs.setAttribute(mbeanObjectName, stateAttribute);

            printSimpleAttributes(mbs, mbeanObjectName);

            echo("\n Invoking reset operation...");
            mbs.invoke(mbeanObjectName, "reset", null, null);

            printSimpleAttributes(mbs, mbeanObjectName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printSimpleAttributes(MBeanServer mbs, ObjectName mbeanObjectName) {
        try {
            echo("\nGetting attribute values:");
            String state = (String) mbs.getAttribute(mbeanObjectName, "state");
            Integer NbChanges = (Integer) mbs.getAttribute(mbeanObjectName, "NbChanges");
            echo("\tState=\"" + state + "\"");
            echo("\tNbChanges=" + NbChanges);
        } catch (Exception e) {
            echo("!!!Could not read attributes!!!");
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
