import java.lang.reflect.Constructor;
import java.util.Iterator;
import javax.management.*;

public class SimpleDynamic extends NotificationBroadcasterSupport implements DynamicMBean {
    public SimpleDynamic() {
        buildDynamicMBeanInfo();
    }

    public Object getAttribute(String attribute_name) throws AttributeNotFoundException,
            MBeanException, ReflectionException {
        if (attribute_name == null) {
            throw new RuntimeOperationsException(
                    new IllegalArgumentException("Attribute name cannot be null"),
                    "Cannot invoke a getter of " + dClassName +
                            " with null attribute name");
        }

        if (attribute_name.equals("State")) {
            return getState();
        }

        if (attribute_name.equals("NbChanges")) {
            return getNbChanges();
        }
        throw new AttributeNotFoundException("Cannot find " + attribute_name + " attribute in "
                + dClassName);
    }

    public void setAttribute(Attribute attribute) throws AttributeNotFoundException,
            InvalidAttributeValueException, MBeanException, ReflectionException {
        if (attribute == null) {
            throw new RuntimeOperationsException(
                    new IllegalArgumentException("Attribute cannot be null"),
                    "Cannot invoke a setter of " + dClassName + " with null attribute");
        }

        String name = attribute.getName();
        Object value = attribute.getValue();
        if (name == null) {
            throw new RuntimeOperationsException(
                    new IllegalArgumentException("Attribute name cannot be null"),
                    "Cannot invoke the setter of " + dClassName + " with null attribute name");
        }

        if (name.equals("State")) {
            if (value == null) {
                try {
                    setState(null);
                } catch (Exception e) {
                    throw new InvalidAttributeValueException("Cannot set attribute" + name + " to null");
                }
            } else {
                try {
                    if (Class.forName("java.lang.String")
                            .isAssignableFrom(value.getClass())) {
                        setState((String) value);
                    } else {
                        throw new InvalidAttributeValueException(
                                "Cannot set attribute " + name + " to a " +
                                        value.getClass().getName() +
                                        " object, String expected");
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } else if (name.equals("NbChanges")) {
            throw new AttributeNotFoundException(
                    "Cannot set attribute " + name + " because it is read-only");
        } else {
            throw new AttributeNotFoundException("Attribute " + name + " not found in " +
                    this.getClass().getName());
        }
    }

    public AttributeList getAttributes(String[] attributeNames) {
        if (attributeNames == null) {
            throw new RuntimeOperationsException(
                    new IllegalArgumentException("attributeNames() cannot be null"),
                    "Cannot invoke a getter of " + dClassName);
        }
        AttributeList resultList = new AttributeList();

        if (attributeNames.length == 0)
            return resultList;

        for (int i = 0; i < attributeNames.length; i++) {
            try {
                Object value = getAttribute(attributeNames[i]);
                resultList.add(new Attribute(attributeNames[i], value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public AttributeList setAttributes(AttributeList attributes) {
        if (attributes == null) {
            throw new RuntimeOperationsException(
                    new IllegalArgumentException("AttributeList attributes cannot be null"),
                    "Cannot invoke a setter of " + dClassName);
        }
        AttributeList resultList = new AttributeList();
        if (attributes.isEmpty()) {
            return resultList;
        }

        for (Iterator i = attributes.iterator(); i.hasNext(); ) {
            Attribute attr = (Attribute) i.next();
            try {
                setAttribute(attr);
                String name = attr.getName();
                Object value = getAttribute(name);
                resultList.add(new Attribute(name, value));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    public Object invoke(String operationName, Object[] param, String[] signature) throws
            MBeanException, ReflectionException {
        if (operationName == null) {
            throw new RuntimeOperationsException(
                    new IllegalArgumentException("Operation name cannot be null"),
                    "Cannot invoke a null operation in " + dClassName);
        }
        if (operationName.equals("reset")) {
            reset();
            return null;
        } else {
            throw new ReflectionException(
                    new NoSuchMethodException(operationName),
                    "Cannot find the operation " + operationName +
                            " in " + dClassName);
        }
    }

    public MBeanInfo getMBeanInfo() {
        return dMBeanInfo;
    }

    public String getState() {
        return state;
    }

    public void setState(String s) {
        state = s;
        nbChanges++;
    }

    public Integer getNbChanges() {
        return nbChanges;
    }

    public void reset() {
        AttributeChangeNotification acn = new AttributeChangeNotification(
                this, 0, 0, "NbChanges reset", "NbChanges", "Integer",
                nbChanges, 0);
        state = "initial state";
        nbChanges = 0;
        nbResets++;
        sendNotification(acn);
    }

    public Integer getNbResets() {
        return nbResets;
    }

    private void buildDynamicMBeanInfo() {
        dAttributes[0] = new MBeanAttributeInfo("State", "java.lang.String",
                "State string.", true, true, false);
        dAttributes[1] = new MBeanAttributeInfo("NbChanges", "java.lang.Integer",
                "Number of times the State string has been changed.",
                true, false, false);
        Constructor[] constructors = this.getClass().getConstructors();
        dConstructors[0] =
                new MBeanConstructorInfo("Constructs a SimpleDynamic object",
                        constructors[0]);

        MBeanParameterInfo[] params = null;
        dOperations[0] =
                new MBeanOperationInfo("reset", "reset State and NbChanges " +
                        "attribute to their initial values",
                        params, "void", MBeanOperationInfo.ACTION);

        dNotifications[0] =
                new MBeanNotificationInfo(
                        new String[]{AttributeChangeNotification.ATTRIBUTE_CHANGE},
                        AttributeChangeNotification.class.getName(),
                        "This notification is emitted when the reset() method is called");

        dMBeanInfo = new MBeanInfo(dClassName, dDescription, dAttributes, dConstructors,
                dOperations, dNotifications);
    }

    private String state = "initial state";
    private int nbChanges = 0;
    private int nbResets = 0;

    private String dClassName = this.getClass().getName();
    private String dDescription = "Simple implementation of a dynamic Bean.";

    private MBeanAttributeInfo[] dAttributes = new MBeanAttributeInfo[2];
    private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
    private MBeanNotificationInfo[] dNotifications = new MBeanNotificationInfo[1];
    private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[1];
    private MBeanInfo dMBeanInfo = null;
}
