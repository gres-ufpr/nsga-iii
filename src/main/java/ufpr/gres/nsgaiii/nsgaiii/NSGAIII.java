package ufpr.gres.nsgaiii.nsgaiii;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.SolutionListUtils;
import org.uma.jmetal.util.solutionattribute.Ranking;
import org.uma.jmetal.util.solutionattribute.impl.DominanceRanking;

import ufpr.gres.nsgaiii.builder.NSGAIIIBuilder;
import ufpr.gres.nsgaiii.util.Niching;

/**
 * Based on the source distributed by Yuan Yuan
 * (https://github.com/yyxhdy/ManyEAs)  &lt;yyxhdy at gmail.com&gt; 
 *
 * @author Gian Fritsche &lt;gmfritsche at inf.ufpr.br&gt; 
 * @param <S>
 */
public class NSGAIII<S extends Solution<?>> implements Algorithm<List<S>> {

    private static final long serialVersionUID = 5929054104233726533L;

    protected int populationSize_;

    protected List<S> population_;
    List<S> offspringPopulation_;
    List<S> union_;

    int evaluations;

    CrossoverOperator<S> crossover_;
    MutationOperator<S> mutation_;
    SelectionOperator<List<S>, S> selection_;

    double[][] lambda_; // reference points

    boolean normalize_; // do normalization or not

    protected final Problem<S> problem_;

    protected int maxEvaluations;
    
    protected double[][] weights;
    
    protected String weightsFilename;

    public NSGAIII(NSGAIIIBuilder<S> builder) {

        problem_ = builder.getProblem();

        maxEvaluations = builder.getMaxEvaluations();

        normalize_ = builder.getNormalize();

        populationSize_ = builder.getPopulationSize();

        mutation_ = builder.getMutation();

        crossover_ = builder.getCrossover();

        selection_ = builder.getSelection();
        
        weights = builder.getWeights();
        
        weightsFilename = builder.getWeightsFilename();

    }

    protected void initializeUniformWeight() {
        
        if (weights != null) {
            lambda_ = weights;
            return;
        }
        
        lambda_ = new double[populationSize_][problem_.getNumberOfObjectives()];
        
        Path path = Paths.get(weightsFilename);
        
        try {
        
            List<String> lines = Files.readAllLines(path);
            
            for (int i = 0; i < lines.size(); i++) {

                String line = lines.get(i);

                String[] parts = line.split(";");
                
                for (int j = 0; j < parts.length; j++) {
                    lambda_[i][j] = new Double(parts[j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        evaluations = 0;

        initializeUniformWeight();

        if (populationSize_ % 2 != 0) {
            populationSize_ += 1;
        }

        initPopulation();
        evaluations += populationSize_;

        while (evaluations < maxEvaluations) {
            offspringPopulation_ = new ArrayList<>(populationSize_);
            for (int i = 0; i < (populationSize_ / 2); i++) {
                if (evaluations < maxEvaluations) {
                    // obtain parents

                    List<S> parents = new ArrayList<>();
                    parents.add(selection_.execute(population_));
                    parents.add(selection_.execute(population_));

                    List<S> offSpring = crossover_.execute(parents);

                    mutation_.execute(offSpring.get(0));
                    mutation_.execute(offSpring.get(1));

                    problem_.evaluate(offSpring.get(0));
                    problem_.evaluate(offSpring.get(1));
                    evaluations += 2;

                    offspringPopulation_.add(offSpring.get(0));
                    offspringPopulation_.add(offSpring.get(1));

                } // if
            } // for

            union_ = new ArrayList<>();
            union_.addAll(population_);
            union_.addAll(offspringPopulation_);

            // Ranking the union
            Ranking ranking = (new DominanceRanking()).computeRanking(union_);

            int remain = populationSize_;
            int index = 0;
            List<S> front;
            population_.clear();

            // Obtain the next front
            front = ranking.getSubfront(index);

            while ((remain > 0) && (remain >= front.size())) {

                for (int k = 0; k < front.size(); k++) {
                    population_.add(front.get(k));
                } // for

                // Decrement remain
                remain = remain - front.size();

                // Obtain the next front
                index++;
                if (remain > 0) {
                    front = ranking.getSubfront(index);
                } // if
            }

            if (remain > 0) { // front contains individuals to insert
                new Niching(population_, front, lambda_, remain, normalize_).execute();
            }

        }

    }

    void initPopulation() {

        population_ = new ArrayList<>(populationSize_);

        for (int i = 0; i < populationSize_; i++) {
            S newSolution = problem_.createSolution();
            problem_.evaluate(newSolution);
            population_.add(newSolution);
        }

    }

    @Override
    public List<S> getResult() {
        return SolutionListUtils.getNondominatedSolutions(population_);
    }

    @Override
    public String getName() {
        return "NSGAIII";
    }

    @Override
    public String getDescription() {
        return "\"unofficial\" implementation of NSGA-III";
    }

}
