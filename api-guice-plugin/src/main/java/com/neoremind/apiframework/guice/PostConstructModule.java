package com.neoremind.apiframework.guice;

import com.google.inject.AbstractModule;
import com.google.inject.ProvisionException;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.google.inject.matcher.Matchers.any;
import static java.lang.String.format;

/**
 * Guice module to invoke {@link PostConstruct}.
 */
public final class PostConstructModule extends AbstractModule implements TypeListener {

    private static final Class<PostConstruct> POSTCONSTRUCT_CLASS = PostConstruct.class;
    private static final String JAVA_PACKAGE = "java";

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure() {
        bindListener(any(), this);
    }

    /**
     * {@inheritDoc}
     */
    public final <i> void hear(final TypeLiteral<i> type, final TypeEncounter<i> encounter) {
        hear(type.getRawType(), encounter);
    }

    private <i> void hear(Class type, TypeEncounter<i> encounter) {
        if (type == null || type.getPackage().getName().startsWith(JAVA_PACKAGE)) {
            return;
        }

        for (Method method : type.getDeclaredMethods()) {
            if (method.isAnnotationPresent(POSTCONSTRUCT_CLASS)) {
                final int numOfMethodParams = method.getParameterTypes().length;

                if (numOfMethodParams != 0) {
                    encounter.addError("@%s annotated methods can't accept any arguments. Method %s in %s has %s " +
                                    "parameters",
                            POSTCONSTRUCT_CLASS.getName(), method, type.getName(), numOfMethodParams);
                }

                hear(method, encounter);
            }
        }

        hear(type.getSuperclass(), encounter);
    }

    private <I> void hear(final Method method, final TypeEncounter<I> encounter) {
        encounter.register(new InjectionListener<I>() {
            public void afterInjection(I injectee) {
                try {
                    method.invoke(injectee);
                } catch (IllegalArgumentException e) {
                    throw new ProvisionException(
                            format("Method @%s %s requires arguments", POSTCONSTRUCT_CLASS.getName(), method), e);
                } catch (IllegalAccessException e) {
                    throw new ProvisionException(format("Impossible to access to @%s %s on %s",
                            POSTCONSTRUCT_CLASS.getName(), method, injectee), e);
                } catch (InvocationTargetException e) {
                    throw new ProvisionException(format("An error occurred while invoking @%s %s on %s",
                            POSTCONSTRUCT_CLASS.getName(), method, injectee), e.getTargetException());
                }
            }
        });
    }
}
