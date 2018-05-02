package pl.dkatszer.inz.apigateway.security.model;

/**
 * Created by doka on 2017-12-28.
 */
public class JsonDTO<T>{
    public T value;

    public static <T> JsonDTO of(T any){
        JsonDTO wrapper = new JsonDTO();
        wrapper.value = any;
        return wrapper;
    }
}
