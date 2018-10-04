package xyz.carosdrean.projects.medidors.pojo;

public class Frase {
    private String texto;
    private String id;

    public Frase() {
    }

    public Frase(String texto, String id) {
        this.texto = texto;
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
