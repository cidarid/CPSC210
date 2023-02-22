# Recipe Book
A way to store recipes

**What will the application do?**

This application will allow users to create recipes and save them for later usage. It will allow users to input various parameters, like *ingredients*, *tags*, *recipe name*, *recipe image*, and more.

**Who will use it?**

I hope to use this recipe book myself, as I cook a lot at home and while some other apps offer the functionality I intend to implement, they generally cost money. I also hope that other people will use it for an easy to access recipe storage system. 

**Why is this project of interest to you?**

Like I said, I cook a lot myself, and modify my recipes a lot, so having a digital system that is easy to change would be great to have.

## User Stories
- As a user, I want to be able to create a new recipe
- As a user, I want to be able to modify a created recipe
- As a user, I want to be able to view recipes that I have created
- As a user, I want to be able to access a more detailed view of a recipe
- As a user, I want to be able to add multiple recipes to my recipe book
- As a user, I want to be able to save my recipe(s) so that they persist
- As a user, I want to be able to open saved recipes

## Instructions for Grader
- You can generate the first required event related to adding recipes to a recipebook by clicking on the "New" button
on the main page. This will bring you to the UI for creating a new recipe, which when saved will be added to the 
recipebook and saved to disk.
- You can generate the second required event related to adding recipes to a recipebook by clicking the view button after
selecting a recipe from the recipebook. This will display a detailed view of the selected recipe.
- Other events added: You are able to edit recipes, delete recipes, and add/delete ingredients from recipes.
- You can locate my visual component by looking at the title bar for the home page, where the recipes are located.
There is a title image at the top of the window.
- You can save the state of my application by creating a new recipe. Every time a new recipe is created, it is 
saved to disk, along with any changes made to the recipe or any of its fields. 
- You can reload the state of my application by opening the application. It automatically loads the recipes from
disk, and is more or less a "live" version of what's on the disk. 

## Phase 4: Task 2
- Added ingredient Tomato Sauce to ingredient list.
- Added ingredient Spaghetti to ingredient list.
- Saved recipe Spaghetti to file.
- Added recipe Spaghetti to recipe book list.
- Added ingredient Bread to ingredient list.
- Saved recipe Toast to file.
- Added recipe Toast to recipe book list.
- Saved recipe Spaghetti to file.
- Deleted ingredient Tomato Sauce from recipe Spaghetti
- Removed ingredient Tomato Sauce from ingredient list.
- Saved recipe Spaghetti to file.
- Deleted recipe Toast.
- Removed recipe Toast from recipe book list.

## Phase 4: Task 3
If I had more time, I would likely remove RecipeBook entirely, and have Main link directly to RecipeFrame. 
RecipeBook is a holdover from the console UI, and is now unnecessary. I also might have ingredients be only 
accessed from recipe instead of being accessible from outside of recipe. Other than that, I would not change anything.