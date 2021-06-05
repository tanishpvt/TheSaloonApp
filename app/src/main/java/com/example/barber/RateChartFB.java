package com.example.barber;

public class RateChartFB {
    public String saloon_name,general_cut,buzz_cut,combover,lowfade,trim,cleanshave,facemassage,classicmassage,Therapeuticmassage;
    public String hair_straightining,facial,manicure,padicure,spa;
    RateChartFB()
    {

    }

    public RateChartFB(String saloon_name,String general_cut, String buzz_cut, String combover, String lowfade, String trim, String cleanshave, String facemassage, String classicmassage, String therapeuticmassage, String hair_straightining, String facial, String manicure, String padicure, String spa) {
        this.saloon_name=saloon_name;
        if(Integer.parseInt(general_cut.trim())==0)
            general_cut="N.A";
        this.general_cut = general_cut;
        if(Integer.parseInt(buzz_cut.trim())==0)
            buzz_cut="N.A";
        this.buzz_cut = buzz_cut;
        if(Integer.parseInt(combover.trim())==0)
            combover="N.A";
        this.combover = combover;
        if(Integer.parseInt(lowfade.trim())==0)
            lowfade="N.A";
        this.lowfade = lowfade;
        if(Integer.parseInt(trim.trim())==0)
            trim="N.A";
        this.trim = trim;
        if(Integer.parseInt(cleanshave.trim())==0)
            cleanshave="N.A";
        this.cleanshave = cleanshave;
        if(Integer.parseInt(facemassage.trim())==0)
            facemassage="N.A";
        this.facemassage = facemassage;
        if(Integer.parseInt(classicmassage.trim())==0)
            classicmassage="N.A";
        this.classicmassage = classicmassage;
        if(Integer.parseInt(therapeuticmassage.trim())==0)
            therapeuticmassage="N.A";
        Therapeuticmassage = therapeuticmassage;
        if(Integer.parseInt(hair_straightining.trim())==0)
            hair_straightining="N.A";
        this.hair_straightining = hair_straightining;
        if(Integer.parseInt(facial.trim())==0)
            facial="N.A";
        this.facial = facial;
        if(Integer.parseInt(manicure.trim())==0)
            manicure="N.A";
        this.manicure = manicure;
        if(Integer.parseInt(padicure.trim())==0)
            padicure="N.A";
        this.padicure = padicure;
        if(Integer.parseInt(spa.trim())==0)
            spa="N.A";
        this.spa = spa;
    }
}
