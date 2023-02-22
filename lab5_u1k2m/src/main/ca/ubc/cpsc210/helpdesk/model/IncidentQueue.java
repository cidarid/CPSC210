package ca.ubc.cpsc210.helpdesk.model;

import java.util.LinkedList;

// Represents a queue of incidents to be handled by helpdesk
// with maximum size MAX_SIZE
public class IncidentQueue {
    public static final int MAX_SIZE = 10;
    private LinkedList<Incident> queue;

    public IncidentQueue() {
        queue = new LinkedList<>();
    }

    // REQUIRES: i is non-null
    // EFFECTS: Adds incident i to this.queue and returns true, unless
    // queue is already full, in which case it doesn't add i and returns false
    public boolean addIncident(Incident i) {
        if (queue.size() < MAX_SIZE) {
            return queue.add(i);
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Returns and removes the next value in the queue
    public Incident getNextIncident() {
        return queue.remove();
    }

    // EFFECTS: If the incident with a case number of caseNumber exists, return
    // the position of that incident in the queue. If it doesn't exist, return -1
    public int getPositionInQueueOfCaseNumber(int caseNumber) {
        int temp = 1;
        for (Incident i : queue) {
            if (i.getCaseNum() == caseNumber) {
                return temp;
            }
            temp++;
        }
        return -1;
    }

    // EFFECTS: Returns the amount of elements in the queue
    public int length() {
        return queue.size();
    }

    // EFFECTS: Returns true if the queue is empty, false otherwise
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // EFFECTS: Returns true if the queue is full, false otherwise
    public boolean isFull() {
        return queue.size() >= MAX_SIZE;
    }

    // EFFECTS: Returns true if the queue contains i, false if not
    public boolean contains(Incident i) {
        return queue.contains(i);
    }
}
