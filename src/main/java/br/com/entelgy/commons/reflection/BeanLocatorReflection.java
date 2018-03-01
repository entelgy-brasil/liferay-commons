package br.com.entelgy.commons.reflection;

import br.com.entelgy.commons.reflection.invokation.FluentReflectionInvokation;

public interface BeanLocatorReflection {

    FluentReflectionInvokation on(final String className);
    
}
