# katharsis-examples-hippo
katharsis-examples for Hippo CMS, using [katharsis-servlet](https://github.com/katharsis-project/katharsis-servlet) in [katharsis-project](https://github.com/katharsis-project).


# Running locally

This project uses the Maven Cargo plugin to run all the web applications locally in Tomcat.
From the project root folder, execute:

```
    mvn clean verify
    mvn -P cargo.run
```

After your project is set up, access the CMS at http://localhost:8080/cms and the site at http://localhost:8080/site.
Logs are located in target/tomcat7x/logs

# How to test JSON API resources

This project provides two JSON API resources as an example: 'projects' and 'tasks'.

To retrieve all the project resources, make a request like the following:

```
    curl -v http://localhost:8080/site/api/projects/
```

If you want to do full-text search on project resources, you can pass a filter parameter named 'q' like the following:

```
    curl -v -G http://localhost:8080/site/api/projects/ --data-urlencode "filter={\"q\":\"box\"}"
```

The above example will return only project resources which has "box" string in its content.

You can retrieve single project resource by requesting a URL like the following:

```
    curl -v http://localhost:8080/site/api/projects/75625baf-b47c-4ec5-b3e1-58889a1a8110
```

You can also retrieve all the tasks with URL like this:

```
    curl -v http://localhost:8080/site/api/tasks
```

You can retrieve one task with URL like this:

```
    curl -v http://localhost:8080/site/api/tasks/61481e5d-db34-4518-af6a-942e4a33c587
```

The single task request URL would not include its associated project resource data by default.
To include associated projects, you can add include parameter like the following:

```
    curl -v -G http://localhost:8080/site/api/tasks/61481e5d-db34-4518-af6a-942e4a33c587 --data-urlencode "include=[\"projects\"]"
```
