/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethodMap;
import com.fasterxml.jackson.databind.introspect.AnnotationMap;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector;
import com.fasterxml.jackson.databind.introspect.MemberKey;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class AnnotatedClass
extends Annotated {
    private static final AnnotationMap[] NO_ANNOTATION_MAPS = new AnnotationMap[0];
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final Class<?> _class;
    protected AnnotationMap _classAnnotations;
    protected List<AnnotatedConstructor> _constructors;
    protected List<AnnotatedMethod> _creatorMethods;
    protected boolean _creatorsResolved = false;
    protected AnnotatedConstructor _defaultConstructor;
    protected List<AnnotatedField> _fields;
    protected AnnotatedMethodMap _memberMethods;
    protected final ClassIntrospector.MixInResolver _mixInResolver;
    protected final Class<?> _primaryMixIn;
    protected final List<Class<?>> _superTypes;

    /*
     * Enabled aggressive block sorting
     */
    private AnnotatedClass(Class<?> class_, List<Class<?>> list, AnnotationIntrospector annotationIntrospector, ClassIntrospector.MixInResolver mixInResolver, AnnotationMap annotationMap) {
        this._class = class_;
        this._superTypes = list;
        this._annotationIntrospector = annotationIntrospector;
        this._mixInResolver = mixInResolver;
        class_ = this._mixInResolver == null ? null : this._mixInResolver.findMixInClassFor(this._class);
        this._primaryMixIn = class_;
        this._classAnnotations = annotationMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _addAnnotationsIfNotPresent(AnnotatedMember annotatedMember, Annotation[] iterator) {
        if (iterator != null) {
            LinkedList<Annotation[]> linkedList = null;
            for (Iterator iterator2 : iterator) {
                if (this._isAnnotationBundle((Annotation)((Object)iterator2))) {
                    LinkedList<Annotation[]> linkedList2 = linkedList;
                    if (linkedList == null) {
                        linkedList2 = new LinkedList<Annotation[]>();
                    }
                    linkedList2.add(iterator2.annotationType().getDeclaredAnnotations());
                    linkedList = linkedList2;
                    continue;
                }
                annotatedMember.addIfNotPresent((Annotation)((Object)iterator2));
            }
            if (linkedList != null) {
                iterator = linkedList.iterator();
                while (iterator.hasNext()) {
                    this._addAnnotationsIfNotPresent(annotatedMember, (Annotation[])iterator.next());
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _addAnnotationsIfNotPresent(AnnotationMap annotationMap, Annotation[] iterator) {
        if (iterator != null) {
            LinkedList<Annotation[]> linkedList = null;
            for (Iterator iterator2 : iterator) {
                if (this._isAnnotationBundle((Annotation)((Object)iterator2))) {
                    LinkedList<Annotation[]> linkedList2 = linkedList;
                    if (linkedList == null) {
                        linkedList2 = new LinkedList<Annotation[]>();
                    }
                    linkedList2.add(iterator2.annotationType().getDeclaredAnnotations());
                    linkedList = linkedList2;
                    continue;
                }
                annotationMap.addIfNotPresent((Annotation)((Object)iterator2));
            }
            if (linkedList != null) {
                iterator = linkedList.iterator();
                while (iterator.hasNext()) {
                    this._addAnnotationsIfNotPresent(annotationMap, (Annotation[])iterator.next());
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void _addOrOverrideAnnotations(AnnotatedMember annotatedMember, Annotation[] iterator) {
        if (iterator != null) {
            LinkedList<Annotation[]> linkedList = null;
            for (Iterator iterator2 : iterator) {
                if (this._isAnnotationBundle((Annotation)((Object)iterator2))) {
                    LinkedList<Annotation[]> linkedList2 = linkedList;
                    if (linkedList == null) {
                        linkedList2 = new LinkedList<Annotation[]>();
                    }
                    linkedList2.add(iterator2.annotationType().getDeclaredAnnotations());
                    linkedList = linkedList2;
                    continue;
                }
                annotatedMember.addOrOverride((Annotation)((Object)iterator2));
            }
            if (linkedList != null) {
                iterator = linkedList.iterator();
                while (iterator.hasNext()) {
                    this._addOrOverrideAnnotations(annotatedMember, (Annotation[])iterator.next());
                }
            }
        }
    }

    private AnnotationMap _emptyAnnotationMap() {
        return new AnnotationMap();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private AnnotationMap[] _emptyAnnotationMaps(int n2) {
        if (n2 == 0) {
            return NO_ANNOTATION_MAPS;
        }
        AnnotationMap[] arrannotationMap = new AnnotationMap[n2];
        int n3 = 0;
        do {
            AnnotationMap[] arrannotationMap2 = arrannotationMap;
            if (n3 >= n2) return arrannotationMap2;
            arrannotationMap[n3] = this._emptyAnnotationMap();
            ++n3;
        } while (true);
    }

    private final boolean _isAnnotationBundle(Annotation annotation) {
        if (this._annotationIntrospector != null && this._annotationIntrospector.isAnnotationBundle(annotation)) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean _isIncludableField(Field field) {
        int n2;
        if (field.isSynthetic() || Modifier.isStatic(n2 = field.getModifiers()) || Modifier.isTransient(n2)) {
            return false;
        }
        return true;
    }

    public static AnnotatedClass construct(Class<?> class_, AnnotationIntrospector annotationIntrospector, ClassIntrospector.MixInResolver mixInResolver) {
        return new AnnotatedClass(class_, ClassUtil.findSuperTypes(class_, null), annotationIntrospector, mixInResolver, null);
    }

    public static AnnotatedClass constructWithoutSuperTypes(Class<?> class_, AnnotationIntrospector annotationIntrospector, ClassIntrospector.MixInResolver mixInResolver) {
        return new AnnotatedClass(class_, Collections.emptyList(), annotationIntrospector, mixInResolver, null);
    }

    private void resolveClassAnnotations() {
        this._classAnnotations = new AnnotationMap();
        if (this._annotationIntrospector != null) {
            if (this._primaryMixIn != null) {
                this._addClassMixIns(this._classAnnotations, this._class, this._primaryMixIn);
            }
            this._addAnnotationsIfNotPresent(this._classAnnotations, this._class.getDeclaredAnnotations());
            for (Class class_ : this._superTypes) {
                this._addClassMixIns(this._classAnnotations, class_);
                this._addAnnotationsIfNotPresent(this._classAnnotations, class_.getDeclaredAnnotations());
            }
            this._addClassMixIns(this._classAnnotations, Object.class);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void resolveCreators() {
        int n2;
        List list = null;
        Constructor<?>[] arrconstructor = this._class.getDeclaredConstructors();
        int n3 = arrconstructor.length;
        for (n2 = 0; n2 < n3; ++n2) {
            void var4_5;
            Constructor constructor = arrconstructor[n2];
            if (constructor.getParameterTypes().length == 0) {
                this._defaultConstructor = this._constructConstructor(constructor, true);
                continue;
            }
            List list2 = list;
            if (list == null) {
                ArrayList arrayList = new ArrayList(Math.max(10, arrconstructor.length));
            }
            var4_5.add(this._constructConstructor(constructor, false));
            list = var4_5;
        }
        this._constructors = list == null ? Collections.emptyList() : list;
        if (!(this._primaryMixIn == null || this._defaultConstructor == null && this._constructors.isEmpty())) {
            this._addConstructorMixIns(this._primaryMixIn);
        }
        if (this._annotationIntrospector != null) {
            if (this._defaultConstructor != null && this._annotationIntrospector.hasIgnoreMarker(this._defaultConstructor)) {
                this._defaultConstructor = null;
            }
            if (this._constructors != null) {
                n2 = this._constructors.size();
                while ((n3 = n2 - 1) >= 0) {
                    n2 = n3;
                    if (!this._annotationIntrospector.hasIgnoreMarker(this._constructors.get(n3))) continue;
                    this._constructors.remove(n3);
                    n2 = n3;
                }
            }
        }
        list = null;
        for (Constructor constructor : this._class.getDeclaredMethods()) {
            void var4_11;
            if (!Modifier.isStatic(constructor.getModifiers())) continue;
            List list3 = list;
            if (list == null) {
                ArrayList arrayList = new ArrayList(8);
            }
            var4_11.add(this._constructCreatorMethod((Method)((Object)constructor)));
            list = var4_11;
        }
        if (list == null) {
            this._creatorMethods = Collections.emptyList();
        } else {
            this._creatorMethods = list;
            if (this._primaryMixIn != null) {
                this._addFactoryMixIns(this._primaryMixIn);
            }
            if (this._annotationIntrospector != null) {
                n2 = this._creatorMethods.size();
                while ((n3 = n2 - 1) >= 0) {
                    n2 = n3;
                    if (!this._annotationIntrospector.hasIgnoreMarker(this._creatorMethods.get(n3))) continue;
                    this._creatorMethods.remove(n3);
                    n2 = n3;
                }
            }
        }
        this._creatorsResolved = true;
    }

    private void resolveFields() {
        Map<String, AnnotatedField> map = this._findFields(this._class, null);
        if (map == null || map.size() == 0) {
            this._fields = Collections.emptyList();
            return;
        }
        this._fields = new ArrayList<AnnotatedField>(map.size());
        this._fields.addAll(map.values());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void resolveMemberMethods() {
        Object object;
        this._memberMethods = new AnnotatedMethodMap();
        Object object2 = new AnnotatedMethodMap();
        this._addMemberMethods(this._class, this._memberMethods, this._primaryMixIn, (AnnotatedMethodMap)object2);
        for (Class class_ : this._superTypes) {
            object = this._mixInResolver == null ? null : this._mixInResolver.findMixInClassFor(class_);
            this._addMemberMethods(class_, this._memberMethods, object, (AnnotatedMethodMap)object2);
        }
        if (this._mixInResolver != null && (object = this._mixInResolver.findMixInClassFor(Object.class)) != null) {
            this._addMethodMixIns(this._class, this._memberMethods, object, (AnnotatedMethodMap)object2);
        }
        if (this._annotationIntrospector != null && !object2.isEmpty()) {
            object = object2.iterator();
            while (object.hasNext()) {
                object2 = (AnnotatedMethod)object.next();
                try {
                    Object object3 = Object.class.getDeclaredMethod(object2.getName(), object2.getRawParameterTypes());
                    if (object3 == null) continue;
                    object3 = this._constructMethod((Method)object3);
                    this._addMixOvers((Method)object2.getAnnotated(), (AnnotatedMethod)object3, false);
                    this._memberMethods.add((AnnotatedMethod)object3);
                }
                catch (Exception var2_2) {}
            }
        }
    }

    protected void _addClassMixIns(AnnotationMap annotationMap, Class<?> class_) {
        if (this._mixInResolver != null) {
            this._addClassMixIns(annotationMap, class_, this._mixInResolver.findMixInClassFor(class_));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addClassMixIns(AnnotationMap annotationMap, Class<?> iterator, Class<?> class_) {
        if (class_ != null) {
            this._addAnnotationsIfNotPresent(annotationMap, class_.getDeclaredAnnotations());
            iterator = ClassUtil.findSuperTypes(class_, iterator).iterator();
            while (iterator.hasNext()) {
                this._addAnnotationsIfNotPresent(annotationMap, iterator.next().getDeclaredAnnotations());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addConstructorMixIns(Class<?> arrmemberKey) {
        MemberKey[] arrmemberKey2 = null;
        int n2 = this._constructors == null ? 0 : this._constructors.size();
        Constructor<?>[] arrconstructor = arrmemberKey.getDeclaredConstructors();
        int n3 = arrconstructor.length;
        int n4 = 0;
        while (n4 < n3) {
            MemberKey[] arrmemberKey3;
            block7 : {
                Constructor constructor = arrconstructor[n4];
                if (constructor.getParameterTypes().length == 0) {
                    arrmemberKey3 = arrmemberKey2;
                    if (this._defaultConstructor != null) {
                        this._addMixOvers(constructor, this._defaultConstructor, false);
                        arrmemberKey3 = arrmemberKey2;
                    }
                } else {
                    int n5;
                    arrmemberKey = arrmemberKey2;
                    if (arrmemberKey2 == null) {
                        arrmemberKey2 = new MemberKey[n2];
                        n5 = 0;
                        do {
                            arrmemberKey = arrmemberKey2;
                            if (n5 >= n2) break;
                            arrmemberKey2[n5] = new MemberKey(this._constructors.get(n5).getAnnotated());
                            ++n5;
                        } while (true);
                    }
                    arrmemberKey2 = new MemberKey(constructor);
                    n5 = 0;
                    do {
                        arrmemberKey3 = arrmemberKey;
                        if (n5 >= n2) break block7;
                        if (arrmemberKey2.equals(arrmemberKey[n5])) break;
                        ++n5;
                    } while (true);
                    this._addMixOvers(constructor, this._constructors.get(n5), true);
                    arrmemberKey3 = arrmemberKey;
                }
            }
            ++n4;
            arrmemberKey2 = arrmemberKey3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addFactoryMixIns(Class<?> object) {
        Object object2 = null;
        int n2 = this._creatorMethods.size();
        Method[] arrmethod = object.getDeclaredMethods();
        int n3 = arrmethod.length;
        int n4 = 0;
        while (n4 < n3) {
            Object object3;
            block7 : {
                Method method = arrmethod[n4];
                if (!Modifier.isStatic(method.getModifiers())) {
                    object3 = object2;
                } else {
                    object3 = object2;
                    if (method.getParameterTypes().length != 0) {
                        int n5;
                        object = object2;
                        if (object2 == null) {
                            object2 = new MemberKey[n2];
                            n5 = 0;
                            do {
                                object = object2;
                                if (n5 >= n2) break;
                                object2[n5] = new MemberKey((Method)this._creatorMethods.get(n5).getAnnotated());
                                ++n5;
                            } while (true);
                        }
                        object2 = new MemberKey(method);
                        n5 = 0;
                        do {
                            object3 = object;
                            if (n5 >= n2) break block7;
                            if (object2.equals(object[n5])) break;
                            ++n5;
                        } while (true);
                        this._addMixOvers(method, this._creatorMethods.get(n5), true);
                        object3 = object;
                    }
                }
            }
            ++n4;
            object2 = object3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addFieldMixIns(Class<?> iterator, Class<?> arrfield, Map<String, AnnotatedField> map) {
        ArrayList arrayList = new ArrayList();
        arrayList.add((Field[])arrfield);
        ClassUtil.findSuperTypes(arrfield, iterator, arrayList);
        iterator = arrayList.iterator();
        block0 : while (iterator.hasNext()) {
            arrfield = iterator.next().getDeclaredFields();
            int n2 = arrfield.length;
            int n3 = 0;
            do {
                AnnotatedField annotatedField;
                if (n3 >= n2) continue block0;
                Field field = arrfield[n3];
                if (this._isIncludableField(field) && (annotatedField = map.get(field.getName())) != null) {
                    this._addOrOverrideAnnotations(annotatedField, field.getDeclaredAnnotations());
                }
                ++n3;
            } while (true);
            break;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addMemberMethods(Class<?> arrmethod, AnnotatedMethodMap annotatedMethodMap, Class<?> object, AnnotatedMethodMap annotatedMethodMap2) {
        if (object != null) {
            this._addMethodMixIns(arrmethod, annotatedMethodMap, object, annotatedMethodMap2);
        }
        if (arrmethod == null) {
            return;
        }
        arrmethod = arrmethod.getDeclaredMethods();
        int n2 = arrmethod.length;
        int n3 = 0;
        while (n3 < n2) {
            object = arrmethod[n3];
            if (this._isIncludableMemberMethod((Method)object)) {
                AnnotatedMethod annotatedMethod = annotatedMethodMap.find((Method)object);
                if (annotatedMethod == null) {
                    annotatedMethod = this._constructMethod((Method)object);
                    annotatedMethodMap.add(annotatedMethod);
                    if ((object = annotatedMethodMap2.remove((Method)object)) != null) {
                        this._addMixOvers((Method)object.getAnnotated(), annotatedMethod, false);
                    }
                } else {
                    this._addMixUnders((Method)object, annotatedMethod);
                    if (annotatedMethod.getDeclaringClass().isInterface() && !object.getDeclaringClass().isInterface()) {
                        annotatedMethodMap.add(annotatedMethod.withMethod((Method)object));
                    }
                }
            }
            ++n3;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void _addMethodMixIns(Class<?> iterator, AnnotatedMethodMap annotatedMethodMap, Class<?> arrmethod, AnnotatedMethodMap annotatedMethodMap2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add((Method[])arrmethod);
        ClassUtil.findSuperTypes(arrmethod, iterator, arrayList);
        iterator = arrayList.iterator();
        block0 : while (iterator.hasNext()) {
            arrmethod = iterator.next().getDeclaredMethods();
            int n2 = arrmethod.length;
            int n3 = 0;
            do {
                if (n3 >= n2) continue block0;
                Method method = arrmethod[n3];
                if (this._isIncludableMemberMethod(method)) {
                    AnnotatedMethod annotatedMethod = annotatedMethodMap.find(method);
                    if (annotatedMethod != null) {
                        this._addMixUnders(method, annotatedMethod);
                    } else {
                        annotatedMethodMap2.add(this._constructMethod(method));
                    }
                }
                ++n3;
            } while (true);
            break;
        }
        return;
    }

    protected void _addMixOvers(Constructor<?> arrannotation, AnnotatedConstructor annotatedConstructor, boolean bl2) {
        this._addOrOverrideAnnotations(annotatedConstructor, arrannotation.getDeclaredAnnotations());
        if (bl2) {
            arrannotation = arrannotation.getParameterAnnotations();
            int n2 = arrannotation.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                Annotation[] arrannotation2 = arrannotation[i2];
                int n3 = arrannotation2.length;
                for (int i3 = 0; i3 < n3; ++i3) {
                    annotatedConstructor.addOrOverrideParam(i2, arrannotation2[i3]);
                }
            }
        }
    }

    protected void _addMixOvers(Method arrannotation, AnnotatedMethod annotatedMethod, boolean bl2) {
        this._addOrOverrideAnnotations(annotatedMethod, arrannotation.getDeclaredAnnotations());
        if (bl2) {
            arrannotation = arrannotation.getParameterAnnotations();
            int n2 = arrannotation.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                Annotation[] arrannotation2 = arrannotation[i2];
                int n3 = arrannotation2.length;
                for (int i3 = 0; i3 < n3; ++i3) {
                    annotatedMethod.addOrOverrideParam(i2, arrannotation2[i3]);
                }
            }
        }
    }

    protected void _addMixUnders(Method method, AnnotatedMethod annotatedMethod) {
        this._addAnnotationsIfNotPresent(annotatedMethod, method.getDeclaredAnnotations());
    }

    protected AnnotationMap _collectRelevantAnnotations(Annotation[] arrannotation) {
        AnnotationMap annotationMap = new AnnotationMap();
        this._addAnnotationsIfNotPresent(annotationMap, arrannotation);
        return annotationMap;
    }

    protected AnnotationMap[] _collectRelevantAnnotations(Annotation[][] arrannotation) {
        int n2 = arrannotation.length;
        AnnotationMap[] arrannotationMap = new AnnotationMap[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            arrannotationMap[i2] = this._collectRelevantAnnotations(arrannotation[i2]);
        }
        return arrannotationMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected AnnotatedConstructor _constructConstructor(Constructor<?> constructor, boolean bl2) {
        if (this._annotationIntrospector == null) {
            return new AnnotatedConstructor(constructor, this._emptyAnnotationMap(), this._emptyAnnotationMaps(constructor.getParameterTypes().length));
        }
        if (bl2) {
            return new AnnotatedConstructor(constructor, this._collectRelevantAnnotations(constructor.getDeclaredAnnotations()), null);
        }
        Annotation[][] arrannotation = constructor.getParameterAnnotations();
        int n2 = constructor.getParameterTypes().length;
        AnnotationMap[] arrannotationMap = null;
        if (n2 != arrannotation.length) {
            Annotation[][] arrannotation2;
            AnnotationMap[] arrannotationMap2;
            Class class_ = constructor.getDeclaringClass();
            if (class_.isEnum() && n2 == arrannotation.length + 2) {
                arrannotation2 = new Annotation[arrannotation.length + 2][];
                System.arraycopy(arrannotation, 0, arrannotation2, 2, arrannotation.length);
                arrannotationMap2 = this._collectRelevantAnnotations(arrannotation2);
            } else {
                arrannotation2 = arrannotation;
                arrannotationMap2 = arrannotationMap;
                if (class_.isMemberClass()) {
                    arrannotation2 = arrannotation;
                    arrannotationMap2 = arrannotationMap;
                    if (n2 == arrannotation.length + 1) {
                        arrannotation2 = new Annotation[arrannotation.length + 1][];
                        System.arraycopy(arrannotation, 0, arrannotation2, 1, arrannotation.length);
                        arrannotationMap2 = this._collectRelevantAnnotations(arrannotation2);
                    }
                }
            }
            arrannotation = arrannotationMap2;
            if (arrannotationMap2 != null) return new AnnotatedConstructor(constructor, this._collectRelevantAnnotations(constructor.getDeclaredAnnotations()), (AnnotationMap[])arrannotation);
            {
                throw new IllegalStateException("Internal error: constructor for " + constructor.getDeclaringClass().getName() + " has mismatch: " + n2 + " parameters; " + arrannotation2.length + " sets of annotations");
            }
        } else {
            arrannotation = this._collectRelevantAnnotations(arrannotation);
        }
        return new AnnotatedConstructor(constructor, this._collectRelevantAnnotations(constructor.getDeclaredAnnotations()), (AnnotationMap[])arrannotation);
    }

    protected AnnotatedMethod _constructCreatorMethod(Method method) {
        if (this._annotationIntrospector == null) {
            return new AnnotatedMethod(method, this._emptyAnnotationMap(), this._emptyAnnotationMaps(method.getParameterTypes().length));
        }
        return new AnnotatedMethod(method, this._collectRelevantAnnotations(method.getDeclaredAnnotations()), this._collectRelevantAnnotations(method.getParameterAnnotations()));
    }

    protected AnnotatedField _constructField(Field field) {
        if (this._annotationIntrospector == null) {
            return new AnnotatedField(field, this._emptyAnnotationMap());
        }
        return new AnnotatedField(field, this._collectRelevantAnnotations(field.getDeclaredAnnotations()));
    }

    protected AnnotatedMethod _constructMethod(Method method) {
        if (this._annotationIntrospector == null) {
            return new AnnotatedMethod(method, this._emptyAnnotationMap(), null);
        }
        return new AnnotatedMethod(method, this._collectRelevantAnnotations(method.getDeclaredAnnotations()), null);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Map<String, AnnotatedField> _findFields(Class<?> class_, Map<String, AnnotatedField> map) {
        Class class_2 = class_.getSuperclass();
        Map<String, AnnotatedField> map2 = map;
        if (class_2 == null) return map2;
        map = this._findFields(class_2, map);
        for (Field field : class_.getDeclaredFields()) {
            if (!this._isIncludableField(field)) continue;
            map2 = map;
            if (map == null) {
                map2 = new LinkedHashMap<String, AnnotatedField>();
            }
            map2.put(field.getName(), this._constructField(field));
            map = map2;
        }
        map2 = map;
        if (this._mixInResolver == null) return map2;
        class_ = this._mixInResolver.findMixInClassFor(class_);
        map2 = map;
        if (class_ == null) return map2;
        this._addFieldMixIns(class_2, class_, map);
        return map;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean _isIncludableMemberMethod(Method method) {
        if (Modifier.isStatic(method.getModifiers()) || method.isSynthetic() || method.isBridge() || method.getParameterTypes().length > 2) {
            return false;
        }
        return true;
    }

    @Override
    public Iterable<Annotation> annotations() {
        if (this._classAnnotations == null) {
            this.resolveClassAnnotations();
        }
        return this._classAnnotations.annotations();
    }

    public Iterable<AnnotatedField> fields() {
        if (this._fields == null) {
            this.resolveFields();
        }
        return this._fields;
    }

    public AnnotatedMethod findMethod(String string2, Class<?>[] arrclass) {
        if (this._memberMethods == null) {
            this.resolveMemberMethods();
        }
        return this._memberMethods.find(string2, arrclass);
    }

    @Override
    protected AnnotationMap getAllAnnotations() {
        if (this._classAnnotations == null) {
            this.resolveClassAnnotations();
        }
        return this._classAnnotations;
    }

    @Override
    public Class<?> getAnnotated() {
        return this._class;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> class_) {
        if (this._classAnnotations == null) {
            this.resolveClassAnnotations();
        }
        return this._classAnnotations.get(class_);
    }

    public Annotations getAnnotations() {
        if (this._classAnnotations == null) {
            this.resolveClassAnnotations();
        }
        return this._classAnnotations;
    }

    public List<AnnotatedConstructor> getConstructors() {
        if (!this._creatorsResolved) {
            this.resolveCreators();
        }
        return this._constructors;
    }

    public AnnotatedConstructor getDefaultConstructor() {
        if (!this._creatorsResolved) {
            this.resolveCreators();
        }
        return this._defaultConstructor;
    }

    public int getFieldCount() {
        if (this._fields == null) {
            this.resolveFields();
        }
        return this._fields.size();
    }

    @Override
    public Type getGenericType() {
        return this._class;
    }

    public int getMemberMethodCount() {
        if (this._memberMethods == null) {
            this.resolveMemberMethods();
        }
        return this._memberMethods.size();
    }

    @Override
    public int getModifiers() {
        return this._class.getModifiers();
    }

    @Override
    public String getName() {
        return this._class.getName();
    }

    @Override
    public Class<?> getRawType() {
        return this._class;
    }

    public List<AnnotatedMethod> getStaticMethods() {
        if (!this._creatorsResolved) {
            this.resolveCreators();
        }
        return this._creatorMethods;
    }

    public boolean hasAnnotations() {
        if (this._classAnnotations == null) {
            this.resolveClassAnnotations();
        }
        if (this._classAnnotations.size() > 0) {
            return true;
        }
        return false;
    }

    public Iterable<AnnotatedMethod> memberMethods() {
        if (this._memberMethods == null) {
            this.resolveMemberMethods();
        }
        return this._memberMethods;
    }

    public String toString() {
        return "[AnnotedClass " + this._class.getName() + "]";
    }

    @Override
    public AnnotatedClass withAnnotations(AnnotationMap annotationMap) {
        return new AnnotatedClass(this._class, this._superTypes, this._annotationIntrospector, this._mixInResolver, annotationMap);
    }
}

