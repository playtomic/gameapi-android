package com.playtomic.android;

public interface NewsletterSubscribeHandler {
    void onSuccess(PResponse response);
    void onFailure(PResponse response);
}
