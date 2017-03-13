package uni.okejki;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class CrossoverController {
    public CrossoverController() {}

    private OperationFrame getMachineOperation(final ArrayList<OperationFrame> machine, int taskId) {
        return machine.stream().filter(frame -> frame.getTaskId() == taskId).findFirst().get();
    }

    private Optional<OperationFrame> getIfOnMachine(final ArrayList<OperationFrame> machine, int taskId) {
        return machine.stream().filter(frame -> frame.getTaskId() == taskId).findFirst();
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

    private void fixPositions(ArrayList<OperationFrame> firstMachine, OperationFrame secondMachineOperation) {
        OperationFrame firstMachineOperation = getMachineOperation(firstMachine, secondMachineOperation.getTaskId());

        if (firstMachineOperation.isSecond()) {
            addIdleFromOperation(
                    firstMachine,
                    firstMachineOperation,
                    secondMachineOperation.getFinishTime() - firstMachineOperation.getStartTime()
            );
        } else {
            secondMachineOperation.setStartTime(firstMachineOperation.getFinishTime());
        }
    }

    private boolean orderIsValid(ArrayList<OperationFrame> firstMachine, OperationFrame secondMachineOperation) {
        OperationFrame firstMachineOperation = getMachineOperation(firstMachine, secondMachineOperation.getTaskId());
        OperationFrame first = firstMachineOperation.isFirst() ? firstMachineOperation : secondMachineOperation;
        OperationFrame second = firstMachineOperation.isSecond() ? firstMachineOperation : secondMachineOperation;

        return first.isBefore(second) && !first.overlaps(second);
    }

    private void addAllOperationsToSecondMachine(
            ArrayList<OperationFrame> begin,
            final ArrayList<OperationFrame> end,
            ArrayList<OperationFrame> firstMachine
    ) {
        for (final OperationFrame endOperation : end) {
            OperationFrame newOperation = new OperationFrame(endOperation);

            if (!begin.isEmpty()) {
                OperationFrame lastElement = begin.get(begin.size() - 1);

                if (newOperation.getStartTime() < lastElement.getFinishTime()) {
                    newOperation.setStartTime(lastElement.getFinishTime());
                }
            }

            if (!orderIsValid(firstMachine, newOperation)) {
                fixPositions(firstMachine, newOperation);
            }

            begin.add(newOperation);
        }
    }

    private void addAllOperations(ArrayList<OperationFrame> begin, final ArrayList<OperationFrame> end, final ArrayList<OperationFrame> second) {
        for (OperationFrame operation : end) {
            if (!begin.isEmpty()) {
                OperationFrame lastElement = begin.get(begin.size() - 1);
                OperationFrame newOperation = new OperationFrame(operation);

                if (newOperation.getStartTime() < lastElement.getFinishTime()) {
                    newOperation.setStartTime(lastElement.getFinishTime());
                }

                Optional<OperationFrame> f = getIfOnMachine(second, newOperation.getTaskId());
                if (f.isPresent()) {
                    OperationFrame frame = f.get();
                    if (frame.isSecond()) {
                        if ((frame.isBefore(newOperation) || frame.overlaps(newOperation))) {
                            newOperation.setStartTime(frame.getFinishTime());
                        }
                    } else {
                        if (!frame.isBefore(newOperation) || frame.overlaps(newOperation)) {
                            newOperation.setStartTime(frame.getFinishTime());
                        }
                    }
                }

                begin.add(newOperation);
            } else {
                begin.add(new OperationFrame(operation));
            }
        }
    }

    private ArrayList<OperationFrame> getCenterSublist(ArrayList<OperationFrame> machine, int centerTime) {
        int index;
        for (index = 0; index < machine.size(); ++index) {
            if (machine.get(index).getFinishTime() >= centerTime) {
                break;
            }
        }

        return new ArrayList<>(machine.subList(0, index));
    }

    public ArrayList<ArrayList<OperationFrame>> crossOver(ProposedSolution toChange, final ProposedSolution other) {
        int time = Math.max(
                toChange.getFirstMachine().get(toChange.getFirstMachine().size() - 1).getFinishTime(),
                toChange.getSecondMachine().get(toChange.getSecondMachine().size() - 1).getFinishTime()
        ) / 2;

        ArrayList<OperationFrame> firstMachineSlice = getCenterSublist(toChange.getFirstMachine(), time);
        ArrayList<OperationFrame> secondMachineSlice = getCenterSublist(toChange.getSecondMachine(), time);


        ArrayList<OperationFrame> otherFirstMachineElements = other.getFirstMachine()
                .stream()
                .filter(operation -> !firstMachineSlice
                        .stream()
                        .anyMatch(operation::equals)
                )
                .collect(Collectors.toCollection(ArrayList<OperationFrame>::new));

        ArrayList<OperationFrame> otherSecondMachineElements = other.getSecondMachine()
                .stream()
                .filter(operation -> !secondMachineSlice
                        .stream()
                        .anyMatch(operation::equals)
                )
                .collect(Collectors.toCollection(ArrayList<OperationFrame>::new));

        addAllOperations(firstMachineSlice, otherFirstMachineElements, secondMachineSlice);
        addAllOperationsToSecondMachine(secondMachineSlice, otherSecondMachineElements, firstMachineSlice);

        ArrayList<ArrayList<OperationFrame>> result = new ArrayList<>();
        result.add(firstMachineSlice);
        result.add(secondMachineSlice);
        return result;
    }
}
