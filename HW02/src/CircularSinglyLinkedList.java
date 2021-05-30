import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Jayanee Venkat
 * @version 1.0
 * @userid jvenkat8
 * @GTID 903628863
 *
 * Collaborators: none
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class CircularSinglyLinkedList<T> {


    // Do not add new instance variables or modify existing ones.
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
// index == size edge case
        int count;
        if (data == null) {
            throw new IllegalArgumentException("enter valid data");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index needs to be pos. and less than size of LL");
        }
        else if (index == 0 && size == 0 && data != null) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(this.head);
            size++;
        } else if (this.head != null && index == 0) {
            this.addToFront(data);
        } else {
            CircularSinglyLinkedListNode prev = this.head;
            for (int i = 1; i < index; i++) {
                prev = prev.getNext();
            }
            prev.setNext(new CircularSinglyLinkedListNode(data, prev.getNext()));
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("don't enter null data");
        }
        if (head == null) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
            size++;
        } else {
            CircularSinglyLinkedListNode n = new CircularSinglyLinkedListNode(head.getData(), head.getNext());
            CircularSinglyLinkedListNode h = head;
            head.setData(data);
            head.setNext(n);
            size++;
        }
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("don't enter null data");
        }
        if (head == null) {
            head = new CircularSinglyLinkedListNode<>(data);
            head.setNext(head);
            size++;
        } else {
            this.addToFront(data);
            head= head.getNext();
        }
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Choose and index that is in bounds");
        }
        if (index == 0) {
            return removeFromFront();
        }
            CircularSinglyLinkedListNode rem;
            CircularSinglyLinkedListNode trans = head;
            for (int i = 1; i < index; i++) {
                trans = trans.getNext();
            }
            rem = trans.getNext();
            trans.setNext(trans.getNext().getNext());
            size--;
            return (T) rem.getData();
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("This list is empty");
        } else {
            T rem = head.getData();
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            size--;
            return rem;
        }
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            head = null;
            throw new NoSuchElementException("list is empty");
        }
        CircularSinglyLinkedListNode n = head;
        T rem;
        int count = 0;
        while (count < size - 2) {
            n = n.getNext();
            count++;
        }
        size--;
        rem = (T) n.getNext().getData();
        n.setNext(head);
        if (size == 0) {
            head = null;
        }
        return rem;
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        CircularSinglyLinkedListNode n = head;
        int count = 0;
        if (index == 0) {
            return head.getData();
        } else {
            while (index > count) {
                n = n.getNext();
                count++;
            }
            return (T) n.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        //data is in the head edge case
        if (data == null) {
            throw new IllegalArgumentException("illegal argumet");
        }
        CircularSinglyLinkedListNode curr = head;
        int index = 0;
        CircularSinglyLinkedListNode bef = new CircularSinglyLinkedListNode(null);
        CircularSinglyLinkedListNode ret = new CircularSinglyLinkedListNode(null);
        for (int i = 0; i < size; i++) {
            if (curr.getNext().getData().equals(data)) {
                bef = curr;
                ret = curr.getNext();
            }
            curr = curr.getNext();
        }
        try {
            bef.setNext(bef.getNext().getNext());
            size--;
            return (T) ret.getData();
        } catch (Exception e) {
            throw new NoSuchElementException("data does not exist");
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] list = (T[]) new Object[size];

        CircularSinglyLinkedListNode curr = head;
        if (this.isEmpty()) {
            return list;
        } else {
            for (int i = 0; i < size; i++) {
                list[i] = (T) curr.getData();
                curr = curr.getNext();
            }
            return list;
        }
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
