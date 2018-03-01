package br.com.entelgy.commons.reflection.invokation;

@SuppressWarnings("rawtypes")
public interface FluentReflectionInvokation {
    
    Object invoke(final String string, final Object... args);
    
    Object setField(final String string, final Object... args);
    
    Object invoke(final String string, final Class klass, final Object ... args);

}
