package Back.Pasport;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public interface TPTC032 {

	default String getCategoryTPTC032Pipe(String TPTCcode, double operPress, double designtTemp, double maxdn) {
		 int  paspCat=0;
		 String dApp=null;
		 if (TPTCcode.toLowerCase().equals("г1")) {
			 if (maxdn <= 100.0 & maxdn > 25.0) {
				 if ((operPress * maxdn <= 100.0 & operPress > 1.0 & operPress <= 3.5) ||
						 (operPress > 0.05 & operPress <= 1.0) )
				 {
					 paspCat = 1;
				 }
			 }

			 if ((maxdn > 100.0 & maxdn <= 350.0 & operPress > 0.05 & operPress <= 1.0)||
					 (maxdn > 25.0 & maxdn <= 350.0 & operPress * maxdn > 100.0 &
							 operPress*maxdn <= 350.0 & operPress > 1.0 & operPress <= 3.5)||
					 (maxdn > 25.0 & maxdn <= 100.0 & operPress > 3.5) ) {
				 paspCat = 2;
			 }

			 if ((maxdn > 350.0  & operPress > 0.05 & operPress <= 1.0)||
					 (maxdn > 100.0 & maxdn <= 350.0 & operPress * maxdn > 350.0 & operPress > 1.0 & operPress <= 3.5)||
					 (maxdn > 100.0 & operPress > 3.5) ) {
				 paspCat=3;
			 }

			 if (operPress <= 0.05 && maxdn <= 32.0&& maxdn*operPress<=100.0) {
				 dApp="не распространяется";
			 }
			 
		 }

		 if (TPTCcode.toLowerCase().equals("г2")) {
			 if ((maxdn > 32.0 && operPress > 0.05 & operPress <= 3.2 && operPress*maxdn > 100.0 &&
					 operPress * maxdn <= 350.0)||
					 (maxdn > 32.0 && maxdn <= 100.0 && operPress > 3.2) ) {
				 paspCat = 1;
			 }
			 
			 if ((maxdn > 100.0 && operPress > 0.05 & operPress <= 3.2 && operPress * maxdn > 350.0 &&
					 operPress * maxdn <= 500.0)||
					 (maxdn > 100.0 && maxdn <= 250.0 & operPress > 3.2) ) {
				 paspCat = 2;
			 }

			 if ((maxdn > 250.0 && operPress > 3.2)||
					 (maxdn > 250.0 && operPress * maxdn > 500.0 && operPress > 0.05 && operPress <= 3.2) ) {
				 paspCat = 3;
			 }

			 if (operPress <= 0.05 && maxdn <= 25.0 && maxdn * operPress <= 200.0) {
				 dApp="не распространяется";
			 }
		 }
		 
		 if (TPTCcode.toLowerCase().equals("ж1")) {
			if (maxdn > 25.0 && operPress > 0.05 && operPress <= 1 && maxdn * operPress > 200){
				 paspCat = 1;
			 }
			if ((maxdn > 25.0 && operPress > 1.0 && operPress <= 8.0 && maxdn * operPress > 200)||
					(maxdn > 25.0 && operPress > 8.0 && operPress <= 50.0 && maxdn * operPress > 350)){
				 paspCat = 2;
			 }
			if (maxdn > 25.0 && operPress > 50.0){
				 paspCat = 3;
			 }

			 if (operPress <= 0.05 && maxdn <= 25.0 && maxdn * operPress <= 200.0) {
				 dApp = "не распространяется";
			 }
		 }
		 
		 if (TPTCcode.toLowerCase().equals("ж2")) {
			 if (maxdn > 200.0 && operPress > 1.0 && operPress <= 50.0 && operPress * maxdn > 500.0 ) {
				 paspCat = 1;
			 }
			 if (maxdn > 200.0 &&  operPress > 50.0) {
				 paspCat = 2;
			 }

			 if (operPress <= 1.0 && maxdn <= 200.0 && maxdn * operPress <= 500.0) {
				 dApp="не распространяется";
			 }
		 }
		 
		 
		 if (designtTemp > 400) {
			 paspCat++;
		 }
		 
		 if(dApp != null) {
			 return dApp;
		 }
		 else
		 return Integer.toString(paspCat);
	 }

	default String getCategoryTPTC032Vessel( String TPTCcodeI, double operPressI, double designtTempI,double maxdnI)
	{
		String TPTCCode = TPTCcodeI;
		try
		{
			double operPress = operPressI;
			double designtTemp = designtTempI;
			double maxDN = maxdnI;
			double p_v = operPress * maxDN;
			int num5 = 0;
			if (TPTCCode.toLowerCase().equals("г1"))
			{
				if (operPress > 0.05 && maxDN > 0.001 && p_v > 0.0025 && p_v <= 0.005)
				{
					num5 = 1;
				}

				//--------------------------------------------------------------------------

				if (operPress > 0.05 && maxDN > 0.001 && p_v > 0.005 && p_v <= 0.02)
				{
					num5 = 2;
				}

				//--------------------------------------------------------------------------

				if ((operPress > 20.0 && operPress <= 100.0 && maxDN > 0.0001 && maxDN <= 0.001) ||
						(operPress > 0.05 && maxDN > 0.001 && p_v > 0.02 && p_v <= 0.1))
				{
					num5 = 3;
				}

				//--------------------------------------------------------------------------

				if ((operPress > 100.0 && maxDN > 0.0001 && maxDN <= 0.001) ||
						(operPress > 0.05 && maxDN > 0.001 && p_v > 0.1))
				{
					num5 = 4;
				}
			}
			//--------------------------------------------------------------------------
			if (TPTCCode.toLowerCase().equals("г2"))
			{
				if (operPress > 0.05 && maxDN > 0.001 && p_v > 0.005 && p_v <= 0.02)
				{
					num5 = 1;
				}

				//--------------------------------------------------------------------------

				if (operPress > 0.05 && maxDN > 0.001 && p_v > 0.02 && p_v <= 0.1)
				{
					num5 = 2;
				}

				//--------------------------------------------------------------------------

				if ((operPress > 100.0 && operPress <= 300.0 && maxDN > 0.0001 && maxDN <= 0.001) ||
						(operPress > 0.05 && p_v > 0.1 && p_v <= 0.3 && maxDN > 0.001 && maxDN <= 1.0) ||
						(operPress > 0.05 && operPress < 0.4 && maxDN > 1.0))
				{
					num5 = 3;
				}

				//--------------------------------------------------------------------------

				if ((operPress > 300.0 && maxDN > 0.0001 && maxDN <= 0.001) ||
						(operPress > 0.4 && p_v > 0.3 && maxDN > 0.001 && maxDN <= 1.0) ||
						(operPress > 0.4 && maxDN > 1.0))
				{
					num5 = 4;
				}

			}

			if (TPTCCode.toLowerCase().equals("ж1"))
			{
				if (operPress > 0.05 && operPress <= 1.0 && p_v > 0.02 && maxDN > 0.01)
				{
					num5 = 1;
				}
				if ((operPress > 1.0 && operPress <= 50.0 && p_v > 0.02 && maxDN > 0.001) ||
						(operPress > 50.0 && maxDN > 0.0001 && maxDN <= 0.001))
				{
					num5 = 2;
				}
				if (operPress > 50.0 && maxDN > 0.001)
				{
					num5 = 3;
				}
			}
			if (TPTCCode.toLowerCase().equals("ж2"))
			{
				if (operPress > 1.0 && operPress <= 50.0 && p_v > 1.0 && maxDN > 0.01)
				{
					num5 = 1;
				}
				if ((operPress > 100.0 && maxDN > 0.0001 && maxDN <= 0.01) ||
						(operPress > 50.0 && p_v > 1.0 && maxDN > 0.01))
				{
					num5 = 2;
				}
			}
			String result = Double.toString(num5);
			if (designtTemp > 400.0)
			{
				result += " (ТЕМПЕРАТУРА ВЫШЕ 400. ПРОВЕРИТЬ МАРКУ СТАЛИ И ПЕРЕСЧИТАТЬ КАТЕГОРИЮ)";
			}
			return result;
		}
		catch (Exception e)
		{
			return "Ошибка";
		}
	}


	default void getFileList(String path, List<String> fileList){
		File[] files = new File(path).listFiles();
		for(File file: files){
			if(!file.isDirectory()){
				fileList.add(file.getPath());

			} else{
				getFileList(file.getPath(),fileList);
			}
		}
	}
}