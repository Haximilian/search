package com.maximilian.app;

import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

public class ContentRepository {
    private final IndexWriter indexWriter;

    public ContentRepository(
            final IndexWriter indexWriter) {
        this.indexWriter = indexWriter;
    }

    public void index(
            Bookmark bookmark,
            Optional<String> optionalContent) {
                if (!optionalContent.isPresent()) {
                    return;
                }
                String content = optionalContent.get();

                Document document = new Document();
                // Field

                    Field pathField = new StringField("path", bookmark.getUrl().toString(), Field.Store.YES);
      document.add(pathField);
      
      document.add(new TextField("contents", new StringReader(content)));

    //   System.out.println(bookmark.getUrl().toString());
    //   System.out.println(content);

                try {
                    indexWriter.updateDocument(new Term("path", bookmark.getUrl().toString()), document);
                } catch (IOException e) {
                    e.printStackTrace();
                }
    }
}
