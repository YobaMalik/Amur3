package Back.Part45;


import Back.Interface.ConvertString;
import Back.Interface.IRows;
import Back.Part45.Interface.GetInfoPart2;
import org.apache.poi.ss.usermodel.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PaspInfoPart45 implements GetInfoPart2, ConvertString {
	private int nameElem;
	private int eThikness;
	private int eSteel;
	private int eGost;
	private int rowIndex;
	private String[] paspPart;
	private Workbook wb;
	private String fileName;
	private double desTemp;
	private double desPress;

	public PaspInfoPart45(Workbook wb, String fileName) {
		this.wb=wb;
		this.fileName=fileName;
		this.desPress=this.getMaxValue(this.GetPressTemp(wb, 0).toString());
		this.desTemp=this.getMaxValue(this.GetPressTemp(wb, 1).toString());
	}


	private boolean PasreCellValue(Cell cell) {
		try {
			cell.getStringCellValue();
			return true;
		} catch(IllegalStateException e) {
			return false;
		}

	}
	private void GetInfoPart51(String paspPart) {
		Sheet iSheet=this.wb.getSheet(paspPart);
		Iterator<Row> rIter=iSheet.rowIterator();
		while(rIter.hasNext()) {
			Iterator<Cell> cIter=rIter.next().cellIterator();
			while(cIter.hasNext()) {
				Cell cell=cIter.next();

				//	System.out.println("row "+cell.getRowIndex()+ " column "+cell.getColumnIndex());
				if(cell!=null&&cell.toString().toLowerCase().contains("наименован")&&
						cell.getStringCellValue().toLowerCase().contains("элемент")) {
					this.nameElem=cell.getColumnIndex();
					this.rowIndex=cell.getRowIndex();
				}

				if(cell!=null&&cell.toString().toLowerCase().contains("наружн")&&
						cell.toString().toLowerCase().contains("диамет")&&cell.toString().toLowerCase().contains("стенк")) {
					this.eThikness=cell.getColumnIndex();
				}

				if(cell!=null&&cell.toString().toLowerCase().contains("марк")
						&&cell.getStringCellValue().toLowerCase().contains("стали")) {
					this.eSteel=cell.getColumnIndex();
				}

				if(cell!=null&&cell.toString().toLowerCase().contains("гост")&&
						cell.getStringCellValue().toLowerCase().contains("ту")) {
					this.eGost=cell.getColumnIndex();
				}
			}
		}
	}

	public void FillTable(List<IRows<String>> resultList, String... paspPart) {
		this.paspPart=paspPart;
		for(int z=0;z<this.paspPart.length;z++) {
			this.GetInfoPart51(this.paspPart[z]);
			Sheet sht = this.wb.getSheet(this.paspPart[z]);
			int[] mass = {this.nameElem, this.eThikness, this.eSteel, this.eGost};
			for (int i = this.rowIndex + 1; i < sht.getLastRowNum() + 1; i++) {
				try {
					if (sht.getRow(i) != null &&
							sht.getRow(i).getCell(this.nameElem) != null &&
							sht.getRow(i).getCell(this.nameElem).getCellType() != CellType.BLANK) {
						IRows<String> row = new NewRow<>();
						row.addValue(0, this.fileName);
						for (int j = 0; j < mass.length; j++) {
							String str = sht.getRow(i).getCell(mass[j]).toString();
							row.addValue(str);
						}
						row.addValue(Double.toString(this.desPress).replace(".", ","));
						row.addValue(Double.toString(this.desTemp).replace(".", ","));
						resultList.add(row);
					}
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
			Arrays.stream(mass).forEach(e->e=0);
		}
	}
}
