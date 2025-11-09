package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A mutable mathematical set of integers backed by an ArrayList.
 * <p>Rules:
 * <ul>
 *   <li>No duplicates stored.</li>
 *   <li>All mutators modify <code>this</code> (no new objects returned).</li>
 *   <li><code>largest()</code> and <code>smallest()</code> throw IllegalStateException on empty set.</li>
 *   <li><code>equals(Object)</code> compares contents order-independently.</li>
 *   <li><code>toString()</code> returns square-bracket format, e.g., [1, 2, 3].</li>
 * </ul>
 */
public class IntegerSet  {
  private List<Integer> set = new ArrayList<Integer>();

  /** Clears the internal representation of the set. */
  public void clear() { 
    set.clear();
  }

  /** Returns the number of elements in the set. */
  public int length() { 
    return set.size();
  }

  /**
   * Returns true if the 2 sets are equal, false otherwise;
   * Two sets are equal if they contain all of the same values in ANY order.
   * This overrides the equals method from the Object class.
   */
  @Override
  public boolean equals(Object o) { 
    if (this == o) return true;
    if (!(o instanceof IntegerSet)) return false;
    IntegerSet other = (IntegerSet) o;
    if (this.length() != other.length()) return false;
    return this.set.containsAll(other.set);
  }

  /** Returns true if the set contains the value, otherwise false. */
  public boolean contains(int value) { 
    return set.contains(value);
  }

  /** Returns the largest item in the set (throws IllegalStateException if empty). */
  public int largest()  { 
    if (set.isEmpty()) throw new IllegalStateException("largest() called on empty set");
    int max = set.get(0);
    for (int v : set) if (v > max) max = v;
    return max;
  }

  /** Returns the smallest item in the set (throws IllegalStateException if empty). */
  public int smallest()  { 
    if (set.isEmpty()) throw new IllegalStateException("smallest() called on empty set");
    int min = set.get(0);
    for (int v : set) if (v < min) min = v;
    return min;
  }

  /** Adds an item to the set or does nothing if already present. */
  public void add(int item) { 
    if (!set.contains(item)) set.add(item);
  }

  /** Removes an item from the set or does nothing if not there. */
  public void remove(int item) { 
    set.remove(Integer.valueOf(item));
  }

  /** Set union: modifies this to contain all unique elements in this or other. */
  public void union(IntegerSet other) { 
    for (int v : other.set) if (!this.set.contains(v)) this.set.add(v);
  }

  /** Set intersection: modifies this to contain only elements in both sets. */
  public void intersect(IntegerSet other) { 
    List<Integer> result = new ArrayList<>();
    for (int v : this.set) if (other.set.contains(v)) result.add(v);
    this.set.clear();
    this.set.addAll(result);
  }

  /** Set difference (this \ other): modifies this to remove elements found in other. */
  public void diff(IntegerSet other) { 
    this.set.removeAll(other.set);
  }

  /** Set complement: modifies this to become (other \ this). */
  public void complement(IntegerSet other) { 
    List<Integer> original = new ArrayList<>(this.set);
    List<Integer> result = new ArrayList<>(other.set);
    result.removeAll(original);
    this.set.clear();
    this.set.addAll(result);
  }

  /** Returns true if the set is empty, false otherwise. */
  public boolean isEmpty() { 
    return set.isEmpty();
  }

  /** Returns a String representation; overrides Object.toString(). */
  @Override
  public String toString() { 
    List<Integer> copy = new ArrayList<>(set);
    Collections.sort(copy);
    return copy.toString();
  }
}
