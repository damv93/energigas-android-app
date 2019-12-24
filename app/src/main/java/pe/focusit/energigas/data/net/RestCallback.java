package pe.focusit.energigas.data.net;

public interface RestCallback<T> {

    void onSuccess(T data);

    void onError(Exception exception);

}
