package com.playtomic.tests;

import com.playtomic.android.Newsletter;
import com.playtomic.android.NewsletterSubscribeHandler;
import com.playtomic.android.NewsletterSubscription;
import com.playtomic.android.PResponse;

public class PTestNewsletter extends PTest {

    public static void subscribe(final TestHandler callback) {

        final String section = "TestNewsletter.subscribe";

        final NewsletterSubscription options = new NewsletterSubscription();
        options.setEmail("invalid @email.com");

        Newsletter.subscribe(options, new NewsletterSubscribeHandler() {

            @Override
            public void onFailure(PResponse r) {
                assertFalse(section, "Request failed", r.getSuccess());
                assertEquals(section, "No errorcode", r.getErrorCode(), 602);

                options.setEmail("valid@testuri.com");
                options.setField("STRINGFIELD", "a value");

                Newsletter.subscribe(options, new NewsletterSubscribeHandler() {

                    @Override
                    public void onSuccess(PResponse r2) {
                        assertTrue(section + "#2", "Request succeeded", r2.getSuccess());
                        assertEquals(section + "#2", "No errorcode", r2.getErrorCode(), 0);
                        callback.done();
                    }
                    @Override
                    public void onFailure(PResponse response) {
                        fail(section, "Request failed with errorcode " + response.getErrorCode());
                        callback.done();
                    }
                });
            }

            @Override
            public void onSuccess(PResponse response) {
                fail(section, "Request failed with errorcode " + response.getErrorCode());
                callback.done();
            }
        });
    }
}