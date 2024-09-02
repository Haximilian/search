Run these commands inside of the root of the java project e.g. /Users/maximilian/Desktop/bookmarkSearch/bookmark-search 

You can display the classpath with this.

```
mvn dependency:build-classpath
```

You can build with this.

```
mvn package
```

```
unzip -p target/bookmark-search-1.0-SNAPSHOT.jar META-INF/MANIFEST.MF
```

You can search using this command. Run this at the root of the project e.g. /Users/maximilian/Desktop/bookmarkSearch/ 

```
java -classpath /Users/maximilian/Desktop/bookmarkSearch/query/lucene-analysis-common-9.11.1.jar:/Users/maximilian/Desktop/bookmarkSearch/query/lucene-core-9.11.1.jar:/Users/maximilian/Desktop/bookmarkSearch/query/lucene-demo-9.11.1.jar:/Users/maximilian/Desktop/bookmarkSearch/query/lucene-queryparser-9.11.1.jar org.apache.lucene.demo.SearchFiles
```
