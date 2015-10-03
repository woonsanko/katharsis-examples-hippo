# katharsis-examples-hippo
katharsis-examples for Hippo CMS


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

To retrieve all the projects, make a request like the following:

    - curl http://localhost:8080/site/api/projects/

If you want to do full-text search on projects, you can pass a filter parameter named 'q' like the following:

    - curl http://localhost:8080/site/api/projects/?filter={"q":"box"}

The above example will return projects only having "box" string.

You can also retrieve all the tasks with http://localhost:8080/site/api/tasks,
and you can retrieve one task by requesting something like http://localhost:8080/site/api/tasks/61481e5d-db34-4518-af6a-942e4a33c587.

The single task request URL would not include its associated project data.
To include associated projects, you can add include parameter like the following:
- http://localhost:8080/site/api/tasks/61481e5d-db34-4518-af6a-942e4a33c587?include=["projects"]
