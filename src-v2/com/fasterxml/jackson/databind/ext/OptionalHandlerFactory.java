/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.ext;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.ser.Serializers;
import java.io.Serializable;

public class OptionalHandlerFactory
implements Serializable {
    private static final String CLASS_NAME_DOM_DOCUMENT = "org.w3c.dom.Node";
    private static final String CLASS_NAME_DOM_NODE = "org.w3c.dom.Node";
    private static final String DESERIALIZERS_FOR_JAVAX_XML = "com.fasterxml.jackson.databind.ext.CoreXMLDeserializers";
    private static final String DESERIALIZER_FOR_DOM_DOCUMENT = "com.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer";
    private static final String DESERIALIZER_FOR_DOM_NODE = "com.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer";
    private static final String PACKAGE_PREFIX_JAVAX_XML = "javax.xml.";
    private static final String SERIALIZERS_FOR_JAVAX_XML = "com.fasterxml.jackson.databind.ext.CoreXMLSerializers";
    private static final String SERIALIZER_FOR_DOM_NODE = "com.fasterxml.jackson.databind.ext.DOMSerializer";
    public static final OptionalHandlerFactory instance = new OptionalHandlerFactory();
    private static final long serialVersionUID = 1;

    protected OptionalHandlerFactory() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean doesImplement(Class<?> class_, String string2) {
        while (class_ != null) {
            if (class_.getName().equals(string2) || this.hasInterface(class_, string2)) {
                return true;
            }
            class_ = class_.getSuperclass();
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasInterface(Class<?> arrclass, String string2) {
        int n2;
        arrclass = arrclass.getInterfaces();
        int n3 = arrclass.length;
        for (n2 = 0; n2 < n3; ++n2) {
            if (arrclass[n2].getName().equals(string2)) return true;
            {
                continue;
            }
        }
        n3 = arrclass.length;
        n2 = 0;
        while (n2 < n3) {
            if (this.hasInterface(arrclass[n2], string2)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasInterfaceStartingWith(Class<?> arrclass, String string2) {
        int n2;
        arrclass = arrclass.getInterfaces();
        int n3 = arrclass.length;
        for (n2 = 0; n2 < n3; ++n2) {
            if (arrclass[n2].getName().startsWith(string2)) return true;
            {
                continue;
            }
        }
        n3 = arrclass.length;
        n2 = 0;
        while (n2 < n3) {
            if (this.hasInterfaceStartingWith(arrclass[n2], string2)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasSupertypeStartingWith(Class<?> class_, String string2) {
        for (Class class_2 = class_.getSuperclass(); class_2 != null; class_2 = class_2.getSuperclass()) {
            if (class_2.getName().startsWith(string2)) return true;
            {
                continue;
            }
        }
        while (class_ != null) {
            if (this.hasInterfaceStartingWith(class_, string2)) {
                return true;
            }
            class_ = class_.getSuperclass();
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Object instantiate(String string2) {
        try {
            return Class.forName(string2).newInstance();
        }
        catch (Exception var1_3) {
            do {
                return null;
                break;
            } while (true);
        }
        catch (LinkageError var1_4) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonDeserializer<?> findDeserializer(JavaType javaType, DeserializationConfig deserializationConfig, BeanDescription beanDescription) throws JsonMappingException {
        Object object = javaType.getRawClass();
        if (object.getName().startsWith("javax.xml.") || this.hasSupertypeStartingWith(object, "javax.xml.")) {
            object = this.instantiate("com.fasterxml.jackson.databind.ext.CoreXMLDeserializers");
            if (object == null) return null;
            return ((Deserializers)object).findBeanDeserializer(javaType, deserializationConfig, beanDescription);
        }
        if (this.doesImplement(object, "org.w3c.dom.Node")) {
            return (JsonDeserializer)this.instantiate("com.fasterxml.jackson.databind.ext.DOMDeserializer$DocumentDeserializer");
        }
        if (this.doesImplement(object, "org.w3c.dom.Node")) return (JsonDeserializer)this.instantiate("com.fasterxml.jackson.databind.ext.DOMDeserializer$NodeDeserializer");
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public JsonSerializer<?> findSerializer(SerializationConfig serializationConfig, JavaType javaType, BeanDescription beanDescription) {
        Object object = javaType.getRawClass();
        if (object.getName().startsWith("javax.xml.") || this.hasSupertypeStartingWith(object, "javax.xml.")) {
            object = this.instantiate("com.fasterxml.jackson.databind.ext.CoreXMLSerializers");
            if (object == null) return null;
            return ((Serializers)object).findSerializer(serializationConfig, javaType, beanDescription);
        }
        if (this.doesImplement(object, "org.w3c.dom.Node")) return (JsonSerializer)this.instantiate("com.fasterxml.jackson.databind.ext.DOMSerializer");
        return null;
    }
}

