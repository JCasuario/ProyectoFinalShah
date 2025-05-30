package com.intranetdesarrollo.intranetback.modelos;

public class Mensaje {
    private String nombreEnvio;
    private String receiverId;
    private String content;


    //Constructores
    public Mensaje(String senderId, String receiverId, String content) {
        this.nombreEnvio = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }

    //Getters-setters
    public String getSenderId() {
        return nombreEnvio;
    }

    public void setSenderId(String senderId) {
        this.nombreEnvio = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
