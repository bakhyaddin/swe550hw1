package main;
import java.util.*;

/**
* @author Yavuz Koroglu
* @updated Bahyeddin Nuri
* @param currentAverage
* @param populationSize
*/

public class RunningAverage {

    public Double getDecimals(Double value){
        try{
            return Double.parseDouble(String.format("%.03f", value));
        }catch(Exception e){
            return null;
        }
    }

    public List<Double> getImmutableList(List<Double> list){
        try{
            list = List.of(list.toArray(new Double[]{}));
        }catch (Exception e){
            return null;
        }

        try{
            list.add(22.22);
        }catch (Exception e) {
            return list;
        }
        return list;
    }

    private Double currentAverage;
    private Integer populationSize;

    /**
    * Default Constructor.
    */
    public RunningAverage(){
        this.currentAverage = 0.0;
        /** @fulfilles 1.1 */
        this.populationSize = 0;
    }

    /**
    * Explicit Value Constructor.
    * @param lastAverage
    * @param lastPopulationSize
    */
    public RunningAverage(double lastAverage, int lastPopulationSize){
        if(lastPopulationSize < 0){
            String msg = new String("lastPopulationSize cannot be less than 0");
            /** @fulfilles 1.3 */
            throw new RuntimeException(msg);
        }
//        System.out.println(lastAverage); 4.9985
        this.currentAverage = getDecimals(lastAverage);
//        System.out.println(this.currentAverage); 4.999
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
    * Removes and adds elements to the population and returns the new average.
    * @param populationList
    * @return currentAverage
    */
    public Double removeAndAddElements(List<Double> populationList, Operations operation){
        /** @fulfilles 1.2.b */
        populationList = getImmutableList(populationList);

        /** @fulfilles 1.2.d */
        if(populationList == null){
            return this.currentAverage;
        }

        if (populationList.size() == 0) {
            return this.currentAverage;
        }

        double sum = this.currentAverage * this.populationSize;
        for (double element : populationList) {
            if(operation == Operations.ADD){
                sum += element;
                this.populationSize++;
            }else{
                sum -= element;
                this.populationSize--;
            }
        }
        /** @fulfilles 1.4 */
        if(this.populationSize < 0){
            throw new RuntimeException("population size cannot be negative");
        }
        this.currentAverage = getDecimals(sum / this.populationSize);
        /** @fulfilles 1.2.c */
        return this.currentAverage;
    }

    /**
    * Combines two running averages and returns a new running average
    * @param RunningAverage1
    * @param RunningAverage2
    * @return combinedAverage
    */
    static public RunningAverage combine(final RunningAverage avg1, final RunningAverage avg2){
        /** @fulfilles 1.5 */
        return new RunningAverage((avg1.getCurrentAverage() * avg1.getPopulationSize() + avg2.getCurrentAverage() * avg2.getPopulationSize())
            / (avg1.getPopulationSize() + avg2.getPopulationSize()),
        avg1.getPopulationSize() + avg2.getPopulationSize()); 
    }
}