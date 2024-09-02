package com.maximilian.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BookmarksRepository {
    private static final String BOOKMARKS_FILE_PATH = "../bookmarks.html";   
    private static final String BOOKMARKS_FILE_PATH_2 = "../bookmarks.csv";   

    private Stream<Optional<Bookmark>> getBookmarkStreamCSV() throws IOException {
        File file = new File(BOOKMARKS_FILE_PATH_2);
        FileReader fileReader = new FileReader(file);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        return bufferedReader
            .lines()
            .map(href -> {
                Optional<URL> URL = Optional.empty();
                try {
                    URL = Optional.of(new URL(href));
                } catch (MalformedURLException e) {
                    System.out.println(e);
                }

                return URL.map(Bookmark::new);
            });

    }

    // specific to fire fox
    public Stream<Optional<Bookmark>> getBookmarkStream() throws IOException {
        final File bookmarkFile = new File(BOOKMARKS_FILE_PATH);
        final Document bookmarkDocument = Jsoup.parse(bookmarkFile);


        return Stream.concat(getBookmarkStreamCSV(), bookmarkDocument
                .select("a[href]")
                .stream()
                .map((Element element) -> {
                    String href = element.attr("href");

                    Optional<URL> URL = Optional.empty();
                    try {
                        URL = Optional.of(new URL(href));
                    } catch (MalformedURLException e) {
                        System.out.println(e);
                    }

                    return URL.map(Bookmark::new);
                }));
    }
}
