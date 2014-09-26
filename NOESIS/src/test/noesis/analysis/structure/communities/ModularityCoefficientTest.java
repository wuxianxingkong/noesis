package test.noesis.analysis.structure.communities;

// Title:       Modularity Coefficient Test
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com & aarr90@gmail.com

import static org.junit.Assert.*;

import noesis.analysis.NodeScore;
import noesis.analysis.structure.communities.ModularityCoefficient;

import org.junit.Before;
import org.junit.Test;

/**
 * Modularity Coefficient Test
 * 
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
public class ModularityCoefficientTest 
{
    private static double EPSILON = 1e-4;
    
    private CliqueNetwork n3x3;
    private NodeScore a3x3;
    
    @Before
    public void setUp() 
    {
        n3x3 = new CliqueNetwork(3,3);
        a3x3 = new NodeScore("cluster assignment", n3x3);

        for (int i = 0; i < a3x3.size(); ++i)
            a3x3.set(i, (i/3)+1);
    }
    
    @Test
    public void test3x3() 
    {
        ModularityCoefficient modularity = new ModularityCoefficient(n3x3,a3x3);
        assertEquals(0.41666, modularity.overallValue(), EPSILON);
    }    
}
