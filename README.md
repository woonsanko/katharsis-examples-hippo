# katharsis-examples-hippo
[JSON API](http://jsonapi.org/) implementation example project for Hippo CMS,
using [katharsis-framework](https://github.com/katharsis-project/katharsis-framework)
of [katharsis-project](https://github.com/katharsis-project).


# Running locally

This project uses the Maven Cargo plugin to run all the web applications locally in Tomcat.
From the project root folder, execute:

```
    mvn clean verify
    mvn -P cargo.run
```

After your project is set up, access the CMS at http://localhost:8080/cms and the site at http://localhost:8080/site.
Logs are located in target/tomcat7x/logs

# Browse/View/Edit Example Content

By default, there are some project and task resource content in Hippo CMS.
Visit http://localhost:8080/cms/ (you can login by author/author, editor/editor or admin/admin).
Select "Content" perspective. You will be able to see example project documents (in katharsisexampleshippo/projects/) and
task documents (in katharsisexampleshippo/tasks/).

Note: If you change something in those documents and publish them, the following JSON API requests will get new data on the fly.

# How to test JSON API resources

This project provides two JSON API resources as an example: 'projects' and 'tasks'.

To retrieve all the project resources, make a request like the following:

```
    curl -v http://localhost:8080/site/api/projects/
```

If you want to do full-text search on project resources, you can pass a filter parameter named 'q' like the following:

```
    curl -v -G http://localhost:8080/site/api/projects/ --data-urlencode "filter\[projects\]\[$contains\]=box"
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

The relationship can be retrieved as well (to-many relationship to projects from tasks in this example):

```
    curl -v http://localhost:8080/site/api/tasks/61481e5d-db34-4518-af6a-942e4a33c587/relationships/projects
```

The single task request URL would not include its associated project resource data by default.
To include associated projects, you can add include parameter like the following:

```
    curl -v -G http://localhost:8080/site/api/tasks/61481e5d-db34-4518-af6a-942e4a33c587 --data-urlencode "include\[tasks\]=projects"
```

