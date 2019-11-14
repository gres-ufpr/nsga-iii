package ufpr.gres.nsgaiii;

import java.util.Arrays;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.problem.multiobjective.dtlz.DTLZ1;
import org.uma.jmetal.problem.multiobjective.zdt.ZDT3;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;

import ufpr.gres.nsgaiii.builder.NSGAIIIBuilder;

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
        
        for (DoubleSolution sol : population) {

            for (int i = 0; i < sol.getNumberOfObjectives(); i++) {
                
                System.out.print(sol.getObjective(i));

                if (i + 1 != sol.getNumberOfObjectives()) {
                    System.out.print(";");
                }
            }
            
            System.out.println();
        }
    }
}
