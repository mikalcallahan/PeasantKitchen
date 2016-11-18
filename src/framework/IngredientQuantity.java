package framework;

public class IngredientQuantity 
{
	public String ingredient;
	public Quantity quantity = new Quantity();
	
	public class Quantity
	{
		public Double amount = 0.0;
		public String unit = "";
	}
}