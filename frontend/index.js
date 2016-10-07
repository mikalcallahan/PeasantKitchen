/*
 *	javascript for index (suggestions)
 */

window.onload=function suggestions(){
	var suggestedIngred = [	'Apple',
							'Almond',
							'Apricot',
							'Artichoke',
							'Asparagus',
							'Avocado',
							'Bacon',
							'Baking Powder',
							'Bay Leaf',
							'Balsamic Vinegar',
							'Cabbage',
							'Dates',
							'Eggs',
							'Fennel'];
	var list = document.getElementById('ingredients');
	suggestedIngred.forEach(function suggestions(item){
	   var option = document.createElement('option');
	   option.value = item;
	   list.appendChild(option);
	};
}
