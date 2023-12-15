package com.sgb.bibliotecakafka.models;

import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.util.Map;

public record AutorAWSSqs (Map<String, MessageAttributeValue> atributosMensaje) implements Autores{


    @Override
    public String getId() {
        return this.atributosMensaje.get("id").getStringValue();
    }

    @Override
    public String getNombre() {
        return this.atributosMensaje.get("nombre").getStringValue();
    }

    @Override
    public String getNacionalidad() {
        return this.atributosMensaje.get("nacionalidad").getStringValue();
    }

    @Override
    public String getFechaNacimiento() {
        return this.atributosMensaje.get("fechaNacimiento").getStringValue();
    }

    @Override
    public String getFechaDefuncion() {
        return this.atributosMensaje.get("fechaDefuncion").getStringValue();
    }



    @Override
    public String productoToString() {
        return "{" +
                "'id':'" + this.getId() + '\'' +
                ", 'nombre':'" + this.getNombre() + '\'' +
                ", 'marca':'" + this.getNacionalidad() + '\'' +
                ", 'precio':" + this.getFechaNacimiento() +'\'' +
                ", 'pesoNeto':" + this.getFechaDefuncion() +'\'' +
                '}';
    }
}
