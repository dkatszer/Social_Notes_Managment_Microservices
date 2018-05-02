package pl.dkatszer.inz.apigateway.zuul.filters;

/**
 * Created by doka on 2017-12-16.
 */
public enum FilterType {
    PRE,
    ROUTE,
    POST,
    ERROR;

    public String value(){
        return name().toLowerCase();
    }
}
