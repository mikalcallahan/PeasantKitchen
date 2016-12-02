package framework;

/**
 * The interface Parser.
 *
 * @param <Input>  the type parameter
 * @param <Result> the type parameter
 */
public interface Parser<Input, Result>
{
    /**
     * Parse result.
     *
     * @param input the input
     * @return the result
     * @throws Exception the exception
     */
    Result parse(Input input) throws Exception;
}
