package Back.Part45;

import java.util.HashMap;
import java.util.Map;


public class HeadTemplateClass {

    private String[] pipeHead={"Название док-та","Наименование элемента","Размеры","Марка стали","ГОСТ/ТУ","Ррасч","Трасч","D1","s1",
            "Smin1","[G]","s=max(sR+c1,Smin)","Ф","c","c1","c2","c11","c12","c2(запрос)","sR","[p]","9"};
    private String[] teeHead={"Название док-та","Наименование элемента","Размеры","Марка стали","ГОСТ/ТУ","Ррасч","Трасч","D1","s1","D2","S2",
            "Smin1","Smin2","[G]","s=max(sR+c1,Smin)","Ф","c","c1","c2","c11","c12","c2(запрос)","sR","[p]","12"};
    private String[] elbowHead={"Название док-та","Наименование элемента","Размеры","Марка стали","ГОСТ/ТУ","Ррасч","Трасч","D1","s1",
            "Smin1","[G]","s=max(sR+c1,Smin)","Ф","c","c1","c2","c11","c12","c2(запрос)","sR","[p]","9"};
    private String[] redHead={"Название док-та","Наименование элемента","Размеры","Марка стали","ГОСТ/ТУ","Ррасч","Трасч","D1","s1","D2","S2",
            "Smin1","Smin2","L перехода","K перехода","[G]","s=max(sR+c1,Smin)","Ф","c","c1","c2","c11","c12","c2(запрос)","sR","[p]","14"};
    private String[] head={"Название док-та","Наименование элемента","Размеры","Марка стали","ГОСТ/ТУ","Ррасч","Трасч","D1","s1","D2","S2",
            "Smin1","Smin2","[G]","s=max(sR+c1,Smin)","Ф","c","c1","c2","c11","c12","c2(запрос)","sR","[p]","12"};

    private String[] blindHead={"Название док-та","Наименование элемента","Размеры","Марка стали","ГОСТ/ТУ","Ррасч","Трасч","D1","s1","D2","S2",
            "Smin1","Smin2","L перехода","K перехода","[G]","s=max(sR+c1,Smin)","Ф","c","c1","c2","c11","c12","c2(запрос)","sR","[p]","5"};

    Map<String, String[]> headMap=new HashMap<>();
    public HeadTemplateClass() {
        headMap.put("трубы",this.pipeHead);
        headMap.put("отводы", this.elbowHead);
        headMap.put("тройники", this.teeHead);
        headMap.put("переходы", this.redHead);
        headMap.put("заглушки", this.blindHead);
        headMap.put("Result", this.head);
    }

    public String[] getHeadTemplate(String elemName) {

        String[] array=null;

        for(Map.Entry<String, String[]> entr:headMap.entrySet()) {
            if(entr.getKey().compareTo(elemName)==0) {
                array=entr.getValue();
            }
        }

        return array;
    }


}
