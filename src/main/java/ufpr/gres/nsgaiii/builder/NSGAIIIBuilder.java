package ufpr.gres.nsgaiii.builder;

import java.util.List;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;

import ufpr.gres.nsgaiii.nsgaiii.NSGAIII;

/**
 * NSGA-III
 * 
 * @author Gian Fritsche &lt;gmfritsche@inf.ufpr.br&gt;
 * @param <S>
 */
public class NSGAIIIBuilder<S extends Solution<?>> implements AlgorithmBuilder<NSGAIII<S>> {

    private final Problem<S> problem;

    private int maxEvaluations;

    private boolean normalize;

    private int populationSize;

    private CrossoverOperator<S> crossover;

    private MutationOperator<S> mutation;

    private SelectionOperator<List<S>,S> selection;
    
    private String weightsFilename;

    private double[][] weights;
    
    public NSGAIIIBuilder(Problem<S> problem) {
        this.problem = problem;
    }

    public Problem<S> getProblem() {
        return this.problem;
    }

    public NSGAIIIBuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
        return this;
    }

    public NSGAIIIBuilder<S> setNormalize(boolean normalize) {
        this.normalize = normalize;
        return this;
    }

    public NSGAIIIBuilder<S> setCrossoverOperator(CrossoverOperator<S> crossover) {
        this.crossover = crossover;
        return this;
    }

    public NSGAIIIBuilder<S> setMutationOperator(MutationOperator<S> mutation) {
        this.mutation = mutation;
        return this;
    }

    public NSGAIIIBuilder<S> setSelectionOperator(SelectionOperator<List<S>,S> selection) {
        this.selection = selection;
        return this;
    }

    public NSGAIIIBuilder<S> setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }
    
    public NSGAIIIBuilder<S> setWeights(double[][] weights) {
        this.weights = weights;
        return this;
    }
    
    public NSGAIIIBuilder<S> setWeightsFilename(String weightsFilename) {
        this.weightsFilename = weightsFilename;
        return this;
    }

    @Override
    public NSGAIII<S> build() {
        return new NSGAIII<S>(this);
    }

    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    public boolean getNormalize() {
        return this.normalize;
    }

    public int getPopulationSize() {
        return this.populationSize;
    }

    public CrossoverOperator<S> getCrossover() {
        return this.crossover;
    }

    public MutationOperator<S> getMutation() {
        return this.mutation;
    }

    public SelectionOperator<List<S>,S> getSelection() {
        return this.selection;
    }
    
    public double[][] getWeights() {
        return this.weights;
    }
    
    public String getWeightsFilename() {
        return this.weightsFilename;
    }
}