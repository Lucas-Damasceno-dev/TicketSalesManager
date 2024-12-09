package com.lucas.ticketsalesmanager.models;


public enum Languages {
    PT_BR("/com/lucas/ticketsalesmanager/lang/labels-pt-br.json"),
    EN_US("/com/lucas/ticketsalesmanager/lang/labels-en-us.json");
    
    private final String fileName;
    
    private Languages(String fileName)
    {
        this.fileName = fileName;
    }
    public String getFileName()
    {
        return this.fileName;
    }
}
