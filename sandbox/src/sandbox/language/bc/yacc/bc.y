%{
   /* Pila de atributos sintetizados */

   typedef union {
     long                   entero;
     char                  *cadena;
     DescriptorDeTipo      descrTipo;
     DescriptorDeArray     descrArray;
     DescriptorDeExpresion descrExpresion;
     DescriptorDeSentencia descrSentencia;
     ListaDeExpresiones    listaExpresiones;
   } EntradaPAS;

   #define YYSTYPE EntradaPAS

   /* Tabla de s¡mbolos */

   extern EntradaTS TS[];

   /* N£mero de l¡nea */

   extern long      NumeroLinea;

   /* Ficheros de salida */

   FILE *fc = NULL;
   FILE *fh = NULL;

   /* Registro de activaci¢n */

   #define METADATA 2   // Enlace din mico + Profundidad


   /* Control de variables temporales */

   #define DIM_MEMORIA  2048
   #define MAX_NIVELES    16

   long tmp = DIM_MEMORIA-1;

   long nuevaVariableTemporal (void)
   {
     long dir = tmp;

     tmp--;

     return dir;
   }

   long nuevoBloqueTemporal (int dim)
   {
     tmp -= dim;

     return tmp+1;
   }

   void resetVariablesTemporales (void)
   {
     tmp = DIM_MEMORIA-1;
   }

   /* Control de etiquetas */

   long etiq = 0;

   long nuevaEtiqueta (void)
   {
     etiq++;
     return etiq;
   }


%}

%start programa

%token ABRIR_CORCHETE
%token ABRIR_PARENTESIS
%token ASIGNACION
%token CADENA
%token CERRAR_CORCHETE
%token CERRAR_PARENTESIS
%token COMA
%token CONSTANTE_BOOL
%token DOS_PUNTOS
%token NATURAL
%token ENTONCES
%token ENTRADA
%token HACER
%token HASTA
%token ID
%token INICIO
%token FIN
%token MIENTRAS
%token NO
%token O
%left  SIGNO
%left  OP_MULTIPLICATIVO
%token OP_RELACION
%token PUNTO
%token PUNTO_Y_COMA
%token PROCEDIMIENTO
%token RANGO
%token REPETIR
%token SALIDA
%token SI
%token SI_NO
%token TIPO_ARRAY
%token TIPO_SIMPLE
%token VAR

%%

programa        : procedimiento PUNTO
		  {
		  };

procedimiento   : PROCEDIMIENTO cabecera bloque
		  {
		   finProcedimientoTS();
		  };

/* ---------------------------- */
/* Cabecera de un procedimiento */
/* ---------------------------- */

cabecera        : ID
		  {
		    insertarProcedimientoTS($1.cadena);
		  }
		  parametros PUNTO_Y_COMA
		  {
		  }
		| error
		  {
		  };

/* ------ */
/* Bloque */
/* ------ */

bloque          : /* INICIO decl proc sentencias FIN */
		  INICIO
		  {
		    insertarBloqueTS();
		  }
		  declaracion procedimientos
		  {
		    // Cabecera

		    int pos = codigoProcedimiento();

		    if (pos!=-1)
		       fprintf (fc,"void p%d (void) // %s\n",TS[pos].des.pro.numero, TS[pos].des.pro.nombre);

		    // Marca de comienzo de bloque

		    fprintf (fc, "{\n");

		    // Registro de activaci¢n

		    fprintf (fc, "mem[TOPE] = BASE;\n");
		    fprintf (fc, "mem[TOPE+1] = %d;\n",profundidadBloque());
		    fprintf (fc, "BASE = TOPE;\n");
		    fprintf (fc, "TOPE = TOPE + %d;\n", dimBloque()+METADATA);

		  }
		  sentencias FIN
		  {
		    // Restauraci¢n del estado de la pila

		    fprintf (fc, "TOPE = BASE;\n");
		    fprintf (fc, "BASE = mem[BASE];\n");

		    // Final de la rutina
		    fprintf (fc, "}\n");

		    eliminarBloqueTS();
		  };

/* ------------------- */
/* Lista de sentencias */
/* ------------------- */

sentencias       : sentencia
		   {
		   }
		 | sentencias PUNTO_Y_COMA sentencia
		   {
		   };

/* ----------------------- */
/* Lista de procedimientos */
/* ----------------------- */

procedimientos   : /* Ninguno */
		 | procedimientos procedimiento PUNTO_Y_COMA
		   {
		   };


/* ------------- */
/* Declaraciones */
/* ------------- */

declaracion     : /* Nada */
		| VAR lista_decl FIN PUNTO_Y_COMA
		  {
		  };

lista_decl      : decl
		  {
		  }
		| lista_decl PUNTO_Y_COMA decl
		  {
		  };

decl            : /* Nada */
		| lista_id DOS_PUNTOS tipo
		  {
		    asignarTipoTS($3.descrTipo);

		    if ($3.descrTipo.datosarray) {
		       free(($3.descrTipo.datosarray)->minrango);
		       free(($3.descrTipo.datosarray)->maxrango);
		       free($3.descrTipo.datosarray);
		    }
		  }
		| error
		  {
		  };

lista_id        : ID
		  {
		    insertarVariableTS($1.cadena);
		  }
		| lista_id COMA ID
		  {
		    insertarVariableTS($3.cadena);
		  };

/* ---------- */
/* Sentencias */
/* ---------- */

sentencia       : /* Nada */
		| asignacion
		  {
		  }
		| entrada
		  {
		  }
		| salida
		  {
		  }
		| llamada_proc
		  {
		  }
		| if
		  {
		  }
		| while
		  {
		  }
		| repeat
		  {
		  }
		| bloque
		  {
		  }
		| error
		  {
		  };

/* ----------------------- */
/* Sentencia de asignaci¢n */
/* ----------------------- */

asignacion      : variable ASIGNACION expresion
		  {
		   int i,n;

		   if (  ($1.descrExpresion.tipobase != noasignado)
		      && ($3.descrExpresion.tipobase != noasignado) ) {

		      if (compararTipo($1.descrExpresion, $3.descrExpresion)) {

			 if (  ($1.descrExpresion.tipobase == entero )
			    || ($1.descrExpresion.tipobase == booleano ) ) {

			    // Copiamos el valor mem[$3.dir] en mem[mem[$1.dir]]

			    fprintf (fc, "mem[mem[%d]] = mem[%d];\n",
					 $1.descrExpresion.direccion,
					 $3.descrExpresion.direccion);

			 } else if ( $1.descrExpresion.tipobase == array ) {

			    // Copiar array de mem[$3.direccion] a mem[mem[$1.direccion]]

			    n = 1;

			    for (i=0; i<$1.descrExpresion.des.datosarray.numdimensiones; i++)
				n *= ($1.descrExpresion.des.datosarray.maxrango[i]-$1.descrExpresion.des.datosarray.minrango[i]+1);

			    for (i=0; i<n; i++)
				fprintf (fc, "mem[mem[%d]+%d] = mem[%d];\n",
					     $1.descrExpresion.direccion,
					     i,
					     $3.descrExpresion.direccion+i );
			 }
		      }
		   }
		   resetVariablesTemporales();
		  };


/* -------------------- */
/* Sentencia de entrada */
/* -------------------- */

entrada         : ENTRADA variable
		  {
		   if ($2.descrExpresion.tipobase == array)
		      printf ("L¡nea %ld: No se puede realizar una 'entrada' sobre un 'array' de forma directa.\n", NumeroLinea);

		   if (  ( $2.descrExpresion.tipobase == entero)
		      || ( $2.descrExpresion.tipobase == booleano) ) {

		      fprintf (fc, "mem[mem[%d]] = Entrada();\n",
				   $2.descrExpresion.direccion );
		   }

		   resetVariablesTemporales();
		  };


/* ------------------- */
/* Sentencia de salida */
/* ------------------- */

salida          : SALIDA expresion
		  {
		   if ($2.descrExpresion.tipobase == array) {

		      printf ("L¡nea %ld: No se puede realizar una 'salida' sobre un 'array' de forma directa.\n", NumeroLinea);

		   } else if ($2.descrExpresion.tipobase == entero) {

		      fprintf (fc, "SalidaEntero(mem[%d]);\n",
				   $2.descrExpresion.direccion );

		   } else if ($2.descrExpresion.tipobase == booleano) {

		      fprintf (fc, "SalidaBooleano(mem[%d]);\n",
				   $2.descrExpresion.direccion );
		   }

		   resetVariablesTemporales();
		  }
		| SALIDA CADENA
		  {
		    int i;

		    fprintf(fc, "SalidaCadena(\"");

		    for (i=1; i<strlen($2.cadena)-1; i++)
			fputc($2.cadena[i], fc);

		    fprintf(fc, "\");\n");

		    free ($2.cadena);
		  };

/* --------------------- */
/* Sentencia condicional */
/* --------------------- */

if      : then else
	  {
	    resetVariablesTemporales();
	    fprintf(fc, "etiq%d:\n", $1.descrSentencia.salida );
	  }

else    : /* Nada */
	| SI_NO sentencia
	  {
	    resetVariablesTemporales();
	  };

then    : ifcond ENTONCES sentencia
	  {
	    $$.descrSentencia.salida = nuevaEtiqueta();

	    fprintf (fc, "goto etiq%d;\n", $$.descrSentencia.salida );
	    fprintf (fc, "etiq%d:\n",      $1.descrSentencia.salida );

	    resetVariablesTemporales();
	  };

ifcond  : SI expresion
	  {
	    if ($2.descrExpresion.tipobase != booleano)
	       printf ("L¡nea %ld: La condici¢n del IF debe ser de tipo booleano.\n", NumeroLinea);

	    $$.descrSentencia.salida = nuevaEtiqueta();

	    fprintf (fc, "if (!mem[%d]) goto etiq%d;\n",
			 $2.descrExpresion.direccion,
			 $$.descrSentencia.salida );

	    resetVariablesTemporales();
	  };

/* ----------- */
/* Bucle WHILE */
/* ----------- */

while    : mientras wcond HACER sentencia
	   {
	     fprintf (fc, "goto etiq%d;\n", $1.descrSentencia.entrada );
	     fprintf (fc, "etiq%d:\n",      $2.descrSentencia.salida );
	   };

mientras : MIENTRAS
	   {
	     $$.descrSentencia.entrada = nuevaEtiqueta();

	     fprintf(fc, "etiq%d:\n", $$.descrSentencia.entrada );
	   }

wcond    : expresion
	   {
	     if ($1.descrExpresion.tipobase != booleano)
	       printf ("L¡nea %ld: Error sem ntico: Se necesita que 'expresi¢n' sea de tipo booleano.\n", NumeroLinea);

	     $$.descrSentencia.salida  = nuevaEtiqueta();

	     fprintf (fc, "if (!mem[%d]) goto etiq%d;\n",
			  $1.descrExpresion.direccion,
			  $$.descrSentencia.salida );

	     resetVariablesTemporales();
	   };

/* ------------ */
/* Bucle REPEAT */
/* ------------ */

repeat  : repetir sentencias HASTA expresion
	{
	   if ($4.descrExpresion.tipobase != booleano)
	      printf ("L¡nea %ld: 'expresi¢n' debe ser de tipo booleano.\n", NumeroLinea);

	   fprintf (fc, "if (!mem[%d]) goto etiq%d;\n",
			$4.descrExpresion.direccion,
			$1.descrSentencia.entrada );

	   resetVariablesTemporales();
	};

repetir : REPETIR
	  {
	    $$.descrSentencia.entrada = nuevaEtiqueta();

	    fprintf(fc, "etiq%d:\n", $$.descrSentencia.entrada );
	  }

/* -------------------------- */
/* Llamada a un procedimiento */
/* -------------------------- */

llamada_proc    : ID
		  {
		   int i;

		   i = encontrarEntrada($1.cadena);

		   if (i == -1) {

		      printf ("L¡nea %ld: Identificador de procedimientos '%s' no declarado.\n", NumeroLinea, $1.cadena);

		   } else if (TS[i].tipoentrada != procedimiento) {

		      printf ("L¡nea %ld: El identificador '%s' no corresponde a un procedimiento.\n", NumeroLinea, $1.cadena);

		   } else if (TS[i].des.pro.numparametros != 0) {

		      printf ("L¡nea %ld: Se esperaban %d par metros, encontrados 0.\n",  (long)NumeroLinea, (int)TS[i].des.pro.numparametros );

		   } else { // Realizar la llamada al procedimiento

		      fprintf (fc, "p%d ();\n", TS[i].des.pro.numero );

		   }
		  }
		| ID ABRIR_PARENTESIS lista_expr CERRAR_PARENTESIS
		  {
		   int i, j, k, bien, n;

		   char *tipo[] = { "no asignado", "entero", "booleano", "array" };

		   i = encontrarEntrada($1.cadena);

		   if (i == -1) {

		      printf ("L¡nea %ld: Identificador de procedimiento '%s' no declarado.\n", NumeroLinea, $1.cadena);

		   } else if (TS[i].tipoentrada != procedimiento) {

		      printf ("L¡nea %ld: El identificador '%s' no corresponde a un procedimiento.\n", NumeroLinea, $1.cadena);

		   } else if ($3.listaExpresiones.num == 0) {

		      /* Nada: La lista est  mal */

		   } else if (TS[i].des.pro.numparametros != $3.listaExpresiones.num) {

		      printf ("L¡nea %ld: Se esperaban %d par metros, encontrados %d.\n", NumeroLinea, TS[i].des.pro.numparametros, $3.listaExpresiones.num);

		   } else {

		      /* Paso de par metros */

		      for (j = 0; j < TS[i].des.pro.numparametros; j++) {

			  if (TS[i+j+1].des.par.tipo.tipobase != $3.listaExpresiones.lista[j].tipobase) {

			     printf ("L¡nea %ld: Par metro %d incorrecto. Se esperaba '%s' y se encontr¢ '%s'.\n", NumeroLinea, (j+1), tipo[TS[i+j+1].des.par.tipo.tipobase], tipo[$3.listaExpresiones.lista[j].tipobase]);

			  /*
			  } else if (  (TS[i+j+1].des.par.paso == referencia)
				    && ($3.listaExpresiones.lista[j].tipoexpr == exp) ) {

			     printf ("L¡nea %ld: Par metro %d incorrecto. Un par metro por referencia debe ser una variable.\n", NumeroLinea, (j+1));
			  */

			  } else if (  ( $3.listaExpresiones.lista[j].tipobase == entero )
				    || ( $3.listaExpresiones.lista[j].tipobase == booleano ) ) {

			     // Par metro entero o booleano

			     fprintf (fc, "mem[TOPE+%d] = mem[%d];\n",
					  TS[i+j+1].des.par.offset + METADATA,
					  $3.listaExpresiones.lista[j].direccion );

			  } else if ($3.listaExpresiones.lista[j].tipobase == array) {

				 if ($3.listaExpresiones.lista[j].des.datosarray.numdimensiones == TS[i+j+1].des.par.tipo.datosarray->numdimensiones) {

				    bien = 1;
				    k = 0;

				    while (  bien
					  && (k < $3.listaExpresiones.lista[j].des.datosarray.numdimensiones)) {

					  if (  ( $3.listaExpresiones.lista[j].des.datosarray.maxrango[k]
						- $3.listaExpresiones.lista[j].des.datosarray.minrango[k] )
					     != ( TS[i+j+1].des.par.tipo.datosarray->maxrango[k]
						- TS[i+j+1].des.par.tipo.datosarray->minrango[k] ) ) {

					     printf ("L¡nea %ld: Intent¢ asignar/operar dos arrays con diferente tama¤o en la %d¦ dimensi¢n.", NumeroLinea, k+1);
					     bien = 0;
					  }
					  k++;
				    }

				    if (bien) {  // Par metro de tipo array

				       n = 1;

				       for (k=0; k<$3.listaExpresiones.lista[j].des.datosarray.numdimensiones; k++)
					   n *= ( $3.listaExpresiones.lista[j].des.datosarray.maxrango[k]
						- $3.listaExpresiones.lista[j].des.datosarray.minrango[k] + 1 );

				       for (k=0; k<n; k++)
					   fprintf (fc, "mem[TOPE+%d] = mem[%d];\n",
							TS[i+j+1].des.par.offset + METADATA + k,
							$3.listaExpresiones.lista[j].direccion + k );
				    }

				 } else {

				    printf ("L¡nea %ld: Intent¢ pasar como par metro un array de dimensi¢n err¢nea.\n", NumeroLinea);

				 }
			  }
		      }

		      /* Llamada al procedimiento */

		      fprintf (fc, "p%d ();\n", TS[i].des.pro.numero );
		   }

		  };

lista_expr      : expresion
		  {
		   if ($1.descrExpresion.tipobase == noasignado)
		      $1.listaExpresiones.num = 0;
		   else {
		      $$.listaExpresiones.num = 1;
		      $$.listaExpresiones.lista = malloc(sizeof(DescriptorDeExpresion));
		      $$.listaExpresiones.lista[0].tipobase  = $1.descrExpresion.tipobase;
		      $$.listaExpresiones.lista[0].tipoexpr  = $1.descrExpresion.tipoexpr;
		      $$.listaExpresiones.lista[0].direccion = $1.descrExpresion.direccion;
		      if ($1.descrExpresion.tipobase == array) {
			 $$.listaExpresiones.lista[0].des.datosarray.numdimensiones = $1.descrExpresion.des.datosarray.numdimensiones;
			 $$.listaExpresiones.lista[0].des.datosarray.minrango = $1.descrExpresion.des.datosarray.minrango;
			 $$.listaExpresiones.lista[0].des.datosarray.maxrango = $1.descrExpresion.des.datosarray.maxrango;
		      }
		   }
		  }
		| lista_expr COMA expresion
		  {
		   int i;
		   if (($3.descrExpresion.tipobase == noasignado) ||
		       ($1.listaExpresiones.num == 0))
		      $$.listaExpresiones.num = 0;
		   else {
		      $$.listaExpresiones.num = $1.listaExpresiones.num + 1;
		      $$.listaExpresiones.lista = (DescriptorDeExpresion*) malloc (sizeof(DescriptorDeExpresion) * $$.listaExpresiones.num);
		      for (i = 0; i < $$.listaExpresiones.num - 1; i++) {
			  $$.listaExpresiones.lista[i].tipobase  = $1.listaExpresiones.lista[i].tipobase;
			  $$.listaExpresiones.lista[i].tipoexpr  = $1.listaExpresiones.lista[i].tipoexpr;
			  $$.listaExpresiones.lista[i].direccion = $1.listaExpresiones.lista[i].direccion;
			  if ($$.listaExpresiones.lista[i].tipobase == array) {
			     $$.listaExpresiones.lista[i].des.datosarray.numdimensiones = $1.listaExpresiones.lista[i].des.datosarray.numdimensiones;
			     $$.listaExpresiones.lista[i].des.datosarray.minrango       = $1.listaExpresiones.lista[i].des.datosarray.minrango;
			     $$.listaExpresiones.lista[i].des.datosarray.maxrango       = $1.listaExpresiones.lista[i].des.datosarray.maxrango;
			  }
		      };
		      $$.listaExpresiones.lista[i].tipobase  = $3.descrExpresion.tipobase;
		      $$.listaExpresiones.lista[i].tipoexpr  = $3.descrExpresion.tipoexpr;
		      $$.listaExpresiones.lista[i].direccion = $3.descrExpresion.direccion;
		      if ($$.listaExpresiones.lista[i].tipobase == array) {
			 $$.listaExpresiones.lista[i].des.datosarray.numdimensiones = $3.descrExpresion.des.datosarray.numdimensiones;
			 $$.listaExpresiones.lista[i].des.datosarray.minrango       = $3.descrExpresion.des.datosarray.minrango;
			 $$.listaExpresiones.lista[i].des.datosarray.maxrango       = $3.descrExpresion.des.datosarray.maxrango;
		      }
		   }
		  };


/* ----------- */
/* Expresiones */
/* ----------- */

expresion : expresion_simple
	    {
	       $$.descrExpresion.tipobase  = $1.descrExpresion.tipobase;
	       $$.descrExpresion.tipoexpr  = $1.descrExpresion.tipoexpr;
	       $$.descrExpresion.direccion = $1.descrExpresion.direccion;

	       if ($$.descrExpresion.tipobase == array) {
		  $$.descrExpresion.des.datosarray.numdimensiones = $1.descrExpresion.des.datosarray.numdimensiones;
		  $$.descrExpresion.des.datosarray.maxrango       = $1.descrExpresion.des.datosarray.maxrango;
		  $$.descrExpresion.des.datosarray.minrango       = $1.descrExpresion.des.datosarray.minrango;
	       }
	    }
	  | expresion_simple OP_RELACION expresion_simple
	    {
	       $$.descrExpresion.tipoexpr  = exp;
	       $$.descrExpresion.direccion = $1.descrExpresion.direccion;

	       if (($1.descrExpresion.tipobase == noasignado) ||
		   ($3.descrExpresion.tipobase == noasignado)) {
		    $$.descrExpresion.tipobase = noasignado;

	       } else {

		  switch ($2.entero) {

		     case 3: // =
		     case 4: // <>

			if (  (  ($1.descrExpresion.tipobase == booleano )
			      && ($3.descrExpresion.tipobase == booleano ) )
			   || (  ($1.descrExpresion.tipobase == entero )
			      && ($3.descrExpresion.tipobase == entero ) ) ) {

			   $$.descrExpresion.tipobase = booleano;

			   if ($2.entero==3) {

			      fprintf (fc, "mem[%d] = ( mem[%d] == mem[%d] );\n",
					   $1.descrExpresion.direccion,
					   $1.descrExpresion.direccion,
					   $3.descrExpresion.direccion );

			   } else {

			      fprintf (fc, "mem[%d] = ( mem[%d] != mem[%d] );\n",
					   $1.descrExpresion.direccion,
					   $1.descrExpresion.direccion,
					   $3.descrExpresion.direccion );
			   }

			 } else {

			   $$.descrExpresion.tipobase = noasignado;
			   printf ("L¡nea %ld: Se esperaba expresi¢n entera.\n", NumeroLinea);
			 }
			 break;

		     case 1 : // <
		     case 2 : // <=
		     case 5 : // >
		     case 6 : // >=

			if (  ($1.descrExpresion.tipobase != entero)
			   || ($3.descrExpresion.tipobase != entero) ) {

			   $$.descrExpresion.tipobase = noasignado;
			   printf ("L¡nea %ld: Se esperaba expresi¢n entera.\n", NumeroLinea);

			} else {

			   $$.descrExpresion.tipobase = booleano;

			   if ($2.entero==1) {

			      fprintf (fc, "mem[%d] = ( mem[%d] < mem[%d] );\n",
					   $1.descrExpresion.direccion,
					   $1.descrExpresion.direccion,
					   $3.descrExpresion.direccion );

			   } else if ($2.entero==5) {

			      fprintf (fc, "mem[%d] = ( mem[%d] > mem[%d] );\n",
					   $1.descrExpresion.direccion,
					   $1.descrExpresion.direccion,
					   $3.descrExpresion.direccion );

			   } else if ($2.entero==2) {

			      fprintf (fc, "mem[%d] = ( mem[%d] <= mem[%d] );\n",
					   $1.descrExpresion.direccion,
					   $1.descrExpresion.direccion,
					   $3.descrExpresion.direccion );

			   } else { // $2.entero == 6

			      fprintf (fc, "mem[%d] = ( mem[%d] >= mem[%d] );\n",
					   $1.descrExpresion.direccion,
					   $1.descrExpresion.direccion,
					   $3.descrExpresion.direccion );
			   }
			}
			break;
		  }

	       }

	    };


expresion_simple        : termino
			  {
			     $$.descrExpresion.tipobase  = $1.descrExpresion.tipobase;
			     $$.descrExpresion.tipoexpr  = $1.descrExpresion.tipoexpr;
			     $$.descrExpresion.direccion = $1.descrExpresion.direccion;

			     if ($$.descrExpresion.tipobase == array) {
				$$.descrExpresion.des.datosarray.numdimensiones = $1.descrExpresion.des.datosarray.numdimensiones;
				$$.descrExpresion.des.datosarray.maxrango       = $1.descrExpresion.des.datosarray.maxrango;
				$$.descrExpresion.des.datosarray.minrango       = $1.descrExpresion.des.datosarray.minrango;
			     }
			  }
			| SIGNO termino   %prec SIGNO
			  {
			   int k,n;

			   $$.descrExpresion.tipobase  = $2.descrExpresion.tipobase;
			   $$.descrExpresion.direccion = $2.descrExpresion.direccion;
			   $$.descrExpresion.tipoexpr  = exp;

			   if ($$.descrExpresion.tipobase == booleano) {

			      printf ("L¡nea %ld: No se puede aplicar (+|-) a un 'booleano'.\n", NumeroLinea);

			   } else if ($$.descrExpresion.tipobase == entero) {

			      if ($1.entero==-1) {
				 fprintf (fc, "mem[%d] = -mem[%d];\n",
					 $2.descrExpresion.direccion,
					 $2.descrExpresion.direccion);
			      }

			   } else if ($$.descrExpresion.tipobase == array) {

			      $$.descrExpresion.des.datosarray.numdimensiones = $2.descrExpresion.des.datosarray.numdimensiones;
			      $$.descrExpresion.des.datosarray.minrango = $2.descrExpresion.des.datosarray.minrango;
			      $$.descrExpresion.des.datosarray.maxrango = $2.descrExpresion.des.datosarray.maxrango;

			      // Cambiar de signo el array

			      if ($1.entero==-1) {

				 n = 1;

				 for (k=0; k<$$.descrExpresion.des.datosarray.numdimensiones; k++)
				     n *= ( $$.descrExpresion.des.datosarray.maxrango[k]
					  - $$.descrExpresion.des.datosarray.minrango[k] + 1 );

				 for (k=0; k<n; k++)
				     fprintf (fc, "mem[%d] = - mem[%d];\n",
						  $$.descrExpresion.direccion + k,
						  $$.descrExpresion.direccion + k );
			      }
			   }
			  }
			| expresion_simple SIGNO termino
			  {
			   int k,n;

			   $$.descrExpresion.tipoexpr = exp;
			   $$.descrExpresion.direccion = $1.descrExpresion.direccion;

			   if (  ($1.descrExpresion.tipobase == noasignado)
			      || ($3.descrExpresion.tipobase == noasignado)) {

			      $$.descrExpresion.tipobase = noasignado;

			   } else if (  ($1.descrExpresion.tipobase == entero)
				     && ($3.descrExpresion.tipobase == entero)) {

			      $$.descrExpresion.tipobase = entero;

			      if ($2.entero==+1) {      // +

				 fprintf (fc, "mem[%d] = mem[%d] + mem[%d];\n",
					      $1.descrExpresion.direccion,
					      $1.descrExpresion.direccion,
					      $3.descrExpresion.direccion );

			      } else {                  // -

				 fprintf (fc, "mem[%d] = mem[%d] - mem[%d];\n",
					      $1.descrExpresion.direccion,
					      $1.descrExpresion.direccion,
					      $3.descrExpresion.direccion );
			      }

			   } else if (  ($1.descrExpresion.tipobase==array)
				     && ($3.descrExpresion.tipobase==array) ) {

			      if (compararTipo($1, $3)) { // arrays
				 $$.descrExpresion.tipobase  = array;
				 $$.descrExpresion.direccion = $1.descrExpresion.direccion;
				 $$.descrExpresion.des.datosarray.numdimensiones = $1.descrExpresion.des.datosarray.numdimensiones;
				 $$.descrExpresion.des.datosarray.minrango = $1.descrExpresion.des.datosarray.minrango;
				 $$.descrExpresion.des.datosarray.maxrango = $1.descrExpresion.des.datosarray.maxrango;

				 n = 1;

				 for (k=0; k<$$.descrExpresion.des.datosarray.numdimensiones; k++)
				     n *= ( $$.descrExpresion.des.datosarray.maxrango[k]
					  - $$.descrExpresion.des.datosarray.minrango[k] + 1 );

				 // Sumar o restar arrays

				 for (k=0; k<n; k++)
				     fprintf (fc, "mem[%d] = mem[%d] %c mem[%d];\n",
						  $$.descrExpresion.direccion + k,
						  $1.descrExpresion.direccion + k,
						  (($2.entero==+1)?'+':'-'),
						  $3.descrExpresion.direccion + k );

			      } else {

				 $$.descrExpresion.tipobase = noasignado;

			      }
			   }
			  }
			| expresion_simple O termino
			  {
			   $$.descrExpresion.tipoexpr  = exp;
			   $$.descrExpresion.direccion = $1.descrExpresion.direccion;

			   if (  ($1.descrExpresion.tipobase==booleano)
			      && ($3.descrExpresion.tipobase==booleano)  ) {

			      $$.descrExpresion.tipobase = booleano;

			      fprintf (fc, "mem[%d] = mem[%d] || mem[%d];\n",
					   $1.descrExpresion.direccion,
					   $1.descrExpresion.direccion,
					   $3.descrExpresion.direccion );
			   } else {
			      $$.descrExpresion.tipobase = noasignado;
			   }

			  };


termino : factor
	  {
	     $$.descrExpresion.tipobase  = $1.descrExpresion.tipobase;
	     $$.descrExpresion.tipoexpr  = $1.descrExpresion.tipoexpr;
	     $$.descrExpresion.direccion = $1.descrExpresion.direccion;

	     if ($$.descrExpresion.tipobase == array) {
		$$.descrExpresion.des.datosarray.numdimensiones = $1.descrExpresion.des.datosarray.numdimensiones;
		$$.descrExpresion.des.datosarray.minrango = $1.descrExpresion.des.datosarray.minrango;
		$$.descrExpresion.des.datosarray.maxrango = $1.descrExpresion.des.datosarray.maxrango;
	     }
	  }
	| termino OP_MULTIPLICATIVO factor
	  {
	     int i,j,k,n;
	     int x,y,z;
	     int tmp;

	     $$.descrExpresion.tipoexpr  = exp;
	     $$.descrExpresion.direccion = $1.descrExpresion.direccion;

	     switch ($2.entero) {

	       case 'y':
	       case 'Y':

		    if (  ($1.descrExpresion.tipobase==booleano)
		       && ($3.descrExpresion.tipobase==booleano) ) {

		       $$.descrExpresion.tipobase = booleano;

		       fprintf (fc, "mem[%d] = mem[%d] && mem[%d];\n",
			       $1.descrExpresion.direccion,
			       $1.descrExpresion.direccion,
			       $3.descrExpresion.direccion );
		    } else {
		       $$.descrExpresion.tipobase = noasignado;
		    }
		    break;

	       case '/': // Entre enteros

		    if (  ($1.descrExpresion.tipobase == noasignado)
		       || ($3.descrExpresion.tipobase == noasignado) ) {

		       $$.descrExpresion.tipobase = noasignado;

		    } else if (  ($1.descrExpresion.tipobase == entero)
			      && ($3.descrExpresion.tipobase == entero) ) {

		      $$.descrExpresion.tipobase = entero;

		      fprintf (fc, "mem[%d] = mem[%d] / mem[%d];\n",
				   $1.descrExpresion.direccion,
				   $1.descrExpresion.direccion,
				   $3.descrExpresion.direccion );

		    } else if (  ($1.descrExpresion.tipobase == array)
			      && ($3.descrExpresion.tipobase == entero) ) {

		       $$.descrExpresion.tipobase = array;
		       $$.descrExpresion.des.datosarray.numdimensiones = $1.descrExpresion.des.datosarray.numdimensiones;
		       $$.descrExpresion.des.datosarray.minrango = $1.descrExpresion.des.datosarray.minrango;
		       $$.descrExpresion.des.datosarray.maxrango = $1.descrExpresion.des.datosarray.maxrango;

		       n = 1;

		       for (k=0; k<$$.descrExpresion.des.datosarray.numdimensiones; k++)
			   n *= ( $$.descrExpresion.des.datosarray.maxrango[k]
				- $$.descrExpresion.des.datosarray.minrango[k] + 1 );

		       for (k=0; k<n; k++)
			   fprintf (fc, "mem[%d] = mem[%d] / mem[%d];\n",
				   $$.descrExpresion.direccion + k,
				   $1.descrExpresion.direccion + k,
				   $3.descrExpresion.direccion );
		    } else {

		       printf ("L¡nea %ld: La divisi¢n ha de ser entre enteros.\n", NumeroLinea);
		       $$.descrExpresion.tipobase = noasignado;

		    }
		    break;

	       case '*':

		    if (  ($1.descrExpresion.tipobase == noasignado)
		       || ($3.descrExpresion.tipobase == noasignado) ) {

		       $$.descrExpresion.tipobase = noasignado;

		    } else if (  ($1.descrExpresion.tipobase == booleano)
			      || ($3.descrExpresion.tipobase == booleano) ) {

		       printf ("L¡nea %ld: No se puede multiplicar un booleano.\n", NumeroLinea);
		       $$.descrExpresion.tipobase = noasignado;

		   } else if (  ($1.descrExpresion.tipobase == entero)
			     && ($3.descrExpresion.tipobase == entero) ) {

		       $$.descrExpresion.tipobase = entero;

		       fprintf (fc, "mem[%d] = mem[%d] * mem[%d];\n",
				    $1.descrExpresion.direccion,
				    $1.descrExpresion.direccion,
				    $3.descrExpresion.direccion );

		   } else if (  ($1.descrExpresion.tipobase == array)
			     && ($3.descrExpresion.tipobase == array) ) {

			     if (  ($1.descrExpresion.des.datosarray.numdimensiones == 2)
				&& ($3.descrExpresion.des.datosarray.numdimensiones == 2)) {

				if (  ( $1.descrExpresion.des.datosarray.maxrango[1]
				      - $1.descrExpresion.des.datosarray.minrango[1])
				   == ( $3.descrExpresion.des.datosarray.maxrango[0]
				      - $3.descrExpresion.des.datosarray.minrango[0])) {

				   // Matrices XxY e YxZ --> Matriz XxZ

				   x = $1.descrExpresion.des.datosarray.maxrango[0]
				     - $1.descrExpresion.des.datosarray.minrango[0] + 1;

				   y = $1.descrExpresion.des.datosarray.maxrango[1]
				     - $1.descrExpresion.des.datosarray.minrango[1] + 1;

				   z = $3.descrExpresion.des.datosarray.maxrango[1]
				     - $3.descrExpresion.des.datosarray.minrango[1] + 1;


				   $$.descrExpresion.tipobase  = array;
				   $$.descrExpresion.direccion = nuevoBloqueTemporal(x*z);
				   $$.descrExpresion.des.datosarray.numdimensiones = 2;

				   $$.descrExpresion.des.datosarray.maxrango = (int*) malloc(2*sizeof(int));
				   $$.descrExpresion.des.datosarray.minrango = (int*) malloc(2*sizeof(int));
				   $$.descrExpresion.des.datosarray.maxrango[0] = $1.descrExpresion.des.datosarray.maxrango[0];
				   $$.descrExpresion.des.datosarray.minrango[0] = $1.descrExpresion.des.datosarray.minrango[0];
				   $$.descrExpresion.des.datosarray.maxrango[1] = $3.descrExpresion.des.datosarray.maxrango[1];
				   $$.descrExpresion.des.datosarray.minrango[1] = $3.descrExpresion.des.datosarray.minrango[1];

				   tmp = nuevaVariableTemporal();

				   for (i=0; i<x; i++) {
				       for (j=0; j<z; j++) {

					   fprintf (fc, "mem[%d] = 0;\n",
							$$.descrExpresion.direccion + i*z + j );

					   for (k=0; k<y; k++) {

					       fprintf (fc, "mem[%d] = mem[%d] * mem[%d];\n",
							    tmp,
							    $1.descrExpresion.direccion + i*y + k,
							    $3.descrExpresion.direccion + k*z + j);

					       fprintf (fc, "mem[%d] = mem[%d] + mem[%d];\n",
							    $$.descrExpresion.direccion + i*z + j,
							    $$.descrExpresion.direccion + i*z + j,
							    tmp );
					   }
				       }
				   }

				} else {  // error ¡ndices

				   printf ("L¡nea %ld: Para poder multiplicar matrices ‚stas deben ser 'MxN' y 'NxP'. Encontrado '%dx%d' y '%dx%d'\n",
					   NumeroLinea,
					   $1.descrExpresion.des.datosarray.maxrango[0] - $1.descrExpresion.des.datosarray.minrango[0] + 1,
					   $1.descrExpresion.des.datosarray.maxrango[1] - $1.descrExpresion.des.datosarray.minrango[1] + 1,
					   $3.descrExpresion.des.datosarray.maxrango[0] - $3.descrExpresion.des.datosarray.minrango[0] + 1,
					   $3.descrExpresion.des.datosarray.maxrango[1] - $3.descrExpresion.des.datosarray.minrango[1] + 1);

				   $$.descrExpresion.tipobase = noasignado;
				}

			     } else {

				printf ("L¡nea %ld: No se pueden multiplicar matrices que no sean '2D'.\n", NumeroLinea);
				$$.descrExpresion.tipobase = noasignado;
			     }

		   } else {  // Producto de un array por un entero

		      if (  ($1.descrExpresion.tipobase == array )
			 && ($3.descrExpresion.tipobase == entero) ) {

			 $$.descrExpresion.tipobase = array;
			 $$.descrExpresion.direccion = $1.descrExpresion.direccion;
			 $$.descrExpresion.des.datosarray.numdimensiones = $1.descrExpresion.des.datosarray.numdimensiones;
			 $$.descrExpresion.des.datosarray.minrango = $1.descrExpresion.des.datosarray.minrango;
			 $$.descrExpresion.des.datosarray.maxrango = $1.descrExpresion.des.datosarray.maxrango;

			 n = 1;

			 for (k=0; k<$$.descrExpresion.des.datosarray.numdimensiones; k++)
			     n *= ( $$.descrExpresion.des.datosarray.maxrango[k]
				  - $$.descrExpresion.des.datosarray.minrango[k] + 1 );

			 for (k=0; k<n; k++)
			     fprintf (fc, "mem[%d] = mem[%d] * mem[%d];\n",
					  $$.descrExpresion.direccion + k,
					  $1.descrExpresion.direccion + k,
					  $3.descrExpresion.direccion );

		      } else if (  ($3.descrExpresion.tipobase == array )
				&& ($1.descrExpresion.tipobase == entero) ) {

			 $$.descrExpresion.tipobase = array;
			 $$.descrExpresion.direccion = $3.descrExpresion.direccion;
			 $$.descrExpresion.des.datosarray.numdimensiones = $3.descrExpresion.des.datosarray.numdimensiones;
			 $$.descrExpresion.des.datosarray.minrango = $3.descrExpresion.des.datosarray.minrango;
			 $$.descrExpresion.des.datosarray.maxrango = $3.descrExpresion.des.datosarray.maxrango;

			 n = 1;

			 for (k=0; k<$$.descrExpresion.des.datosarray.numdimensiones; k++)
			     n *= ( $$.descrExpresion.des.datosarray.maxrango[k]
				  - $$.descrExpresion.des.datosarray.minrango[k] + 1 );

			 for (k=0; k<n; k++)
			     fprintf (fc, "mem[%d] = mem[%d] * mem[%d];\n",
				     $$.descrExpresion.direccion + k,
				     $3.descrExpresion.direccion + k,
				     $1.descrExpresion.direccion );
		      }
		   }
		   break;

	       case 250:  // ú

		    if (compararTipo ($1, $3)) {
		       if ($1.descrExpresion.tipobase == array) {
			  $$.descrExpresion.tipobase = array;
			  $$.descrExpresion.tipoexpr = exp;
			  $$.descrExpresion.tipobase = array;
			  $$.descrExpresion.des.datosarray.numdimensiones = $1.descrExpresion.des.datosarray.numdimensiones;
			  $$.descrExpresion.des.datosarray.minrango = $1.descrExpresion.des.datosarray.minrango;
			  $$.descrExpresion.des.datosarray.maxrango = $1.descrExpresion.des.datosarray.maxrango;

			  n = 1;

			  for (k=0; k<$$.descrExpresion.des.datosarray.numdimensiones; k++)
			      n *= ( $$.descrExpresion.des.datosarray.maxrango[k]
				   - $$.descrExpresion.des.datosarray.minrango[k] + 1 );

			  for (k=0; k<n; k++)
			      fprintf (fc, "mem[%d] = mem[%d] * mem[%d];\n",
					   $$.descrExpresion.direccion + k,
					   $1.descrExpresion.direccion + k,
					   $3.descrExpresion.direccion + k);
		       } else {
			  $$.descrExpresion.tipobase = noasignado;
			  printf ("L¡nea %ld: Se esperaba 'array' ú 'array'.\n", NumeroLinea);
		       }
		    } else {
		       $$.descrExpresion.tipobase = noasignado;
		       printf ("L¡nea %ld: Se esperaba 'array' ú 'array'.\n", NumeroLinea);
		    }
		    break;
	     }
	  };



factor  : CONSTANTE_BOOL
	  {
	     $$.descrExpresion.tipobase  = booleano;
	     $$.descrExpresion.tipoexpr  = exp;
	     $$.descrExpresion.direccion = nuevaVariableTemporal();

	     fprintf (fc, "mem[%d] = %ld;\n",
			  $$.descrExpresion.direccion,
			  $1.entero );
	  }
	| NATURAL
	  {
	     $$.descrExpresion.tipobase  = entero;
	     $$.descrExpresion.tipoexpr  = exp;
	     $$.descrExpresion.direccion = nuevaVariableTemporal();

	     fprintf (fc, "mem[%d] = %ld;\n",
			  $$.descrExpresion.direccion,
			  $1.entero );
	  }
	| variable
	  {
	     int i,n;

	     $$.descrExpresion.tipobase  = $1.descrExpresion.tipobase;
	     $$.descrExpresion.tipoexpr  = exp; // var;
	     // $$.descrExpresion.direccion = $1.descrExpresion.direccion;

	     if (  ($$.descrExpresion.tipobase == entero)
		|| ($$.descrExpresion.tipobase == booleano) ) {

		// Copiar valor de mem[$1.direccion] a mem[mem[$$.direccion]]

		$$.descrExpresion.direccion = nuevaVariableTemporal();

		fprintf (fc, "mem[%d] = mem[mem[%d]];\n",
			     $$.descrExpresion.direccion,
			     $1.descrExpresion.direccion );

	     } else if ($$.descrExpresion.tipobase == array) {

		$$.descrExpresion.des.datosarray.numdimensiones = $1.descrExpresion.des.datosarray.numdimensiones;
		$$.descrExpresion.des.datosarray.minrango       = $1.descrExpresion.des.datosarray.minrango;
		$$.descrExpresion.des.datosarray.maxrango       = $1.descrExpresion.des.datosarray.maxrango;

		// Copiar array de mem[$1.direccion] a mem[mem[$$.direccion]]

		n = 1;

		for (i=0; i<$$.descrExpresion.des.datosarray.numdimensiones; i++)
		    n *= ($$.descrExpresion.des.datosarray.maxrango[i]-$$.descrExpresion.des.datosarray.minrango[i]+1);

		$$.descrExpresion.direccion = nuevoBloqueTemporal(n);

		for (i=0; i<n; i++)
		    fprintf (fc, "mem[%d] = mem[mem[%d]+%d];\n",
			     $$.descrExpresion.direccion + i ,
			     $1.descrExpresion.direccion ,
			     i );
	     }
	  }
	| NO factor
	  {
	     $$.descrExpresion.tipoexpr  = exp;
	     $$.descrExpresion.direccion = $2.descrExpresion.direccion;

	     if ($2.descrExpresion.tipobase==booleano) {

		$$.descrExpresion.tipobase = booleano;

		fprintf (fc, "mem[%d] = !mem[%d];\n",
			  $2.descrExpresion.direccion,
			  $2.descrExpresion.direccion);

	     } else {

		$$.descrExpresion.tipobase = noasignado;

	     }
	  }
	| ABRIR_PARENTESIS expresion CERRAR_PARENTESIS
	  {
	     $$.descrExpresion.tipobase  = $2.descrExpresion.tipobase;
	     $$.descrExpresion.tipoexpr  = $2.descrExpresion.tipoexpr;
	     $$.descrExpresion.direccion = $2.descrExpresion.direccion;

	     if ($$.descrExpresion.tipobase == array) {
		$$.descrExpresion.des.datosarray.numdimensiones = $2.descrExpresion.des.datosarray.numdimensiones;
		$$.descrExpresion.des.datosarray.minrango = $2.descrExpresion.des.datosarray.minrango;
		$$.descrExpresion.des.datosarray.maxrango = $2.descrExpresion.des.datosarray.maxrango;
	     }
	  };

/* ------------------ */
/* Acceso a variables */
/* ------------------ */

variable        : ID
		  {
		   int pos, j, etiq, tmp;

		   pos = buscarID($1.cadena);

		   if (pos == -1) {

		      printf ("L¡nea %ld: Identificador '%s' no declarado.\n", NumeroLinea, $1.cadena);
		      $$.descrExpresion.tipobase = noasignado;

		   } else {

		      $$.descrExpresion.tipoexpr = var;
		      $$.descrExpresion.tipobase = TipoBaseTS(pos);
		      $$.descrExpresion.direccion = nuevaVariableTemporal();

		      // Pasar a mem[$$.direccion] la direcci¢n de la variable

		      if (TS[pos].profundidad==profundidadBloque()) {

			 fprintf (fc, "mem[%d] = BASE+%d;\n",
				      $$.descrExpresion.direccion,
				      ( TS[pos].tipoentrada==variable)
					? TS[pos].des.var.offset + METADATA
					: TS[pos].des.par.offset + METADATA );

		      } else {  // Seguir enlaces

			  etiq = nuevaEtiqueta();
			  tmp  = nuevaVariableTemporal();

			  fprintf (fc, "mem[%d] = BASE;\n",
				       $$.descrExpresion.direccion );

			  fprintf (fc, "etiq%d:\n", etiq);

			  fprintf (fc, "mem[%d] = mem[mem[%d]];\n",
				       $$.descrExpresion.direccion,
				       $$.descrExpresion.direccion);

			  fprintf (fc, "mem[%d] = mem[%d] + 1;\n",
					tmp,
					$$.descrExpresion.direccion );

			  fprintf (fc, "mem[%d] = (mem[mem[%d]] == %d );\n",
				       tmp, tmp, TS[pos].profundidad );

			  fprintf (fc, "if (!mem[%d]) goto etiq%d;\n",
				       tmp, etiq);

			  fprintf (fc, "mem[%d] = mem[%d] + %d;\n",
				       $$.descrExpresion.direccion,
				       $$.descrExpresion.direccion,
				      ( TS[pos].tipoentrada==variable)
					? TS[pos].des.var.offset + METADATA
					: TS[pos].des.par.offset + METADATA );
		      }

		      // Arrays

		      if ($$.descrExpresion.tipobase == array) {

			 $$.descrExpresion.des.datosarray.numdimensiones = TS[pos].des.var.tipo.datosarray->numdimensiones;

			 /* Copiar los ¡ndices */

			 $$.descrExpresion.des.datosarray.maxrango = (int*) malloc($$.descrExpresion.des.datosarray.numdimensiones*sizeof(int));
			 $$.descrExpresion.des.datosarray.minrango = (int*) malloc($$.descrExpresion.des.datosarray.numdimensiones*sizeof(int));

			 for (j = 0; j < $$.descrExpresion.des.datosarray.numdimensiones; j++) {
			    $$.descrExpresion.des.datosarray.maxrango[j] = TS[pos].des.var.tipo.datosarray->maxrango[j];
			    $$.descrExpresion.des.datosarray.minrango[j] = TS[pos].des.var.tipo.datosarray->minrango[j];
			 }
		      }
		   }
		  }
		| ID ABRIR_CORCHETE lista_expr CERRAR_CORCHETE
		  {
		   int i, j;
		   int etiq, tmp;
		   int pos, tipo, d, nd, min, bien;
		   int dim[8];	// Array de 8 dimensiones, m s que suficiente

		   $$.descrExpresion.tipoexpr = exp;

		   pos = buscarID($1.cadena);

		   if (pos == -1) {

		      printf ("L¡nea %ld: Identificador '%s' no declarado.\n", NumeroLinea, $1.cadena);
		      $$.descrExpresion.tipobase = noasignado;

		   } else {

		      // Comprobar que list_expr == enteros

		      j = 0;
		      bien = 1;

		      while ((bien) && (j < $3.listaExpresiones.num)) {
			 if ($3.listaExpresiones.lista[j].tipobase != entero)
			    bien = 0;
			 j++;
		      }

		      if (!bien || j == 0) { // j == 0 por si viene un error en la lista

			 printf ("L¡nea %ld: La lista de ¡ndices del array debe estar formada por enteros.\n", NumeroLinea);
			 $$.descrExpresion.tipobase = noasignado;

		      } else {

			 tipo = TipoBaseTS(pos);

			 if ( tipo == array) {

			    if (TS[pos].tipoentrada == variable)
			       nd = TS[pos].des.var.tipo.datosarray->numdimensiones;
			    else
			       nd = TS[pos].des.par.tipo.datosarray->numdimensiones;

			    d = nd - $3.listaExpresiones.num;

			    if (d >= 0) {

			       $$.descrExpresion.direccion = nuevaVariableTemporal();

			       if (d==0) {
				  $$.descrExpresion.tipobase = entero;
			       } else {
				  $$.descrExpresion.tipobase = array;
				  $$.descrExpresion.des.datosarray.numdimensiones = d;
				  $$.descrExpresion.des.datosarray.maxrango = malloc (d*sizeof(int));
				  $$.descrExpresion.des.datosarray.minrango = malloc (d*sizeof(int));

				  for (j = 0; j < d; j++) {
				      if (TS[pos].tipoentrada == variable) {
					 $$.descrExpresion.des.datosarray.maxrango[j] = TS[pos].des.var.tipo.datosarray->maxrango[j + $3.listaExpresiones.num];
					 $$.descrExpresion.des.datosarray.minrango[j] = TS[pos].des.var.tipo.datosarray->minrango[j + $3.listaExpresiones.num];
				      } else {
					 $$.descrExpresion.des.datosarray.maxrango[j] = TS[pos].des.par.tipo.datosarray->maxrango[j + $3.listaExpresiones.num];
					 $$.descrExpresion.des.datosarray.minrango[j] = TS[pos].des.par.tipo.datosarray->minrango[j + $3.listaExpresiones.num];
				      }
				  }
			       }

			       // Obtener la direcci¢n de la "variable"
			       // -------------------------------------

			       // Pasar a mem[$$.direccion] la direcci¢n de la variable

			       if (TS[pos].profundidad==profundidadBloque()) {

				  fprintf (fc, "mem[%d] = BASE+%d;\n",
					       $$.descrExpresion.direccion,
					       ( TS[pos].tipoentrada==variable)
					       ? TS[pos].des.var.offset + METADATA
					       : TS[pos].des.par.offset + METADATA );

			       } else {  // Seguir enlaces

				  etiq = nuevaEtiqueta();
				  tmp  = nuevaVariableTemporal();

				  fprintf (fc, "mem[%d] = BASE;\n",
					       $$.descrExpresion.direccion );

				  fprintf (fc, "etiq%d:\n", etiq);

				  fprintf (fc, "mem[%d] = mem[mem[%d]];\n",
					       $$.descrExpresion.direccion,
					       $$.descrExpresion.direccion);

				  fprintf (fc, "mem[%d] = mem[%d] + 1;\n",
					       tmp,
					       $$.descrExpresion.direccion );

				  fprintf (fc, "mem[%d] = (mem[mem[%d]] == %d );\n",
					       tmp, tmp, TS[pos].profundidad );

				  fprintf (fc, "if (!mem[%d]) goto etiq%d;\n",
					       tmp, etiq);

				  fprintf (fc, "mem[%d] = mem[%d] + %d;\n",
					       $$.descrExpresion.direccion,
					       $$.descrExpresion.direccion,
					       ( TS[pos].tipoentrada==variable)
					       ? TS[pos].des.var.offset + METADATA
					       : TS[pos].des.par.offset + METADATA );
			       }

			       // Ya sabemos la direcci¢n inicial del array
			       // Ahora tenemos que calcular el offset debido
			       // a las expresiones entre corchetes

			       // array 'a' MxNxP:

			       // direcci¢n ( a[m,n,p] )
			       // = direcci¢n ( a )
			       //   + (m-Min(M))*N*P  ---> N*P => dim[2]
			       //   + (n-Min(N))*P    ---> P   => dim[1]
			       //   + (p-Min(P))      ---> 1   => dim[0]

			       dim[0] = 1;

			       if (TS[pos].tipoentrada == variable) {

				  for (i=1; i<nd; i++)
				      dim[i] = dim[i-1]
					     * ( TS[pos].des.var.tipo.datosarray->maxrango[nd-i]
					       - TS[pos].des.var.tipo.datosarray->minrango[nd-i]
					       + 1 );
			       } else {

				  for (i=1; i<nd; i++)
				      dim[i] = dim[i-1]
					     * ( TS[pos].des.par.tipo.datosarray->maxrango[nd-i]
					       - TS[pos].des.par.tipo.datosarray->minrango[nd-i]
					       + 1 );
			       }

			       // Indice i ==> mem[$3.listaExpresiones.lista[i].direccion]

			       for (i=0; i<$3.listaExpresiones.num; i++) {

				   if (TS[pos].tipoentrada == variable) {
				      min = TS[pos].des.var.tipo.datosarray->minrango[nd-1-i];
				   } else {
				      min = TS[pos].des.par.tipo.datosarray->minrango[nd-1-i];
				   }

				   // Obtenci¢n del offset dentro del ¡ndice

				   fprintf (fc, "mem[%d] = mem[%d] - %d;\n",
						$3.listaExpresiones.lista[i].direccion,
						$3.listaExpresiones.lista[i].direccion,
						min );

				   // Obtenci¢n del offset (en palabras)

				   if (i<nd-1)
				      fprintf (fc, "mem[%d] = mem[%d] * %d;\n",
						   $3.listaExpresiones.lista[i].direccion,
						   $3.listaExpresiones.lista[i].direccion,
						   dim[nd-1-i] );

				   // Desplazamiento

				   fprintf (fc, "mem[%d] = mem[%d] + mem[%d];\n",
						$$.descrExpresion.direccion,
						$$.descrExpresion.direccion,
						$3.listaExpresiones.lista[i].direccion );
			       }

			    }  else {
			       /* Error de dimensiones */
			       $$.descrExpresion.tipobase = noasignado;
			       printf ("L¡nea %ld: Error sem ntico: Intent¢ acceder a un array '%dD'como si fuera '%dD'.\n", NumeroLinea, nd, $3.listaExpresiones.num);
			    }

			 } else {
			    $$.descrExpresion.tipobase = noasignado;
			 }
		      }
		   }
		  };


/* ---------- */
/* Par metros */
/* ---------- */

parametros      : /* Nada */
		| ABRIR_PARENTESIS lista_par CERRAR_PARENTESIS
		  {
		  };

lista_par       : parametro
		  {
		  }
		| lista_par PUNTO_Y_COMA parametro
		  {
		  };

parametro       : lista_var DOS_PUNTOS tipo
		  {
		    asignarTipoTS($3.descrTipo);

		    if ($3.descrTipo.datosarray) {
		       free(($3.descrTipo.datosarray)->minrango);
		       free(($3.descrTipo.datosarray)->maxrango);
		       free($3.descrTipo.datosarray);
		    }
		  };

lista_var       : ID
		  {
		    insertarParametroTS($1.cadena,valor);
		  }
		| VAR ID
		  {
		    // insertarParametroTS($2.cadena,referencia);
		    insertarParametroTS($2.cadena,valor);
		    printf ("L¡nea %ld: AVISO: Paso de par metros por referencia no soportado.\n", NumeroLinea );
		  }
		| lista_var COMA ID
		  {
		    insertarParametroTS($3.cadena,valor);
		  }
		| lista_var COMA VAR ID
		  {
		    // insertarParametroTS($4.cadena,referencia);
		    insertarParametroTS($2.cadena,valor);
		    printf ("L¡nea %ld: AVISO: Paso de par metros por referencia no soportado.\n", NumeroLinea );
		  };

/* ----- */
/* Tipos */
/* ----- */

tipo    : TIPO_SIMPLE
	  {
	    $$.descrTipo.tipobase   = $1.entero;
	    $$.descrTipo.datosarray = NULL;
	  }
	| TIPO_ARRAY ABRIR_CORCHETE lista_rango CERRAR_CORCHETE
	  {
	    $$.descrTipo.tipobase   = array;
	    $$.descrTipo.datosarray = malloc (sizeof(DescriptorDeArray));
	    ($$.descrTipo.datosarray)->numdimensiones = $3.descrArray.numdimensiones;
	    ($$.descrTipo.datosarray)->minrango       = $3.descrArray.minrango;
	    ($$.descrTipo.datosarray)->maxrango       = $3.descrArray.maxrango;
	  };

entero : NATURAL
	 {
	   $$.entero = $1.entero;
	 }
       | SIGNO NATURAL
	 {
	   $$.entero = $1.entero * $2.entero;
	 };

lista_rango     : entero RANGO entero
		  {
		    $$.descrArray.numdimensiones = 1;
		    $$.descrArray.minrango = (int*)malloc(sizeof(int));
		    $$.descrArray.maxrango = (int*)malloc(sizeof(int));
		    $$.descrArray.minrango[0] = $1.entero;
		    $$.descrArray.maxrango[0] = $3.entero;
		  }
		| lista_rango COMA entero RANGO entero
		  {
		    int i;

		    $$.descrArray.numdimensiones = $1.descrArray.numdimensiones + 1;
		    $$.descrArray.minrango = (int*)malloc(sizeof(int)*($$.descrArray.numdimensiones));
		    $$.descrArray.maxrango = (int*)malloc(sizeof(int)*($$.descrArray.numdimensiones));

		    for (i=0; i<$$.descrArray.numdimensiones-1; i++) {
			$$.descrArray.minrango[i] = $1.descrArray.minrango[i];
			$$.descrArray.maxrango[i] = $1.descrArray.maxrango[i];
		    }

		    free($1.descrArray.minrango);
		    free($1.descrArray.maxrango);

		    $$.descrArray.minrango[$$.descrArray.numdimensiones-1] = $3.entero;
		    $$.descrArray.maxrango[$$.descrArray.numdimensiones-1] = $5.entero;
		  };
%%

#include "lexyy.c"
