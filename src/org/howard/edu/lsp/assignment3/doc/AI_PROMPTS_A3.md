# AI Prompts – Assignment 3

> Note: I used a generative AI assistant to brainstorm an OO decomposition that preserves
> Assignment 2 behavior. Below are several prompts, short excerpts, and how I used or
> adapted the suggestions.

---

## Prompt 1
**My prompt:**  
“Propose an object-oriented redesign for a small Java ETL that reads `data/products.csv`, applies:
(1) UPPERCASE name → (2) 10% discount for original Electronics (HALF_UP) → (3) recategorize to
‘Premium Electronics’ if final price > $500 and original category is Electronics → (4) compute PriceRange.
Write to `data/transformed_products.csv`. Use only core Java and relative paths.”

**AI excerpt considered:**  
- “Introduce a `Transformation` interface with one class per rule.”
- “Create `Product` model; enforce rounding in `setPrice` to keep two decimals (HALF_UP).”
- “Separate `CsvReader` and `CsvWriter`; orchestrate in a small main class.”

**How I used it:**  
I adopted the interface-based pipeline and the `Product` rounding guard, and split I/O into reader/writer.

---

## Prompt 2
**My prompt:**  
“How can I avoid Windows file-lock issues if the output CSV is open in Excel?”

**AI excerpt considered:**  
“Write to a temporary file, then `Files.move(temp, target, REPLACE_EXISTING)` to atomically replace.”

**How I used it:**  
Implemented this in `CsvWriter.load`.

---

## Prompt 3
**My prompt:**  
“Suggest Javadoc structure for each class and public method to meet a course rubric.”

**AI excerpt considered:**  
“Class description, responsibilities, assumptions; for methods: params, return, exceptions, side effects.”

**How I used it:**  
Wrote/edited Javadocs for every class and public method. I verified accuracy and edited wording.

---

## Prompt 4 (sanity check)
**My prompt:**  
“List the transform order and edge cases I might miss.”

**AI excerpt considered:**  
“Order strictly: uppercase → discount → recategorize → price range; ignore blank lines; skip malformed rows.”

**How I used it:**  
Reader ignores blank lines; parse failures return `null` and are counted as skipped by the app.
