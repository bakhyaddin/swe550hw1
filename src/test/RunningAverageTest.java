package test;
import main.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RunningAverageTest {
    public RunningAverage runningAverage;

    private List<Operations> operationsList(){
        return Arrays.asList(Operations.ADD, Operations.REMOVE);
    }
    private List<List<Double>> typesOfList() {
        List <List<Double>> list = new ArrayList<>();
        List<Double> list1 = new ArrayList<>();

        list.add(list1);
        list.add(null);

        return list;

    }
    /*
    * Instantiating Running Average
    */
    @BeforeEach
    public void setup() {
        runningAverage = new RunningAverage();
    }


    /*
    * Check Values after Instantiation of Running Average
    * @for 1.1
    */
    @Test
    @DisplayName("Values of population size and current average should be 0 and 0.0 respectively after Instantiating basic constructor")
    public void testPopulationSizeAndCurrectAverage() {
        assertEquals(0, runningAverage.getPopulationSize());
        assertEquals(0.0, runningAverage.getCurrentAverage());
    }


    /*
    * Must not change the contents of the given List.
    * @for 1.2.b
    */

    @Test
    @DisplayName("The given and the returned List should be equal")
    public void isGivenTheListForOperationsImmutable() {
        List<Double> initialList = new ArrayList<>(Arrays.asList(22.22, 33.33));
        assertEquals(initialList, runningAverage.getImmutableList(initialList));
    }


    /*
    * Adds and removes the given population to and from the RunningAverage, respectively.
    * @for 1.2.a
    * Must return the new average.
    * @for 1.2.c
    */
    @ParameterizedTest
    @DisplayName("populationSize and currentAverage should be correct after operations with new population number of 2")
    @MethodSource("operationsList")
    public void isCurrentAverageCorrectAfterPopulationIsAddedOrRemoved(Operations operation) {
        // removeAndAddElements is simulated here
        List<Double> populationList = List.of(22.22, 33.11);

        if(operation == Operations.REMOVE){
            // adding additional 2 element to avoid populationSize cannot be less than 0 exception
            runningAverage.removeAndAddElements(populationList, Operations.ADD);
        }

        int populationSize = runningAverage.getPopulationSize();
        double currentAverage = runningAverage.getCurrentAverage();
        double sum = populationSize * currentAverage;
        for (double element : populationList) {
            if(operation == Operations.ADD){
                sum += element;
                populationSize++;
            }else{
                sum -= element;
                populationSize--;
            }
        }

        currentAverage = sum / populationSize;
        runningAverage.removeAndAddElements(populationList, operation);
        assertEquals(currentAverage, runningAverage.getCurrentAverage());
        assertEquals(populationSize, runningAverage.getPopulationSize());
    }

    /*
    * Must return the current average if the given List object is empty or null
    * @for 1.2.d
    */
    @ParameterizedTest
    @DisplayName("Should get currentAverage if populationList is empty or null")
    @MethodSource("typesOfList")
    public void shouldTestIfOperationsGetZeroWhilePopulationListIsEmpty(List typeOfList) {
        Double currentAverage = runningAverage.removeAndAddElements(typeOfList, Operations.ADD);
        assertEquals(currentAverage, runningAverage.getCurrentAverage());
    }

    /*
    * Must update the population size, accordingly.
    * @for 1.2.e
    */
    @ParameterizedTest
    @DisplayName("Should get updated populationSize")
    @MethodSource("operationsList")
    public void shouldUpdatePopulationSize(Operations operation) {
        runningAverage = new RunningAverage(12, 2);
        runningAverage.removeAndAddElements(List.of(22.22, 33.11), operation);
        int currentPopulationSize = runningAverage.getPopulationSize();
        assertEquals(currentPopulationSize , runningAverage.getPopulationSize());
    }


    /*
     * Constructor
     * You may assume that the initial population size is NOT negative
     * @for 1.3
     */
    @Test
    @DisplayName("RuntimeException when initialPopulationSize < 0")
    public void isInitialPopulationSizeLessThanZero() {
        assertThrows(RuntimeException.class, () -> {
            new RunningAverage(1, -1);
        });
    }


    /*
    * Remove Population
    * @for 1.4
    */
    @Test
    @DisplayName("RuntimeException when populationSize < 0 during REMOVE")
    public void isPopulationSizeLessThanZeroRemove() {
        assertThrows(RuntimeException.class, () -> {
            runningAverage.removeAndAddElements(List.of(3.3, -3.4), Operations.REMOVE);
        });
    }


    /*
    * Must create a new RunningAverage object.
    * @for 1.5.a
    */
    @Test
    @DisplayName("Checks if the variable returned from create method is RunninAverage object")
    public void isRunningAverageObject() {
        RunningAverage runningAverage1 = new RunningAverage(12.2, 2);
        RunningAverage runningAverage2 = new RunningAverage(18.3, 3);
        assertTrue(RunningAverage.combine(runningAverage1, runningAverage2).getClass().getName() == runningAverage.getClass().getName());
    }


    /*
    * Must combine the two averages and their respective population sizes.
    * @for 1.5.b
    */
    @Test
    @DisplayName("Checks if the connent function combines two averages and population size")
    public void isAverageAndPopulationGotCorrectly() {
        // We are going to simulate this
        RunningAverage runningAverage1 = new RunningAverage(12.2, 2);
        RunningAverage runningAverage2 = new RunningAverage(18.3, 3);
        RunningAverage runningAverageTest = new RunningAverage((runningAverage1.getCurrentAverage() * runningAverage1.getPopulationSize() + runningAverage2.getCurrentAverage() * runningAverage2.getPopulationSize())
            / (runningAverage1.getPopulationSize() + runningAverage2.getPopulationSize()),
            runningAverage1.getPopulationSize() + runningAverage2.getPopulationSize());

        // the real data
        runningAverage = RunningAverage.combine(runningAverage1, runningAverage2);

        assertEquals(runningAverageTest.getCurrentAverage(), runningAverage.getCurrentAverage());
        assertEquals(runningAverageTest.getPopulationSize(), runningAverage.getPopulationSize());
    }


    /*
    * Must combine the two averages and their respective population sizes.
    * @for 1.5.c
    */
    @Test
    @DisplayName("Checks if the connent function gets correct results")
    public void isAverageAndPopulationCorrect() {
        RunningAverage runningAverage1 = new RunningAverage(12.2, 2);
        RunningAverage runningAverage2 = new RunningAverage(18.3, 3);

        runningAverage = RunningAverage.combine(runningAverage1, runningAverage2);

        assertEquals(15.860, runningAverage.getCurrentAverage());
        assertEquals(5, runningAverage.getPopulationSize());
    }


    /*
    * Test 3 Decimal places.
    * @for 2.4
    */
    @Test
    @DisplayName("Checks if the average value has 3 decimal places")
    public void threeDecimalPlaces() {
        runningAverage = new RunningAverage(23.2353445, 2);
        System.out.println(runningAverage.getCurrentAverage());
        assertEquals(23.235, runningAverage.getCurrentAverage());
    }

    /*
     * Test 3 Decimal places.
     * @for 3
     */
    @Test
    @DisplayName("Test populations sizes for a single instantiation")
    public void testPopulationSizes() {
        runningAverage = new RunningAverage(3.0, 5);
        // step 1
        runningAverage.removeAndAddElements(List.of(4.0, 5.0), Operations.ADD);
        assertEquals(runningAverage.getDecimals(3.4286), runningAverage.getCurrentAverage());
        assertEquals(7, runningAverage.getPopulationSize());

        // step 2
        RunningAverage runningAverage1 = new RunningAverage(runningAverage);
        runningAverage1.removeAndAddElements(List.of(2.0), Operations.REMOVE);
        assertEquals(runningAverage1.getDecimals(3.6667), runningAverage1.getCurrentAverage());
        assertEquals(6, runningAverage1.getPopulationSize());

        // step 3
        RunningAverage runningAverage2 = new RunningAverage(runningAverage1.getDecimals(6.33), 6);
        RunningAverage runningAverageCombined = RunningAverage.combine(runningAverage1, runningAverage2);
        assertEquals(runningAverageCombined.getDecimals(4.999), runningAverageCombined.getCurrentAverage());
        assertEquals(12, runningAverageCombined.getPopulationSize());
    }
}
