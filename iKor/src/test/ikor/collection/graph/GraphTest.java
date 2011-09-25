package test.ikor.collection.graph;

// Title:       Generic Graph ADT
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.graph.*;

/**
 * Generic Graph Test
 * 
 * @author Fernando Berzal
 */

public class GraphTest
{
	// TEST

	public static void main (String args[])
	{
		DynamicGraph<String,Integer> undirected = roadmap();
		DynamicGraph<String,String>  directed   = web();
 
		System.out.println("UNDIRECTED GRAPH: ROADMAP");
		System.out.println(undirected);

		undirected.remove("Granada");
		undirected.remove("Huétor Vega", "Cájar", 1);
		undirected.add("La Zubia", "Motril", 70);
		System.out.println("UNDIRECTED GRAPH AFTER MODIFICATIONS");
		System.out.println(undirected);


		System.out.println("DIRECTED GRAPH: WEB");
		System.out.println(directed);

		directed.remove("Java");
		directed.remove("home", "ASP.NET", null);
		System.out.println("DIRECTED GRAPH AFTER MODIFICATIONS");
		System.out.println(directed);

	}


	public static DynamicGraph<String,Integer> roadmap ()
	{
		DynamicGraph<String,Integer> graph = new DynamicGraph<String,Integer>(false);

		graph.add("Granada");
		graph.add("Motril");
		graph.add("La Zubia");
		graph.add("Cájar");
		graph.add("Huétor Vega");
		graph.add("Castell de Ferro");
		graph.add("Almería");
		graph.add("Guadix");

		graph.add("Granada", "Motril", 70);
		graph.add("Granada", "La Zubia", 3);
		graph.add("Granada", "Cájar", 3);
		graph.add("Granada", "Huétor Vega", 2);
		graph.add("Huétor Vega", "Cájar", 1);
		graph.add("Cájar", "La Zubia", 1);
		graph.add("Motril", "Castell de Ferro", 20);
		graph.add("Castell de Ferro", "Almería", 94);
		graph.add("Guadix", "Almería", 106);
		graph.add("Granada", "Guadix", 55);

		return graph;
	}

	public static DynamicGraph<String,String> web ()
	{
		DynamicGraph<String,String> graph = new DynamicGraph<String,String>(true);

		graph.add("home");
		graph.add("C");
		graph.add("C#");
		graph.add("C++Builder");
		graph.add("Java");
		graph.add("DB");
		graph.add("Data Mining");
		graph.add("Internet");
		graph.add("ASP.NET");

		graph.add("home", "C", null);
		graph.add("home", "C#", null);
		graph.add("home", "C++Builder", null);
		graph.add("home", "Java", null);
		graph.add("home", "Internet", null);
		graph.add("home", "ASP.NET", null);
		graph.add("home", "DB", null);
		graph.add("home", "Data Mining", null);
		graph.add("C", "C#", null);
		graph.add("C", "C++Builder", null);
		graph.add("C", "Java", null);
		graph.add("C#", "ASP.NET", null);
		graph.add("Java", "C", null);
		graph.add("Java", "C#", null);
		graph.add("Java", "C++Builder", null);
		graph.add("DB", "Data Mining", null);
		graph.add("Internet", "DB", null);

		return graph;		
	}
}

