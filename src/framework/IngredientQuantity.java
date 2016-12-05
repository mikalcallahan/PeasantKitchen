package framework;

/**
 * The type Ingredient quantity.
 */
public class IngredientQuantity
{
    /**
     * The Ingredient.
     */
    public String ingredient;
    /**
     * The Quantity.
     */
    public Range quantity;
    /**
     * The Unit.
     */
    public String unit;


    @Override
    public String toString()
    {
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


    /**
     * The type Range.
     */
    public static class Range
    {
        /**
         * The Lower.
         */
        public Double lower = 0.0;
        /**
         * The Upper.
         */
        public Double upper = 0.0;

        /**
         * Instantiates a new Range.
         *
         * @param val the val
         */
        public Range(Double val)
        {
            this.lower = this.upper = val;
        }

        /**
         * Instantiates a new Range.
         *
         * @param lower the lower
         * @param upper the upper
         */
        public Range(Double lower, Double upper)
        {
            this.lower = lower;
            this.upper = upper;
        }

        /**
         * Is single value boolean.
         *
         * @return the boolean
         */
        public boolean isSingleValue()
        {
            return
                    this.lower.equals(this.upper);
        }

        @Override
        public String toString()
        {
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
