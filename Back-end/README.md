## 1. User Stories:

**Authentication & Roles**
1. As a user, I want to log in with my email and password, so that I can receive an access token to use the system.
2. As the system, I want to validate the token and role on every request, so that unauthorized users cannot access protected endpoints.

**Supplier** 

3. As an admin, I want to register a supplier with name, phone, email and address, so that I can keep their contact information available at all times.
4. As an admin, I want to list, edit and deactivate suppliers, so that I can keep the supplier catalog up to date.

**Ingredient**

5. As an admin, I want to register an ingredient with a base quantity, cost and optional supplier, so that I can use it later in recipes.
6. As an admin, I want to update the cost of an ingredient and have the price history saved automatically, so that I can track price changes over time.
7. As an admin, I want to view the price history of an ingredient, so that I can compare suppliers and make better purchasing decisions.

**Recipe**

8. As an admin, I want to create a recipe with its ingredients and the quantity each one uses, so that the system can calculate costs automatically.
9. As an admin, I want to see the automatically calculated cost of a recipe based on its ingredients, so that I can make informed pricing decisions.

**Additional Costs**

10. As an admin, I want to add cost components to a recipe such as labor, gas and overhead, so that the total cost reflects the real production cost beyond ingredients.

**Product**

11. As an admin, I want to register a product linked to a recipe and define its suggested price, so that I can manage what is offered for sale.
12. As an admin, I want to see a product summary with its total cost breakdown and margin against the suggested price, so that I can evaluate the profitability of each product.
## 2. Business rules for this project
Due to the limited time I have to develop this whole project, I am not going to implement it in as much detail as the project needs. For that reason I will develop only the most important business rules, which are:

- [x] **User**
- No duplicate username
- No duplicate email
- A user cannot be deleted if they are the only ADMIN in the system

- [x] **Supplier**
- No duplicate name
- A supplier cannot be deactivated if it is the only supplier providing a specific ingredient

- [x] **Ingredient**
- No duplicate name
- An ingredient cannot be deactivated if it is currently used in at least one active recipe

- [x] **Recipe**
- No duplicate internal code
- Cannot add an ingredient that doesn't exist
- A recipe cannot be activated if any of its ingredients are inactive

- [x] **Product**
- No duplicate SKU
- Must be linked to an existing recipe
- A product's suggested price cannot be lower than the total calculated cost of its recipe

- [x] **SupplierIngredient**
- Unit price cannot be negative
- A supplier ingredient cannot be deleted if it is the only source for that ingredient