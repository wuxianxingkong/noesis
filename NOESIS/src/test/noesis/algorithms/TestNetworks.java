package test.noesis.algorithms;

import test.noesis.MockVisitor;

import noesis.Network;
import noesis.ArrayNetwork;

public class TestNetworks 
{

	// Undirected graph
	
	public static Network<String,Integer> roadmap ()
	{
		Network<String,Integer> net = new ArrayNetwork<String,Integer>();

		net.add("Granada");
		net.add("Motril");
		net.add("La Zubia");
		net.add("Cájar");
		net.add("Huétor Vega");
		net.add("Castell de Ferro");
		net.add("Almería");
		net.add("Guadix");

		net.add2("Granada", "Motril", 70);
		net.add2("Granada", "La Zubia", 3);
		net.add2("Granada", "Cájar", 3);
		net.add2("Granada", "Huétor Vega", 2);
		net.add2("Huétor Vega", "Cájar", 1);
		net.add2("Cájar", "La Zubia", 1);
		net.add2("Motril", "Castell de Ferro", 20);
		net.add2("Castell de Ferro", "Almería", 94);
		net.add2("Guadix", "Almería", 106);
		net.add2("Granada", "Guadix", 55);

		return net;
	}
	
	
	public static MockVisitor<String> roadmapBFSVisitor (Network<String,Integer> roadmap)
	{
		MockVisitor<String> visitor = new MockVisitor<String>();
		
		visitor.addVisit("Granada");
		visitor.addVisit("Motril");
		visitor.addVisit("La Zubia");
		visitor.addVisit("Cájar");
		visitor.addVisit("Huétor Vega");
		visitor.addVisit("Guadix");
		
		visitor.addVisit("Castell de Ferro");
		
		visitor.addVisit("Almería");
		
		return visitor;
	}

	
	public static MockVisitor<String> roadmapDFSVisitor (Network<String,Integer> roadmap)
	{
		MockVisitor<String> visitor = new MockVisitor<String>();
		
		visitor.addVisit("Granada");
		visitor.addVisit("Motril");
		visitor.addVisit("Castell de Ferro");
		visitor.addVisit("Almería");
		visitor.addVisit("Guadix");
		
		visitor.addVisit("La Zubia");
		visitor.addVisit("Cájar");
		visitor.addVisit("Huétor Vega");		
		
		return visitor;
	}	

	
	public static MockVisitor<Integer> roadmapBFSLinkVisitor (Network<String,Integer> map)
	{
		MockVisitor<Integer> visitor = new MockVisitor<Integer>();
		
		visitor.addVisit(map.get("Granada", "Motril"));
		visitor.addVisit(map.get("Granada", "La Zubia"));
		visitor.addVisit(map.get("Granada", "Cájar"));
		visitor.addVisit(map.get("Granada", "Huétor Vega"));
		visitor.addVisit(map.get("Granada", "Guadix"));

		visitor.addVisit(map.get("Motril", "Granada"));
		visitor.addVisit(map.get("Motril", "Castell de Ferro"));

		visitor.addVisit(map.get("La Zubia", "Granada"));
		visitor.addVisit(map.get("La Zubia", "Cájar"));
		
		visitor.addVisit(map.get("Cájar", "Granada"));
		visitor.addVisit(map.get("Cájar", "Huétor Vega"));
		visitor.addVisit(map.get("Cájar", "La Zubia"));

		visitor.addVisit(map.get("Huétor Vega", "Granada"));
		visitor.addVisit(map.get("Huétor Vega", "Cájar"));

		visitor.addVisit(map.get("Guadix", "Almería"));
		visitor.addVisit(map.get("Guadix", "Granada"));
	
		visitor.addVisit(map.get("Castell de Ferro", "Motril"));
		visitor.addVisit(map.get("Castell de Ferro", "Almería"));

		visitor.addVisit(map.get("Almería", "Castell de Ferro"));
		visitor.addVisit(map.get("Almería", "Guadix"));

		return visitor;
	}

	
	public static MockVisitor<Integer> roadmapDFSLinkVisitor (Network<String,Integer> map)
	{
		MockVisitor<Integer> visitor = new MockVisitor<Integer>();
		
		visitor.addVisit(map.get("Granada", "Motril"));

		visitor.addVisit(map.get("Motril", "Granada"));
		visitor.addVisit(map.get("Motril", "Castell de Ferro"));
		
		visitor.addVisit(map.get("Castell de Ferro", "Motril"));
		visitor.addVisit(map.get("Castell de Ferro", "Almería"));

		visitor.addVisit(map.get("Almería", "Castell de Ferro"));
		visitor.addVisit(map.get("Almería", "Guadix"));

		visitor.addVisit(map.get("Guadix", "Almería"));
		visitor.addVisit(map.get("Guadix", "Granada"));
		
		visitor.addVisit(map.get("Granada", "La Zubia"));

		visitor.addVisit(map.get("La Zubia", "Granada"));
		visitor.addVisit(map.get("La Zubia", "Cájar"));
		
		visitor.addVisit(map.get("Cájar", "Granada"));
		visitor.addVisit(map.get("Cájar", "Huétor Vega"));

		visitor.addVisit(map.get("Huétor Vega", "Granada"));
		visitor.addVisit(map.get("Huétor Vega", "Cájar"));
		
		visitor.addVisit(map.get("Cájar", "La Zubia"));
		
		visitor.addVisit(map.get("Granada", "Cájar"));
		visitor.addVisit(map.get("Granada", "Huétor Vega"));
		visitor.addVisit(map.get("Granada", "Guadix"));

		return visitor;
	}	
	
	// Directed graph
	
	public static Network<String,String> web ()
	{
		Network<String,String> graph = new ArrayNetwork<String,String>();

		graph.add("home");
		graph.add("C");
		graph.add("C#");
		graph.add("C++Builder");
		graph.add("Java");
		graph.add("DB");
		graph.add("Data Mining");
		graph.add("Internet");
		graph.add("ASP.NET");

		graph.add("home", "C");
		graph.add("home", "C#");
		graph.add("home", "C++Builder");
		graph.add("home", "Java");
		graph.add("home", "Internet");
		graph.add("home", "ASP.NET");
		graph.add("home", "DB");
		graph.add("home", "Data Mining");
		graph.add("C", "C#");
		graph.add("C", "C++Builder");
		graph.add("C", "Java");
		graph.add("C#", "ASP.NET");
		graph.add("Java", "C");
		graph.add("Java", "C#");
		graph.add("Java", "C++Builder");
		graph.add("DB", "Data Mining");
		graph.add("Internet", "DB");

		return graph;		
	}

	public static MockVisitor<String> webBFSVisitor (Network<String,String> web)
	{
		MockVisitor<String> visitor = new MockVisitor<String>();

		visitor.addVisit("home");

		visitor.addVisit("C");
		visitor.addVisit("C#");
		visitor.addVisit("C++Builder");
		visitor.addVisit("Java");
		visitor.addVisit("Internet");
		visitor.addVisit("ASP.NET");
		visitor.addVisit("DB");
		visitor.addVisit("Data Mining");		
		
		return visitor;
	}
	
	public static MockVisitor<String> webDFSVisitor (Network<String,String> web)
	{
		MockVisitor<String> visitor = new MockVisitor<String>();
	
		visitor.addVisit("home");
		visitor.addVisit("C");
		visitor.addVisit("C#");
		visitor.addVisit("ASP.NET");
		
		// C
		visitor.addVisit("C++Builder");
		visitor.addVisit("Java");
		
		// home
		visitor.addVisit("Internet");
		visitor.addVisit("DB");
		visitor.addVisit("Data Mining");		
		
		return visitor;
	}
	
	
	public static MockVisitor<String> webBFSLinkVisitor (Network<String,String> web)
	{
		MockVisitor<String> visitor = new MockVisitor<String>();
		
		visitor.addVisit(web.get("home", "C"));
		visitor.addVisit(web.get("home", "C#"));
		visitor.addVisit(web.get("home", "C++Builder"));
		visitor.addVisit(web.get("home", "Java"));
		visitor.addVisit(web.get("home", "Internet"));
		visitor.addVisit(web.get("home", "ASP.NET"));
		visitor.addVisit(web.get("home", "DB"));
		visitor.addVisit(web.get("home", "Data Mining"));
		visitor.addVisit(web.get("C", "C#"));
		visitor.addVisit(web.get("C", "C++Builder"));
		visitor.addVisit(web.get("C", "Java"));
		visitor.addVisit(web.get("C#", "ASP.NET"));
		visitor.addVisit(web.get("Java", "C"));
		visitor.addVisit(web.get("Java", "C#"));
		visitor.addVisit(web.get("Java", "C++Builder"));
		visitor.addVisit(web.get("Internet", "DB"));
		visitor.addVisit(web.get("DB", "Data Mining"));
		
		return visitor;
	}

	public static MockVisitor<String> webDFSLinkVisitor (Network<String,String> web)
	{
		MockVisitor<String> visitor = new MockVisitor<String>();
		
		visitor.addVisit(web.get("home", "C"));
		visitor.addVisit(web.get("C", "C#"));
		visitor.addVisit(web.get("C#", "ASP.NET"));

		visitor.addVisit(web.get("C", "C++Builder"));
		visitor.addVisit(web.get("C", "Java"));
		visitor.addVisit(web.get("Java", "C"));
		visitor.addVisit(web.get("Java", "C#"));
		visitor.addVisit(web.get("Java", "C++Builder"));
		
		visitor.addVisit(web.get("home", "C#"));
		visitor.addVisit(web.get("home", "C++Builder"));
		visitor.addVisit(web.get("home", "Java"));
		visitor.addVisit(web.get("home", "Internet"));

		visitor.addVisit(web.get("Internet", "DB"));
		visitor.addVisit(web.get("DB", "Data Mining"));
		
		visitor.addVisit(web.get("home", "ASP.NET"));
		visitor.addVisit(web.get("home", "DB"));
		visitor.addVisit(web.get("home", "Data Mining"));
		
		return visitor;
	}
	
	
	// Weighted directed graph
	
	public static final double INF = Double.POSITIVE_INFINITY;
	
	public static final double[][] DIRECTED_DISTANCE = {
	    // s   2   3   4   5   6   7   t
		{  0,  9, 32, 45, 34, 14, 15, 50},
		{INF,  0, 23, 36, 25,INF,INF, 41},
		{INF,INF,  0, 13,  2,INF,INF, 18},
		{INF,INF,  6,  0,  8,INF,INF,  6},
		{INF,INF, 17, 11,  0,INF,INF, 16},
		{INF,INF, 18, 31, 20,  0,  5, 36},
		{INF,INF, 37, 31, 20,INF,  0, 36},
		{INF,INF,INF,INF,INF,INF,INF,  0}
	};
	
	public static Network<String,Integer> weightedDirectedGraph ()
	{
		Network<String,Integer> graph = new ArrayNetwork<String,Integer>();
		
		graph.add("s");
		graph.add("2");
		graph.add("3");
		graph.add("4");
		graph.add("5");
		graph.add("6");
		graph.add("7");
		graph.add("t");
		
		graph.add("s","2",9);
		graph.add("s","6",14);
		graph.add("s","7",15);
		
		graph.add("2","3",23);
		
		graph.add("3","5",2);
		graph.add("3","t",19);

		graph.add("4","3",6);
		graph.add("4","t",6);

		graph.add("5","4",11);
		graph.add("5","t",16);

		graph.add("6","3",18);
		graph.add("6","5",30);
		graph.add("6","7",5);

		graph.add("7","5",20);
		graph.add("7","t",44);
				
		return graph;
	}
	
	public static final double[][] UNREACHABLE_DISTANCE = {
	    // s   2   3   4   5   6   7   t
		{  0,  9, 32,INF,INF, 14, 15, 51},
		{INF,  0, 23,INF,INF,INF,INF, 42},
		{INF,INF,  0,INF,INF,INF,INF, 19},
		{INF,INF,  6,  0,INF,INF,INF,  6},
		{INF,INF, 17, 11,  0,INF,INF, 16},
		{INF,INF, 18,INF,INF,  0,  5, 37},
		{INF,INF,INF,INF,INF,INF,  0, 44},
		{INF,INF,INF,INF,INF,INF,INF,  0}
	};
	
	public static Network<String,Integer> weightedUnreachableGraph ()
	{
		Network<String,Integer> graph = new ArrayNetwork<String,Integer>();
		
		graph.add("s");
		graph.add("2");
		graph.add("3");
		graph.add("4"); // Unreachable node (starting from s)
		graph.add("5"); // Unreachable node (starting from s)
		graph.add("6");
		graph.add("7");
		graph.add("t");
		
		graph.add("s","2",9);
		graph.add("s","6",14);
		graph.add("s","7",15);
		
		graph.add("2","3",23);
		
		//graph.add("3","5",2);
		graph.add("3","t",19);

		graph.add("4","3",6);
		graph.add("4","t",6);

		graph.add("5","4",11);
		graph.add("5","t",16);

		graph.add("6","3",18);
		// graph.add("6","5",30);
		graph.add("6","7",5);

		// graph.add("7","5",20);
		graph.add("7","t",44);
				
		return graph;
	}	

	public static final double[][] DISCONNECTED_DISTANCE = {
	    // s   2   3   4   5   6   7   t
		{  0,  9,INF,INF,INF, 14, 15,INF},
		{INF,  0,INF,INF,INF,INF,INF,INF},
		{INF,INF,  0, 13,  2,INF,INF, 18},
		{INF,INF,  6,  0,  8,INF,INF,  6},
		{INF,INF, 17, 11,  0,INF,INF, 16},
		{INF,INF,INF,INF,INF,  0,  5,INF},
		{INF,INF,INF,INF,INF,INF,  0,INF},
		{INF,INF,INF,INF,INF,INF,INF,  0}
	};
	
	// Two connected components: {s,2,6,7} {3,4,5,t}
	
	public static Network<String,Integer> weightedDisconnectedGraph ()
	{
		Network<String,Integer> graph = new ArrayNetwork<String,Integer>();
		
		graph.add("s");
		graph.add("2");
		graph.add("3"); 		
		graph.add("4"); 
		graph.add("5"); 
		graph.add("6");
		graph.add("7");
		graph.add("t");
		
		graph.add("s","2",9);
		graph.add("s","6",14);
		graph.add("s","7",15);
		
		//graph.add("2","3",23);
		
		graph.add("3","5",2);
		graph.add("3","t",19);

		graph.add("4","3",6);
		graph.add("4","t",6);

		graph.add("5","4",11);
		graph.add("5","t",16);

		// graph.add("6","3",18);
		// graph.add("6","5",30);
		graph.add("6","7",5);

		// graph.add("7","5",20);
		// graph.add("7","t",44);
				
		return graph;
	}		

	
	public static final double[][] NEGATIVE_DISTANCE = {
	    // s   a   b   t
		{  0, -4, -1, -6},
		{INF,  0,INF, -2},
		{INF,INF,  0, -3},
		{INF,INF,INF,  0}
	};
	
	
	public static Network<String,Integer> negativeWeightsGraph ()
	{
		Network<String,Integer> graph = new ArrayNetwork<String,Integer>();
		
		graph.add("s");
		graph.add("a");
		graph.add("b");
		graph.add("t");
		
		graph.add("s","a",-4);
		graph.add("s","b",-1);

		graph.add("a","t",-2);
		graph.add("b","t",-3);
				
		return graph;
	}		

	
	public static Network<String,Integer> negativeCycleGraph ()
	{
		Network<String,Integer> graph = new ArrayNetwork<String,Integer>();
		
		graph.add("a");
		graph.add("b");
		graph.add("c");
		graph.add("d");
		
		graph.add("a","b",-1);
		graph.add("b","c",-2);
		graph.add("c","d",-3);
		graph.add("d","a",-4);
				
		return graph;
	}		


	
}

