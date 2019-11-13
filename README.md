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

## How to use

```java
public class Explore {

    public static void main(String[] args) {

        Problem<DoubleSolution> problem = new ZDT3(30);
        
        double crossoverProbability = 0.9 ;
        double crossoverDistributionIndex = 30.0 ;
        CrossoverOperator<DoubleSolution> crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex) ;

        double mutationProbability = 1.0 / problem.getNumberOfVariables() ;
        double mutationDistributionIndex = 20.0 ;
        MutationOperator<DoubleSolution> mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex) ;
        
        SelectionOperator<List<DoubleSolution>, DoubleSolution> selection = new BinaryTournamentSelection<DoubleSolution>();
        
        Algorithm<List<DoubleSolution>> algorithm = new NSGAIIIBuilder<>(problem)
                .setPopulationSize(128)
                .setMaxEvaluations(128 * 1000)
                .setSelectionOperator(selection)
                .setCrossoverOperator(crossover)
                .setMutationOperator(mutation)
                .setWeightsFilename("src/main/resources/weights/W2D_128.txt")
                .build() ;
        
        new AlgorithmRunner.Executor(algorithm).execute() ;
        
        List<DoubleSolution> population = algorithm.getResult() ;
    }
}
```

## For Developers
	
For generating a distributable version, run:
	
```sh
mvn deploy
```

## Original Paper

Deb, Kalyanmoy, and Himanshu Jain. "**An evolutionary many-objective optimization algorithm using reference-point-based nondominated sorting approach, part I: solving problems with box constraints.**" IEEE Transactions on Evolutionary Computation 18, no. 4 (2013): 577-601.

## Contributions

Feel free to fork this project, work on it and then make a pull request.

## Questions or Suggestions

Feel free to create <a href="https://github.com/gres-ufpr/nsga-iii/issues">issues</a> here as you need
