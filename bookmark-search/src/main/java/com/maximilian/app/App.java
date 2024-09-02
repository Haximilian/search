package com.maximilian.app;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

// todo: latency mask on request
// todo: avoid oom with thread pool
// todo: sneaky throws
// todo: guice
// IndexWriter is thread safe!

public class App {
    private static final String INDEX_DIRECTORY_PATH = "../index";

    public static void main(String[] args) {
        Path indexDirectoryPath = Paths.get(INDEX_DIRECTORY_PATH);

        try {
            Directory dir = FSDirectory.open(indexDirectoryPath);
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

            iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

            IndexWriter indexWriter = new IndexWriter(dir, iwc);

            ContentRepository contentRepository = new ContentRepository(indexWriter);

            BookmarksRepository bookmarks = new BookmarksRepository();
            Stream<Optional<Bookmark>> bookmarkStream = bookmarks.getBookmarkStream();

            Stream<Optional<String>> contentStream = bookmarkStream
                    .map((Optional<Bookmark> bookmark) -> 
                        bookmark.flatMap((Bookmark b) -> {
                            Optional<String> content = b.fetchContent();

                            contentRepository.index(b, content);
                            
                            return content;
                        })
                    );

                    // apparently map won't run unless you do a for each...
                    // makes sense...
                    // it's lazy
            contentStream
            .forEach(c -> c.map(t -> {
                // System.out.println(t); 
                return t;}));

                // you need this otherwise ... it won't flush the new index...
            indexWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}