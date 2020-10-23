package com.soubao.common.service;

public class SoubaoService {
    public String sayWhat;
    public String toWho;
    public SoubaoService(String sayWhat, String toWho){
        this.sayWhat = sayWhat;
        this.toWho = toWho;
    }
    public String say(){
        return this.sayWhat + "!  " + toWho;
    }
}
