package com.algaworks.algafood_api.core.replicacaoDoSquiggly;

public class FilterContext {
    private static final ThreadLocal<String> fields = new ThreadLocal<>();

    public static void set(String value) { fields.set(value); }
    public static String get() { return fields.get(); }
}