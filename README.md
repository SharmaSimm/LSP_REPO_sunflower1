# Assignment 2 – CSV ETL Pipeline (Java)

**Course:** Large Scale Programming (CSCI 363/540)  
**Student:** Shikshya Sharma  
**Package:** `org.howard.edu.lsp.assignment2`  
**Main class:** `ETLPipeline.java`

---

## How to Run
- Place the input file `products.csv` in the `data/` folder at the project root.
- Run the program from the project root (Eclipse does this automatically).
- The program reads from `data/products.csv` and writes the output to `data/transformed_products.csv`.

**AI Usage**
## AI Usage
- **Tool used:** ChatGPT  
- **Purpose:** To validate the code, identify potential errors, and improve readability and structure.  
- **Prompt used:**  
  “Look over any errors which we have avoid and make the code more readable and scalable.”  
- **Response excerpt considered:** Suggestions to skip blank lines, improve error messages, and use `BigDecimal.setScale(2, RoundingMode.HALF_UP)` for precise rounding.  
- **How I used it:** Reviewed the feedback, kept the core structure of my code, and applied some improvements (comments, readability tweaks, and better error messages).

