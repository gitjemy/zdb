package com.a7md.zdb.properties;

import java.math.BigDecimal;

public class WritableProperty<E, V> extends Property<E, V> {

    private final Writer<E, V> writer;
    Class<V> assignedClass = null;

    public WritableProperty(String title, Func<E, V> reader, Writer<E, V> writer) {
        super(title, reader);
        this.writer = writer;
    }

    public void setValue(E e, V v) {
        writer.set(e, v);
    }

    public void setValueFromString(E e, String token) throws Exception {
        boolean written = false;
        try {
            writer.set(e, token == null ? (V) "" : (V) token);
            written = true;
        } catch (Exception ignored) {
        }

        if (!written) {
            try {
                writer.set(e, token == null ? (V) new Integer(0) : (V) Integer.valueOf(token));
                written = true;
            } catch (Exception ignored) {

            }
        }
        if (!written) {
            try {
                writer.set(e, token == null ? (V) BigDecimal.ZERO : (V) new BigDecimal(token));
                written = true;
            } catch (Exception ignored) {

            }
        }
        if (!written) {
            throw new Exception("لا يمكن تسجيل تحويل القيمة" + "\n" + token + "\n" + getTitle());
        }
    }


    public void validate(V v) throws Exception {

    }
}
