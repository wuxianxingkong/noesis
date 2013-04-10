package test.ikor.model.data.annotations;

// Title:       Document
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


import ikor.model.data.annotations.DataType;
import ikor.model.data.annotations.Derived;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Key;
import ikor.model.data.annotations.Label;
import ikor.model.data.annotations.Persistent;

// Document
// --------------------------------

// v1.0 - 16/08/2006

@Persistent(id="DOCUMENT")
@Label("Documento")
@Description("Ejemplo de uso de asociaciones entre clases: Representación de documentos")
public class Document 
{

    @Key
    @Persistent(type=DataType.Integer)
    @Label("ID")
    @Description("Identificador del documento")
    protected int    id;

    @Persistent(type=DataType.String, size=256, nullable=false)
    @Label("Título")
    @Description("Título del documento")   
    protected String title;

    @Persistent(type=DataType.String, size=256)
    @Label("Autor")
    @Description("Autor(es) del documento")
    protected String author;

    @Persistent(type=DataType.String, size=256)
    @Label("Fuente")
    @Description("Origen del documento")
    protected String source;

    // Derived attribute
    @Label("Cita")
    @Description("Cita bibliográfica")
    @Derived("author+\": \"+title+\". \"+source")
    protected String citation;

    @Persistent
    @Label("Formato")
    @Description("Formato del documento")
    protected Format format;

    @Persistent
    @Label("Propietario")
    @Description("Usuario responsable del documento")
    protected User owner;
}
