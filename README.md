# Pipeline Utils

This is a shared Jenkins pipeline library that is loaded implicitly in our Jenkins instance. It allows any Jenkinsfile to make calls to methods that can do things like initiate a kaniko build or push an image with certain tags to our registry. It significantly reduces the code needed to craft a Jenkinsfile and allows easy changes to all repositories that use it by simply changing the library's implementation of its tasks. 

## Usage

There are a couple ways to call methods within files in the `vars` directory. If a method inside a file called `sayHello.groovy` is titled `call`, one can simply write the following in a script block in the Jenkinsfile to call the method

```
sayHello 'Joe'
or
sayHello('Joe')
```

A method can also have another name, which is the case with the `build()` method in the `kaniko.groovy` file. This method is simply accessed like this

```
kaniko.build()
```

These files (like kaniko.groovy) are considered instance variables to the Jenkinsfile, which is why they are named in camelcase and accessed like they are.

For more information about calling methods within the library, see the [Jenkins documentation](https://www.jenkins.io/doc/book/pipeline/shared-libraries/#accessing-steps).