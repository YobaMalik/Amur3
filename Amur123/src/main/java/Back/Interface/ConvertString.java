package Back.Interface;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public interface ConvertString {

    default double conv(String vaule) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        Number number = format.parse(vaule);
        return  number.doubleValue();
    }

    default int convToInt(String vaule) throws ParseException {
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        Number number = format.parse(vaule);
        return  number.intValue();
    }


    default double getMaxValue(String sValue)throws NumberFormatException {
        String tempString = sValue.replace(";", "/");
        tempString = tempString.replace(" ","");
        tempString = tempString.replace(",",".");
        String[] splt = tempString.split("/");
        double val = -100000.0;
        for (String s : splt) {
            if (this.stringToDouble(s)) {
                double tempVal = Double.parseDouble(s);
                val = Math.max(val, tempVal);
            }
        }

        return (val != -100000.0)? val: -100000.0;
    }

    default boolean stringToDouble(String value){
        try {
            Double.parseDouble(value);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }




}


