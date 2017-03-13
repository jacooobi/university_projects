package projektPO.utility;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item>
{
    private Node first;
    private class Node {
        Item item;
        Node next;
    }

    /**
     * Dodaje na koniec listy jednokierunkowej
     * @param item nowy element listy
     */
    public void add(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    /**
     * Iterator
     * @return zwraca iterator
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;  }

        public Item next()
        {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}