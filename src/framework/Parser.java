package framework;

/**
 * The interface Parser.
 *
 * @param <Input>  The input type given to the parser
 * @param <Result> The type of the parsed (result) object
 */
public interface Parser<Input, Result>
{
    /**
     * Parse the input object into the result object
     *
     * @param input the input
     * @return the result
     * @throws Exception the exception
     */
    Result parse(Input input) throws Exception;
}
