package com.squareup.picasso;

public interface Callback {

    public static class EmptyCallback implements Callback {
        public void onSuccess() {
        }

        public void onError(Exception e) {
        }
    }

    void onError(Exception exception);

    void onSuccess();
}
