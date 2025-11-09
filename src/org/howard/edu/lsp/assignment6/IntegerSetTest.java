package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** JUnit 5 tests for IntegerSet (normal + edge + self-ops). */
public class IntegerSetTest {

  private IntegerSet a;
  private IntegerSet b;

  @BeforeEach
  void setup() {
    a = new IntegerSet();
    b = new IntegerSet();
  }

  @Test
  void testClearLengthIsEmpty() {
    assertTrue(a.isEmpty());
    a.add(1); a.add(2);
    assertEquals(2, a.length());
    a.clear();
    assertTrue(a.isEmpty());
    assertEquals(0, a.length());
  }

  @Test
  void testAddPreventsDuplicatesContains() {
    a.add(1); a.add(1); a.add(2);
    assertEquals(2, a.length());
    assertTrue(a.contains(1));
    assertTrue(a.contains(2));
    assertFalse(a.contains(3));
  }

  @Test
  void testRemovePresentAbsent() {
    a.add(10); a.add(20);
    a.remove(30); // no-op
    assertEquals(2, a.length());
    a.remove(10);
    assertEquals(1, a.length());
    assertFalse(a.contains(10));
  }

  @Test
  void testLargestSmallest() {
    a.add(5); a.add(2); a.add(9); a.add(7);
    assertEquals(9, a.largest());
    assertEquals(2, a.smallest());
  }

  @Test
  void testLargestThrowsOnEmpty() {
    assertThrows(IllegalStateException.class, () -> a.largest());
  }

  @Test
  void testSmallestThrowsOnEmpty() {
    assertThrows(IllegalStateException.class, () -> a.smallest());
  }

  @Test
  void testEqualsOrderIndependent() {
    a.add(1); a.add(2); a.add(3);
    b.add(3); b.add(2); b.add(1);
    assertEquals(a, b);
    b.add(4);
    assertNotEquals(a, b);
  }

  @Test
  void testUnion() {
    a.add(1); a.add(2);
    b.add(2); b.add(3);
    a.union(b);                   // modifies a
    assertEquals(3, a.length());
    assertTrue(a.contains(1));
    assertTrue(a.contains(2));
    assertTrue(a.contains(3));
    assertEquals(2, b.length());  // b unchanged
  }

  @Test
  void testIntersect() {
    a.add(1); a.add(2); a.add(3);
    b.add(2); b.add(3); b.add(4);
    a.intersect(b); // modifies a → {2,3}
    assertEquals(2, a.length());
    assertTrue(a.contains(2)); assertTrue(a.contains(3));
    assertFalse(a.contains(1)); assertFalse(a.contains(4));
  }

  @Test
  void testDiff() {
    a.add(1); a.add(2); a.add(3);
    b.add(2); b.add(99);
    a.diff(b); // a \ b => {1,3}
    assertEquals(2, a.length());
    assertTrue(a.contains(1)); assertTrue(a.contains(3));
    assertFalse(a.contains(2));
  }

  @Test
  void testComplement() {
    a.add(1); a.add(2);
    b.add(2); b.add(3); b.add(4);
    a.complement(b); // => (b \ original_a) = {3,4}
    assertEquals(2, a.length());
    assertTrue(a.contains(3)); assertTrue(a.contains(4));
    assertFalse(a.contains(1)); assertFalse(a.contains(2));
  }

  @Test
  void testSelfOperations() {
    a.add(1); a.add(2);

    a.union(a);      // no change
    assertEquals(2, a.length());

    a.intersect(a);  // no change
    assertEquals(2, a.length());

    a.diff(a);       // becomes empty
    assertTrue(a.isEmpty());

    a.add(7); a.add(8);
    a.complement(a); // (a \ a) = empty
    assertTrue(a.isEmpty());
  }

  @Test
  void testToStringFormat() {
    a.add(3); a.add(1); a.add(2);
    assertEquals("[1, 2, 3]", a.toString()); // sorted stable format
  }
}
