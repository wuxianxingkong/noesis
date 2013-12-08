package sandbox.language;

import java.lang.reflect.ParameterizedType;

import java.util.Collection;

import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;


public class ModelCCTest<T> 
{
	public T parse (String text)
			throws Exception
	{
	    Parser<T> parser = createParser();
	    T         model = parser.parse(text);

		return model;
	}

	public Collection<T> parseAll (String text)
			throws Exception
	{
	    Parser<T>     parser = createParser();
	    Collection<T> models = parser.parseAll(text);

		return models;
	}

	public Parser<T> createParser ()
		throws Exception
	{
		ModelReader modelReader = new JavaModelReader(getGenericClass());
		Model model = modelReader.read();
		Parser<T> parser = ParserFactory.create(model,ParserFactory.WHITESPACE);

		return parser;
	}

	private Class getGenericClass ()
	{
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class) parameterizedType.getActualTypeArguments()[0];
	}
}

