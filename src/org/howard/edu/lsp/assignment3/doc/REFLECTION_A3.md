# Reflection – Assignment 3 (Object-Oriented Redesign)

## Overview
Assignment 2 implemented the ETL pipeline in a single class with procedural logic. Assignment 3 redesigns the solution into multiple collaborating classes with clear responsibilities while preserving identical behavior and outputs.

## What changed vs. A2
**A2 (procedural)**  
- One class (`ETLPipeline`) performed extract, transform, and load in one place.  
- Transformation rules were expressed inside a single method (`transformLine`).  
- Rounding and category logic were “scattered” inside that method.  
- Tight coupling between I/O and transformation logic.

**A3 (OO decomposition)**  
- **CsvReader**: extract responsibility (file → `Product` objects).  
- **CsvWriter**: load responsibility (objects → CSV), includes atomic replace to avoid Windows file locks.  
- **Product** (domain model): encapsulates state; rounding policy enforced in `setPrice(…)`.  
- **PriceRange** (enum): boundary mapping from final price → bucket.  
- **Transformation** (interface) with concrete steps:
  - `UppercaseName`
  - `DiscountElectronics`
  - `RecategorizePremium`
  - `ComputePriceRange`
- **ETLApp**: orchestration only (builds ordered pipeline and runs steps).

This separation increases clarity, testability, and flexibility (e.g., add/remove/reorder steps without touching I/O).

## OO Concepts Used
- **Object & Class**: `Product`, `CsvReader`, `CsvWriter`, transformation classes encapsulate state/behavior.  
- **Encapsulation**: `Product#setPrice` enforces **HALF_UP** rounding to two decimals, guaranteeing consistency across steps.  
- **Inheritance / Polymorphism**: `Transformation` interface with multiple implementations used uniformly by `ETLApp`.  
- **Single Responsibility**: Each class does one thing well (read, write, a single rule, etc.).  
- **Separation of Concerns**: I/O is isolated from business rules; the pipeline doesn’t know about files.

## Why A3 is More Maintainable
- **Extensibility**: New rules are added as new `Transformation` classes; the pipeline order is explicit and easy to change.  
- **Testability**: Each transformation can be unit-tested in isolation using `Product` instances.  
- **Readability**: The pipeline reads in the same order as the spec: UPPERCASE → discount → recategorize → price range.  
- **Robustness**: Writer uses a temp file then atomic move to avoid partial writes when CSV is open.

## Equivalence Testing vs A2
I validated that A3 behaves exactly like A2:
1. **Case A (Normal)**  
   - Input: 6 rows from the spec.  
   - Output: `data/transformed_products.csv` matches A2 byte-for-byte (checked visually and with `fc` on Windows).  
   - Summary: 6 read, 6 transformed, 0 skipped.

2. **Case B (Empty: header only)**  
   - Reader returns empty list with `rowsRead = 0`.  
   - Writer still writes a file with just the header.  
   - Summary shows 0 transformed, 0 skipped.

3. **Case C (Missing file)**  
   - `CsvReader` throws `MissingInputException`.  
   - App prints the required error message and exits without crashing.

## Design Trade-offs / Limitations
- The assignment forbids external CSV libs, so splitting is basic (`String.split(",")`) and assumes no quoted commas—per spec.  
- I preserved the exact rounding/recategorization semantics from A2 for correctness; in a real system we might centralize numeric policy in a `Money` type.  
- The pipeline mutates `Product` in place for simplicity; an immutable variant with “copy-on-write” could make testing even safer.

## What I Would Improve Next
- Add lightweight unit tests for each transformation class.  
- Introduce a `Pipeline` class to encapsulate the ordered list and step execution.  
- Add minimal validation/error reporting for malformed lines (line numbers) while still skipping them gracefully.
