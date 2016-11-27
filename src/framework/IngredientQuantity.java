package framework;

public class IngredientQuantity
{
	public String ingredient;
	public Range quantity;
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




	public static class Range
	{
		public Double lower = 0.0;
		public Double upper = 0.0;
		
		public Range(Double val)
		{
			this.lower = this.upper = val;
		}
		
		public Range(Double lower, Double upper)
		{
			this.lower = lower;
			this.upper = upper;
		}
		
		public boolean isSingleValue()
		{
			return
					this.lower.equals(this.upper);
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Range [lower=");
			builder.append(lower);
			builder.append(", upper=");
			builder.append(upper);
			builder.append("]");
			return builder.toString();
		}
		
		
	}
}
