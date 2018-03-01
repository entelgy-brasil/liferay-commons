package br.com.entelgy.commons.reflection;

import com.liferay.portal.kernel.bean.BeanLocator;

import br.com.entelgy.commons.reflection.invokation.FluentReflectionInvokation;
import br.com.entelgy.commons.reflection.invokation.impl.FluentReflectionInvokationClass;
import br.com.entelgy.commons.reflection.invokation.impl.FluentReflectionInvokationObject;

public class FluentReflection {
	
	private FluentReflection() {
		
	}
    
    public static BeanLocatorReflection within(final BeanLocator b) {
        return new BeanLocatorReflection() {

            @Override
            public FluentReflectionInvokation on(final String beanName) {
                Object bean = b.locate(beanName);
                return new FluentReflectionInvokationObject(bean);
            }

        };
    }

    public static FluentReflectionInvokation on(final Class<?> clazz) {
        return new FluentReflectionInvokationClass(clazz);
    }
    
    public static FluentReflectionInvokation on(final Object object) {
        return new FluentReflectionInvokationObject (object);
    }


}