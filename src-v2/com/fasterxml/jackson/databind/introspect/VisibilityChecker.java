/*
 * Decompiled with CFR 0_110.
 */
package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public interface VisibilityChecker<T extends VisibilityChecker<T>> {
    public boolean isCreatorVisible(AnnotatedMember var1);

    public boolean isCreatorVisible(Member var1);

    public boolean isFieldVisible(AnnotatedField var1);

    public boolean isFieldVisible(Field var1);

    public boolean isGetterVisible(AnnotatedMethod var1);

    public boolean isGetterVisible(Method var1);

    public boolean isIsGetterVisible(AnnotatedMethod var1);

    public boolean isIsGetterVisible(Method var1);

    public boolean isSetterVisible(AnnotatedMethod var1);

    public boolean isSetterVisible(Method var1);

    public T with(JsonAutoDetect.Visibility var1);

    public T with(JsonAutoDetect var1);

    public T withCreatorVisibility(JsonAutoDetect.Visibility var1);

    public T withFieldVisibility(JsonAutoDetect.Visibility var1);

    public T withGetterVisibility(JsonAutoDetect.Visibility var1);

    public T withIsGetterVisibility(JsonAutoDetect.Visibility var1);

    public T withSetterVisibility(JsonAutoDetect.Visibility var1);

    public T withVisibility(PropertyAccessor var1, JsonAutoDetect.Visibility var2);

    @JsonAutoDetect(creatorVisibility=JsonAutoDetect.Visibility.ANY, fieldVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY, getterVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY, isGetterVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY, setterVisibility=JsonAutoDetect.Visibility.ANY)
    public static class Std
    implements VisibilityChecker<Std>,
    Serializable {
        protected static final Std DEFAULT = new Std((JsonAutoDetect)Std.class.getAnnotation(JsonAutoDetect.class));
        private static final long serialVersionUID = -7073939237187922755L;
        protected final JsonAutoDetect.Visibility _creatorMinLevel;
        protected final JsonAutoDetect.Visibility _fieldMinLevel;
        protected final JsonAutoDetect.Visibility _getterMinLevel;
        protected final JsonAutoDetect.Visibility _isGetterMinLevel;
        protected final JsonAutoDetect.Visibility _setterMinLevel;

        public Std(JsonAutoDetect.Visibility visibility) {
            if (visibility == JsonAutoDetect.Visibility.DEFAULT) {
                this._getterMinLevel = Std.DEFAULT._getterMinLevel;
                this._isGetterMinLevel = Std.DEFAULT._isGetterMinLevel;
                this._setterMinLevel = Std.DEFAULT._setterMinLevel;
                this._creatorMinLevel = Std.DEFAULT._creatorMinLevel;
                this._fieldMinLevel = Std.DEFAULT._fieldMinLevel;
                return;
            }
            this._getterMinLevel = visibility;
            this._isGetterMinLevel = visibility;
            this._setterMinLevel = visibility;
            this._creatorMinLevel = visibility;
            this._fieldMinLevel = visibility;
        }

        public Std(JsonAutoDetect.Visibility visibility, JsonAutoDetect.Visibility visibility2, JsonAutoDetect.Visibility visibility3, JsonAutoDetect.Visibility visibility4, JsonAutoDetect.Visibility visibility5) {
            this._getterMinLevel = visibility;
            this._isGetterMinLevel = visibility2;
            this._setterMinLevel = visibility3;
            this._creatorMinLevel = visibility4;
            this._fieldMinLevel = visibility5;
        }

        public Std(JsonAutoDetect jsonAutoDetect) {
            this._getterMinLevel = jsonAutoDetect.getterVisibility();
            this._isGetterMinLevel = jsonAutoDetect.isGetterVisibility();
            this._setterMinLevel = jsonAutoDetect.setterVisibility();
            this._creatorMinLevel = jsonAutoDetect.creatorVisibility();
            this._fieldMinLevel = jsonAutoDetect.fieldVisibility();
        }

        public static Std defaultInstance() {
            return DEFAULT;
        }

        @Override
        public boolean isCreatorVisible(AnnotatedMember annotatedMember) {
            return this.isCreatorVisible(annotatedMember.getMember());
        }

        @Override
        public boolean isCreatorVisible(Member member) {
            return this._creatorMinLevel.isVisible(member);
        }

        @Override
        public boolean isFieldVisible(AnnotatedField annotatedField) {
            return this.isFieldVisible((Field)annotatedField.getAnnotated());
        }

        @Override
        public boolean isFieldVisible(Field field) {
            return this._fieldMinLevel.isVisible(field);
        }

        @Override
        public boolean isGetterVisible(AnnotatedMethod annotatedMethod) {
            return this.isGetterVisible((Method)annotatedMethod.getAnnotated());
        }

        @Override
        public boolean isGetterVisible(Method method) {
            return this._getterMinLevel.isVisible(method);
        }

        @Override
        public boolean isIsGetterVisible(AnnotatedMethod annotatedMethod) {
            return this.isIsGetterVisible((Method)annotatedMethod.getAnnotated());
        }

        @Override
        public boolean isIsGetterVisible(Method method) {
            return this._isGetterMinLevel.isVisible(method);
        }

        @Override
        public boolean isSetterVisible(AnnotatedMethod annotatedMethod) {
            return this.isSetterVisible((Method)annotatedMethod.getAnnotated());
        }

        @Override
        public boolean isSetterVisible(Method method) {
            return this._setterMinLevel.isVisible(method);
        }

        public String toString() {
            return "[Visibility:" + " getter: " + (Object)((Object)this._getterMinLevel) + ", isGetter: " + (Object)((Object)this._isGetterMinLevel) + ", setter: " + (Object)((Object)this._setterMinLevel) + ", creator: " + (Object)((Object)this._creatorMinLevel) + ", field: " + (Object)((Object)this._fieldMinLevel) + "]";
        }

        @Override
        public Std with(JsonAutoDetect.Visibility visibility) {
            if (visibility == JsonAutoDetect.Visibility.DEFAULT) {
                return DEFAULT;
            }
            return new Std(visibility);
        }

        @Override
        public Std with(JsonAutoDetect jsonAutoDetect) {
            Std std;
            Std std2 = std = this;
            if (jsonAutoDetect != null) {
                std2 = std.withGetterVisibility(jsonAutoDetect.getterVisibility()).withIsGetterVisibility(jsonAutoDetect.isGetterVisibility()).withSetterVisibility(jsonAutoDetect.setterVisibility()).withCreatorVisibility(jsonAutoDetect.creatorVisibility()).withFieldVisibility(jsonAutoDetect.fieldVisibility());
            }
            return std2;
        }

        @Override
        public Std withCreatorVisibility(JsonAutoDetect.Visibility visibility) {
            JsonAutoDetect.Visibility visibility2 = visibility;
            if (visibility == JsonAutoDetect.Visibility.DEFAULT) {
                visibility2 = Std.DEFAULT._creatorMinLevel;
            }
            if (this._creatorMinLevel == visibility2) {
                return this;
            }
            return new Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, visibility2, this._fieldMinLevel);
        }

        @Override
        public Std withFieldVisibility(JsonAutoDetect.Visibility visibility) {
            JsonAutoDetect.Visibility visibility2 = visibility;
            if (visibility == JsonAutoDetect.Visibility.DEFAULT) {
                visibility2 = Std.DEFAULT._fieldMinLevel;
            }
            if (this._fieldMinLevel == visibility2) {
                return this;
            }
            return new Std(this._getterMinLevel, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, visibility2);
        }

        @Override
        public Std withGetterVisibility(JsonAutoDetect.Visibility visibility) {
            JsonAutoDetect.Visibility visibility2 = visibility;
            if (visibility == JsonAutoDetect.Visibility.DEFAULT) {
                visibility2 = Std.DEFAULT._getterMinLevel;
            }
            if (this._getterMinLevel == visibility2) {
                return this;
            }
            return new Std(visibility2, this._isGetterMinLevel, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
        }

        @Override
        public Std withIsGetterVisibility(JsonAutoDetect.Visibility visibility) {
            JsonAutoDetect.Visibility visibility2 = visibility;
            if (visibility == JsonAutoDetect.Visibility.DEFAULT) {
                visibility2 = Std.DEFAULT._isGetterMinLevel;
            }
            if (this._isGetterMinLevel == visibility2) {
                return this;
            }
            return new Std(this._getterMinLevel, visibility2, this._setterMinLevel, this._creatorMinLevel, this._fieldMinLevel);
        }

        @Override
        public Std withSetterVisibility(JsonAutoDetect.Visibility visibility) {
            JsonAutoDetect.Visibility visibility2 = visibility;
            if (visibility == JsonAutoDetect.Visibility.DEFAULT) {
                visibility2 = Std.DEFAULT._setterMinLevel;
            }
            if (this._setterMinLevel == visibility2) {
                return this;
            }
            return new Std(this._getterMinLevel, this._isGetterMinLevel, visibility2, this._creatorMinLevel, this._fieldMinLevel);
        }

        @Override
        public Std withVisibility(PropertyAccessor propertyAccessor, JsonAutoDetect.Visibility visibility) {
            switch (.$SwitchMap$com$fasterxml$jackson$annotation$PropertyAccessor[propertyAccessor.ordinal()]) {
                default: {
                    return this;
                }
                case 1: {
                    return this.withGetterVisibility(visibility);
                }
                case 2: {
                    return this.withSetterVisibility(visibility);
                }
                case 3: {
                    return this.withCreatorVisibility(visibility);
                }
                case 4: {
                    return this.withFieldVisibility(visibility);
                }
                case 5: {
                    return this.withIsGetterVisibility(visibility);
                }
                case 6: 
            }
            return this.with(visibility);
        }
    }

}

