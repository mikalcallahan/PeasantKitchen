package framework;

public class IngredientQuantity
{
	public String ingredient;
	public Integer quantity;
	public String unit;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IngredientQuantity [ingredient=");
		builder.append(ingredient);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", unit=");
		builder.append(unit);
		builder.append("]");
		return builder.toString();
	}
	
	
}
