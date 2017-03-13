package uni.okejki;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by tomek on 23.01.2016.
 */
public class MutationController {
    private final Random RANDOM = new Random();

    public void mutate(ProposedSolution solution) {
        ArrayList<OperationFrame> firstMachine = solution.getFirstMachine();
        ArrayList<OperationFrame> secondMachine = solution.getSecondMachine();
        ArrayList<Integer> firstOperationsIndexes = getFirstOperationsIndexes(secondMachine);

        int randomIndex = getFirst(firstOperationsIndexes.size());
        int firstToMutate = firstOperationsIndexes.get(randomIndex);
        int firstToMutateSibling = findSiblingIndex(firstMachine, secondMachine.get(firstToMutate));
        int secondToMutate = firstOperationsIndexes.get(randomIndex + 1);
        int secondToMutateSibling = findSiblingIndex(firstMachine, secondMachine.get(secondToMutate));

        changePlaces(secondMachine, firstToMutate, secondToMutate);
        changePlaces(firstMachine, firstToMutateSibling, secondToMutateSibling);

        fixOrder(secondMachine);
        fixOrder(firstMachine);
        fixOperations(firstMachine, secondMachine);
        solution.reduceIdleTimes();
    }

    private ArrayList<Integer> getFirstOperationsIndexes(ArrayList<OperationFrame> machine) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < machine.size(); ++i) {
            if (machine.get(i).isFirst()) {
                indexes.add(i);
            }
        }
        return indexes;
    }

    private void fixOrder(ArrayList<OperationFrame> machine) {
        for (int i = 1; i < machine.size(); ++i) {
            OperationFrame frameBefore = machine.get(i - 1);
            OperationFrame frameAfter = machine.get(i);

            if (frameBefore.getFinishTime() > frameAfter.getStartTime()) {
                frameAfter.setStartTime(frameBefore.getFinishTime());
            }
        }
    }

    private void fixOperations(ArrayList<OperationFrame> machine, ArrayList<OperationFrame> otherMachine) {
        for (int i = 0; i < machine.size(); ++i) {
            OperationFrame frame = machine.get(i);
            OperationFrame sibling = findSibling(otherMachine, frame);
            if (!frame.inRightOrderWith(sibling)) {
                if (frame.isFirst()) {
                    addIdleFromOperation(otherMachine, sibling, frame.getFinishTime() - sibling.getStartTime());
                } else {
                    addIdleFromOperation(machine, frame, sibling.getFinishTime() - frame.getStartTime());
                }
            }
        }
    }

    private void changePlaces(ArrayList<OperationFrame> machine, int firstIndex, int secondIndex) {
        int firstStartTime = machine.get(firstIndex).getStartTime();
        machine.get(firstIndex).setStartTime(machine.get(secondIndex).getStartTime());
        machine.get(secondIndex).setStartTime(firstStartTime);
        Collections.swap(machine, firstIndex, secondIndex);
    }

    private OperationFrame findSibling(final ArrayList<OperationFrame> machine, final OperationFrame operation) {
        for (final OperationFrame frame : machine) {
            if (frame.getTaskId() == operation.getTaskId()) {
                return frame;
            }
        }

        return null;
    }

    private int findSiblingIndex(final ArrayList<OperationFrame> machine, final OperationFrame operation) {
        for (int i = 0; i < machine.size(); ++i) {
            if (machine.get(i).getTaskId() == operation.getTaskId()) {
                return i;
            }
        }

        return -1;
    }

    private int getFirst(int size) {
        return RANDOM.nextInt(size - 1);
    }

    private void addIdleFromOperation(ArrayList<OperationFrame> machine, OperationFrame operation, int time) {
        boolean readyToMove = false;
        for (OperationFrame o : machine) {
            if (o.equals(operation)) {
                readyToMove = true;
            }

            if (readyToMove) {
                o.setStartTime(o.getStartTime() + time);
            }
        }
    }
}
