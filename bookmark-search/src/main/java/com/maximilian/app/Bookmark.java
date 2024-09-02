package com.maximilian.app;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import org.jsoup.Jsoup;

public class Bookmark {
    final static int REQUEST_TIMEOUT = 1000;

    private URL Url;

    public Bookmark(URL Url) {
        this.Url = Url;
    }

    public URL getUrl() {
        return Url;
    }

    public Optional<String> fetchContent() {
        System.out.print(this.Url.toString());
        try {
            Optional<String> content = Optional.of(Jsoup
                    .parse(Url, REQUEST_TIMEOUT)
                    .text());

            System.out.println("✅");

            return content;

        } catch (IOException e) {
            System.out.println("❌");
            System.out.println(e);
        }

        return Optional.empty();
    }
}
