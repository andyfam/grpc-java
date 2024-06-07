# Grpc java
A demo project show how to use Grpc in java.

## Requirements
- Java 1.8+
- Maven 3.0+

## project structure
inorder to reuse the code and improve the clearness of the project, we divide the project to three modules:
- stub
- server
- client

## stub module
include the proto files
### Maven artifacts
```
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-netty</artifactId>
    <version>1.62.2</version>
</dependency>
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-protobuf</artifactId>
    <version>1.62.2</version>
</dependency>
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-stub</artifactId>
    <version>1.62.2</version>
</dependency>
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>javax.annotation-api</artifactId>
    <version>1.3.2</version>
</dependency>
```
### maven plugins
inorder to produce the stub code from proto files, you need to the plugin as follows:
```
<plugin>
    <groupId>org.xolstice.maven.plugins</groupId>
    <artifactId>protobuf-maven-plugin</artifactId>
    <version>0.6.1</version>
    <configuration>
        <protocArtifact>
            com.google.protobuf:protoc:3.3.0:exe:${os.detected.classifier}
        </protocArtifact>
        <pluginId>grpc-java</pluginId>
        <pluginArtifact>
            io.grpc:protoc-gen-grpc-java:${grpc.version}:exe:${os.detected.classifier}
        </pluginArtifact>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>compile</goal>
                <goal>compile-custom</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
also need to add the <code>extension</code> for generates various useful platform-dependent project properties, like <code>${os.detected.classifier}</code>
```
<extension>
    <groupId>kr.motd.maven</groupId>
    <artifactId>os-maven-plugin</artifactId>
    <version>1.6.1</version>
</extension>
```
### usage
run command: <code>mvn compile</code> to generate stub code.

## Server module
implement the services, dependant the stub module
```
<dependency>
    <groupId>com.yufeng</groupId>
    <artifactId>grpc-java-stub</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Maven artifacts
in order to run the unit test, we need to add the following dependence:
```
<!-- https://mvnrepository.com/artifact/junit/junit -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>

<!-- https://mvnrepository.com/artifact/io.grpc/grpc-inprocess -->
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-inprocess</artifactId>
    <version>1.62.2</version>
    <scope>test</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/io.grpc/grpc-testing -->
<dependency>
    <groupId>io.grpc</groupId>
    <artifactId>grpc-testing</artifactId>
    <version>1.62.2</version>
    <scope>test</scope>
</dependency>
```

### maven plugin
in order to generate reports, we add the following plugin:
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.2.5</version>
</plugin>
```
### usage
run <code>java com.yufeng.grpcjavaserver.GrpcServer</code> to start the server.

## Client module
consume the services provided by the server module, dependant the stub module
```
<dependency>
    <groupId>com.yufeng</groupId>
    <artifactId>grpc-java-stub</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
### usage
run <code>java com.yufeng.grpcjavaclient.GrpcClient</code> to demonstrate regular grpc communication.

run <code>java com.yufeng.grpcjavaclient.StockClient</code> to demonstrate stream grpc communication.