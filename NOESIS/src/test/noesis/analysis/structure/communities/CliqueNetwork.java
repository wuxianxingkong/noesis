package test.noesis.analysis.structure.communities;

// Title:       Clique Network
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com - aarr90@gmail.com

import noesis.Attribute;
import noesis.AttributeNetwork;

/**
 * Clique Network
 *
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
public class CliqueNetwork extends AttributeNetwork 
{
	public CliqueNetwork (int numCliques, int nodesPerClique) 
	{
		// Node Attribute ID
		addNodeAttribute(new Attribute("id", new ikor.model.data.IntegerModel()));

		// Nodes
		for (int i = 0; i < numCliques; ++i) {
			for (int j = 0; j < nodesPerClique; ++j) {
				this.add(i * nodesPerClique + j);
				setNodeAttribute("id", i * nodesPerClique + j, Integer.toString(i * nodesPerClique + j));
			}
		}

		// Links
		int mod = numCliques * nodesPerClique;
		int plus = 0;
		if (nodesPerClique > 1) {
			plus = 1;
		}
		for (int i = 0; i < numCliques; ++i) {
			for (int j = 0; j < nodesPerClique; ++j) {
				int source = i * nodesPerClique + j;
				// Links between clique nodes
				for (int k = 1; k < nodesPerClique - j; ++k) { 
					int destination = source + k;
					this.add2(source, destination);
				}
			}
			// Link between different cliques
			if (numCliques > 2 || (numCliques == 2 && (nodesPerClique >= 2 || i == 0))) {
				int source = i * nodesPerClique;
				int destination = ((i + 1) * nodesPerClique + plus) % mod;
				this.add2(source, destination);
			}
		}
	}
}
