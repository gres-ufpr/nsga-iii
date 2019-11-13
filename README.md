# NSGA-III

The NSGA-III algorithm proposed by Deb and Jain (2013)

## Source Code

This project is based on https://github.com/yyxhdy/ManyEAs. Many thanks!

## How to install

This project uses GitHub as a Maven Repository. Then you have just add the following section to your repositories tag in pom.xml

```xml
<repository>
    <id>mvn-repo</id>
    <url>https://github.com/gres-ufpr/mvn-repo/raw/master/releases</url>
    <releases>
        <enabled>true</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```

Then add a dependency into tag of your pom.xml

```xml
<dependency>
	<groupId>gres.nsgaiii</groupId>
	<artifactId>nsga-iii</artifactId>
	<version>1.0.0</version>
</dependency>
```

## Original Paper

Deb, Kalyanmoy, and Himanshu Jain. "**An evolutionary many-objective optimization algorithm using reference-point-based nondominated sorting approach, part I: solving problems with box constraints.**" IEEE Transactions on Evolutionary Computation 18, no. 4 (2013): 577-601.
