package br.com.entelgy.commons.reflection.invokation.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import com.liferay.portal.kernel.util.ReflectionUtil;

import br.com.entelgy.commons.reflection.FluentReflectionException;
import br.com.entelgy.commons.reflection.invokation.FluentReflectionInvokation;

@SuppressWarnings("rawtypes")
public class FluentReflectionInvokationObject implements FluentReflectionInvokation {

    private final Object object;

    public FluentReflectionInvokationObject(final Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(final String methodName, final Object... args) {

        Class<?>[] parameterTypes = ReflectionUtil.getParameterTypes(args);

        Method method;
        try {

            method = ReflectionUtils.findMethod(object.getClass(), methodName, parameterTypes);
            return method.invoke(object, args);

        } catch (Exception e) {
            throw new FluentReflectionException(e);
        }
    }

    @Override
    public Object invoke(String methodName, Class klass, Object[] args) {

        Method method;
        try {

            method = ReflectionUtils.findMethod(object.getClass(), methodName, klass);
            return method.invoke(object, args);

        } catch (Exception e) {
            throw new FluentReflectionException(e);
        }

    }

    @Override
    public Object setField(final String fieldName, final Object... args) {
        Field field;
        try {

            field = ReflectionUtils.findField(object.getClass(), fieldName);
            field.setAccessible(true);
            return field.get(object);

        } catch (Exception e) {
            throw new FluentReflectionException(e);
        }
    }

}