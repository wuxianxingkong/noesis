package ikor.util;

// Title:       Benchmarking
// Version:     1.0
// Copyright:   1998
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.Date;

// Cronometraje
// ------------

public class Benchmark
{
  long   begin;
  long   total;
  String title;
  int    count;

  // Constructor

  public Benchmark ( String string )
  {
    title = string;
  }

  public Benchmark()
  {
    this( "" );
  }

  // Start

  public void start()
  {
    begin = (new Date()).getTime();
  }

  // Stop

  public void stop()
  {
    total += ((new Date()).getTime() - begin);
  }

  // Tiempo total en milisegundos

  public long milliseconds()
  {
    return total;
  }

  // Salida

  public String toString()
  {
    return title + " " + total + " ms";
  }

  // Comparativa

  public float ratio ( Benchmark benchmark )
  {
    return ((float) total) / ((float) benchmark.total);
  }
}
