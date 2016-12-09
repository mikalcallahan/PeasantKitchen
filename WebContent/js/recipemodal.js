var recipeTitle, recipeRequirements, recipe;

$(document).ready(function() {
    $("#modalswitch").click(function() {
        $("#recipetitle").html(recipeTitle);
        $("#recipedisplay").html(recipe);
    });
});

function getRecipe(fullrecipe) {
    recipe = fullrecipe;
}

// recipesPerRow
// populateRecipeGrid
