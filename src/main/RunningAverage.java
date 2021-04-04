import java.util.*;
/**
* @author Yavuz Koroglu
* @editor Bahyeddin Nuri
*/
public class RunningAverage{
    private Double currentAverage = new Double(0.0);
    private Integer populationSize = new Integer(0);

    /**
    * Default Constructor.
    */
    public RunningAverage(){
        this.currentAverage = 0.0;
        this.populationSize = 1;
    }

    /**
    * Explicit Value Constructor.
    * @param lastAverage
    * @param lastPopulationSize
    */
    public RunningAverage(double lastAverage, int lastPopulationSize){
        this.currentAverage = lastAverage;
        this.populationSize = lastPopulationSize;
    }

    /**
    * Copy Constructor.
    * @param RunningAverage
    */
    public RunningAverage(RunningAverage lastAverage){
        this.currentAverage = lastAverage.currentAverage;
        this.populationSize = lastAverage.populationSize;
    }

    /**
    * Getter for currentAverage
    * @return currentAverage
    */
    public Double getCurrentAverage(){
        return currentAverage;
    }

    /**
    * Getter for populationSize
    * @return populationSize
    */
    public Integer getPopulationSize(){
        return populationSize;
    }

    /**
    * Adds elements to the population and returns the new average.
    * @param addedPopulationList
    * @return currentAverage
    */
    public Double addElements(List<Double> addedPopulation){
        if (addedPopulation.size() == 0 || addedPopulation == null) {
            return this.currentAverage;
        }
        double sum = this.currentAverage * this.populationSize;
        for (double element : addedPopulation) {
            sum += element;
            this.populationSize++;

        }
        this.currentAverage = sum / this.populationSize;
        return this.currentAverage;
    }


    /**
    * Removes elements to the population and returns the new average.
    * @param removedPopulation
    * @return currentAverage
    */
    public Double removeElements(List<Double> removedPopulation){
        if (removedPopulation.size() == 0 || removedPopulation == null) {
            return 0.0;
        }
        double sum = this.currentAverage * this.populationSize;
        for (double element : removedPopulation) {
            sum -= element;
            this.populationSize--;
        }
        this.currentAverage = sum / this.populationSize;
        return this.currentAverage;
    }

    /**
    * Combines two running averages and returns a new running average
    * @param RunningAverage1
    * @param RunningAverage2
    * @return combinedAverage
    */
    static public RunningAverage combine(final RunningAverage avg1, final RunningAverage avg2){
        return new RunningAverage(avg1.getCurrentAverage() * avg1.getPopulationSize() + avg2.getCurrentAverage() * avg2.getPopulationSize() 
            / (avg1.getPopulationSize() + avg2.getPopulationSize()),
        avg1.getPopulationSize() + avg2.getPopulationSize()); 
    }
}