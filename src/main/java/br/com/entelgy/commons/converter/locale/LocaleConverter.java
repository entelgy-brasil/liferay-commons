package br.com.entelgy.commons.converter.locale;

import java.util.Locale;

/**
 * Created by andre on 14/12/16.
 */
public interface LocaleConverter<I, O> {
    O convert(I entity, Locale locale);
}
