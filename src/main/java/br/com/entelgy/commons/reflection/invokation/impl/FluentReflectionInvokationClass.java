package br.com.entelgy.commons.reflection.invokation.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import com.liferay.portal.kernel.util.ReflectionUtil;

import br.com.entelgy.commons.reflection.FluentReflectionException;
import br.com.entelgy.commons.reflection.invokation.FluentReflectionInvokation;

@SuppressWarnings("rawtypes")
public class FluentReflectionInvokationClass implements FluentReflectionInvokation {

    private final Class<?> target;

    public FluentReflectionInvokationClass(final Class<?> target) {
        this.target = target;
    }

    @Override
    public Object invoke(final String methodName, final Object... args) {

        Class<?>[] parameterTypes = ReflectionUtil.getParameterTypes(args);

        Method method;
        try {

            method = ReflectionUtils.findMethod(target, methodName, parameterTypes);
            return method.invoke(target, args);

        } catch (Exception e) {
            throw new FluentReflectionException(e);
        }
    }

    @Override
    public Object invoke(String methodName, Class klass, Object[] args) {

        Method method;
        try {

            method = ReflectionUtils.findMethod(target, methodName, klass);
            return method.invoke(target, args);

        } catch (Exception e) {
            throw new FluentReflectionException(e);
        }

    }

    @Override
    public Object setField(final String fieldName, final Object... args) {
        Field field;
        try {

            field = ReflectionUtils.findField(target, fieldName);

            return field.get(target);

        } catch (Exception e) {
            throw new FluentReflectionException(e);
        }
    }

}
